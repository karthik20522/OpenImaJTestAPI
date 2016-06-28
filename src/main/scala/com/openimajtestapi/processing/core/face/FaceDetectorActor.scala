package com.OpenImaJTestAPI.processing.core.face

import akka.actor.Actor
import org.openimaj.image.FImage
import org.openimaj.image.processing.face.detection.HaarCascadeDetector
import com.OpenImaJTestAPI.models.DetectedFaces
import org.openimaj.image.DisplayUtilities
import org.openimaj.image.MBFImage
import org.openimaj.image.processing.resize.ResizeProcessor
import scala.collection.JavaConverters._
import org.openimaj.image.processing.face.detection.SandeepFaceDetector
import org.openimaj.image.processing.face.detection.CLMDetectedFace
import org.openimaj.image.processing.face.detection.CLMFaceDetector
import com.OpenImaJTestAPI.models._
import com.OpenImaJTestAPI.processing.core.classifier.CityLandscapeTree
import java.io.File
import java.io.FileInputStream

class FaceDetectorActor extends Actor {
  def receive = {
    case fImage: FImage => {
      val clm = new CLMFaceDetector()
      val resizedfImage = (fImage.getHeight(), fImage.getWidth()) match {
        case (h @ _, w @ _) if h > 1024 || w > 1024 => fImage.process(new ResizeProcessor(1024))
        case (_, _) => fImage
      }
      val clmfaces = clm.detectFaces(resizedfImage).asScala

      val faces = clmfaces.map(f => Face(f.getConfidence(),
        Position(f.getBounds().getTopLeft().getX(), f.getBounds().getTopLeft().getY()),
        Position(f.getBounds().getBottomRight().getX(), f.getBounds().getBottomRight().getY()),
        f.getYaw(), f.getPitch(), f.getRoll()))

      sender ! DetectedFaces(clmfaces.size, faces = Some(faces.toList))
    }
    case _ => DetectedFaces
  }
}