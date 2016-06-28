package com.OpenImaJTestAPI

import akka.io.IO
import spray.can.Http
import akka.actor.{ Props, ActorSystem }
import com.OpenImaJTestAPI.api.routing.RestRouting
import akka.actor._

object Boot extends App {
  implicit val system = ActorSystem("OpenImaJTestAPIAPI")
  //AkkaSampling.print(system)

  val serviceActor = system.actorOf(Props(new RestRouting), name = "rest-routing")

  system.registerOnTermination {
    system.log.info("OpenImaJTestAPI actor shutdown.")
  }

  IO(Http) ! Http.Bind(serviceActor, "0.0.0.0", port = 8081)
}
