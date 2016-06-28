package com.OpenImaJTestAPI.processing.core.actors
import akka.actor.Actor
import com.OpenImaJTestAPI.models.RequestFeatureExtract

class FeatureExtractActor extends Actor {
  def receive = {
    case RequestFeatureExtract(uri) => {

    }
  }
}