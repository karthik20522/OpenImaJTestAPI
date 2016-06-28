package com.OpenImaJTestAPI.api.routing

import akka.actor.{ Props, Actor, ActorRefFactory, ActorRef }
import spray.routing.{ Route, HttpService }
import com.OpenImaJTestAPI.processing.core._
import com.OpenImaJTestAPI._
import com.OpenImaJTestAPI.models._
import com.OpenImaJTestAPI.processing.core.estimator._
import com.OpenImaJTestAPI.processing.core.face._
import com.OpenImaJTestAPI.processing.core.actors._
import com.OpenImaJTestAPI.processing.core.pattern.SiftExtractor
/*import akka.pattern.ask
import spray.can.server.Stats
import scala.concurrent.duration._*/

class RestRouting extends HttpService with Actor with PerRequestCreator with RateLimitDirectives {

  implicit def actorRefFactory = context

  val blurEstimatorActor = context.actorOf(Props[BlurEstimateActor], "Blur")
  val sharpnessEstimatorActor = context.actorOf(Props[SharpnessEstimateActor], "Sharpness")
  val colorEstimatorActor = context.actorOf(Props[ColorEstimateActor], "Color")
  val naturalnessEstimatorActor = context.actorOf(Props[NaturalnessEstimateActor], "Naturanlness")
  val faceEstimatorActor = context.actorOf(Props[FaceDetectorActor], "FaceDetector")
  val metadataExtractActor = context.actorOf(Props[MetadataActor], "Metadata")
  val contrastEstimatorActor = context.actorOf(Props[ContrastEstimateActor], "Contrast")

  val testImage = "http://ppcdn.500px.org/60292350/bc290ee7408c1d11a469e0565775b8f1ba5697a2/5.jpg"

  def receive = runRoute(route)

  val route = {
    clientIP { ip =>
      rateLimit(ip) {
        get {
          path(Segment / "meta") { (id) =>
            readMetadataRoute {
              RequestMetadata(testImage)
            }
          } ~
            path("analyse" / Rest) { (rest) =>
              analyseImageRoute {
                RequestAnalysis(rest)
              }
            } ~
            path("sift" / Rest) { (rest) =>
              complete {
                println("SIFT")
                new SiftExtractor().test
                "OK"
              }
            } ~
            path(Segment / Rest) { (id, pathRest) =>
              processImageRoute {
                Request(testImage, pathRest)
              }
            } /*~
            path("stats") {
              complete {
                actorRefFactory.actorSelection("/user/IO-HTTP/listener-0")
                  .ask(Http.GetStats)(10.second)
                  .mapTo[Stats]
              }
            }*/
        }
      }
    }
  }

  def readMetadataRoute(message: RestMessage): Route =
    ctx => perRequest(ctx, Props(new MetadataActor()), message)

  def processImageRoute(message: RestMessage): Route =
    ctx => perRequest(ctx, Props(new ImageProcessingActor()), message)

  def analyseImageRoute(message: RestMessage): Route =
    ctx => perRequest(ctx, Props(new ImageAnalysisActor(blurEstimatorActor,
      sharpnessEstimatorActor,
      colorEstimatorActor,
      naturalnessEstimatorActor,
      faceEstimatorActor,
      metadataExtractActor,
      contrastEstimatorActor)), message)
}
