package com.OpenImaJTestAPI.processing.core.estimator

import org.openimaj.image.FImage
import akka.actor.Actor
import org.openimaj.image.feature.global.Sharpness
import akka.actor.actorRef2Scala
import com.OpenImaJTestAPI.models.SharpnessValue

class SharpnessEstimateActor extends Actor {
  val sharpDetector = new Sharpness()

  def receive = {
    case fImage: FImage => {
      sharpDetector.analyseImage(fImage)
      sender ! SharpnessValue(sharpDetector.getSharpness)
    }
    case _ => sender ! SharpnessValue
  }
}