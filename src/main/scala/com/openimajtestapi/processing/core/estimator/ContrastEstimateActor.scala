package com.OpenImaJTestAPI.processing.core.estimator

import akka.actor.Actor
import com.OpenImaJTestAPI.models.ContrastValue
import org.openimaj.image.feature.global.RGBRMSContrast
import org.openimaj.image.MBFImage

class ContrastEstimateActor extends Actor {
  def receive = {
    case mbfImage: MBFImage => {
      val contrastEstimator = new RGBRMSContrast()
      contrastEstimator.analyseImage(mbfImage)
      sender ! ContrastValue(contrastEstimator.getContrast())
    }
    case _ => sender ! ContrastValue
  }
}