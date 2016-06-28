package com.OpenImaJTestAPI.processing.core.face

import akka.actor.Actor
import org.openimaj.image.processing.face.recognition.FaceRecognitionEngine
import org.openimaj.image.processing.face.recognition.EigenFaceRecogniser
import org.openimaj.image.processing.face.detection.keypoints.FKEFaceDetector
import org.openimaj.image.processing.face.detection.HaarCascadeDetector
import org.openimaj.image.processing.face.alignment.RotateScaleAligner
import org.openimaj.feature.DoubleFVComparison
import org.openimaj.image.processing.face.detection.keypoints.KEDetectedFace
import org.openimaj.image.processing.face.feature.EigenFaceFeature.Extractor
import org.openimaj.image.FImage
import org.openimaj.image.processing.face.recognition._
import org.openimaj.feature.FeatureExtractor;
import org.openimaj.image.FImage;
import org.openimaj.image.processing.face.detection.DatasetFaceDetector;
import org.openimaj.image.processing.face.detection.DetectedFace;
import org.openimaj.image.processing.face.detection.FaceDetector;
import org.openimaj.io.IOUtils;
import org.openimaj.io.ReadWriteableBinary;
import org.openimaj.ml.annotation.AnnotatedObject;
import org.openimaj.ml.annotation.ScoredAnnotation;
import org.openimaj.util.pair.IndependentPair;
import scala.collection.JavaConverters._

class FaceRecognitionActor(recognitionType: String) extends Actor {

  var person: String = ""
  def receive = {
    case fImage: FImage => {
      /*val faceDetector = new FKEFaceDetector(new HaarCascadeDetector());
      val faceRecognizer: EigenFaceRecogniser[KEDetectedFace, String] = EigenFaceRecogniser.create(20, new RotateScaleAligner(), 1, DoubleFVComparison.CORRELATION, 0.9f);
      val faceEngine: FaceRecognitionEngine[KEDetectedFace, Extractor[KEDetectedFace], String] = FaceRecognitionEngine.create(faceDetector, faceRecognizer);

      val faces = faceEngine.getDetector().detectFaces(fImage).asScala
      for (face <- faces) {
        val matchedFace = faceEngine.recogniseBest(face.getFacePatch());
        val score = matchedFace.get(0).getSecondObject()
        faceEngine.train(person, face.getFacePatch());
      }*/
    }
  }
}