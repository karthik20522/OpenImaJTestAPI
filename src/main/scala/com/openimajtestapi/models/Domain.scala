package com.OpenImaJTestAPI.models

import com.sksamuel.scrimage.Image
import java.util.UUID

trait RestMessage
case class Request(id: String, restOperations: String) extends RestMessage
case class ProcessImage(image: Image, operations: Map[String, String])
case class HttpException(msg: String) extends Exception(msg)
case class Error(message: String)
case class ProcessedMetadata(meta: Map[String, String] = Map(), timeTaken: Option[String] = None) extends RestMessage

/*METADATA*/
case class RequestMetadata(uri: String) extends RestMessage

/*IMAGE ANALYSIS*/
case class RequestAnalysis(uri: String) extends RestMessage
case class AnalysisResult(
  imageRank: Int = 1,
  blur: Double = 0,
  sharpness: Double = 0,
  color: Double = 0,
  quality: Double = 0,
  contrast: Double = 0,
  faces: DetectedFaces,
  metadata: Map[String, String] = Map(),
  e: Option[Map[String, String]] = None)
  extends RestMessage

/*USER FILE*/
case class UserFile(userName: String = "Anonymous",
  fileName: String = s"${UUID.randomUUID.toString}.jpeg",
  uri: Option[String] = None,
  bytes: Option[Array[Byte]] = None)

/*IMAGE FEATURE EXTRACTOR*/
case class RequestFeatureExtract(uri: String) extends RestMessage