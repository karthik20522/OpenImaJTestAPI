package com.OpenImaJTestAPI.files

import akka.actor.Actor
import com.OpenImaJTestAPI.models.HttpException
import awscala._
import s3._
import java.io.InputStream
import com.OpenImaJTestAPI.models.UserFile
import com.OpenImaJTestAPI.utils.Utils
import java.net.ConnectException
import dispatch.StatusCode
import com.amazonaws.services.s3.model.ObjectMetadata

class S3File extends Actor {
  import context._

  val awsCredentials = new awscala.Credentials(//credentials)
  implicit val s3 = S3(awsCredentials)

  def receive = {

    /**
     * Download file given username and filename
     */
    case UserFile(userName @ _, fileName @ _, None, None) => {
      val userBucket = s3.bucket(userName).getOrElse(s3.createBucket(userName))
      val downloadedFile = userBucket.get(fileName)
      downloadedFile match {
        case None => sender ! HttpException("No image found")
        case _ => {
          val stream = downloadedFile.get.getObjectContent()
          sender ! org.apache.commons.io.IOUtils.toByteArray(stream)
          stream.close()
        }
      }
    }

    /**
     * Upload file given image URI
     */
    case UserFile(userName @ _, fileName @ _, imageURL @ _, None) => {
      val userBucket = s3.bucket(userName).getOrElse(s3.createBucket(userName))
      Utils.downloadFileFromURL(imageURL.get) match {
        case Right(res) => {
          res.getStatusCode() match {
            case 200 => {
              self ! UserFile(userName, fileName, None, Some(res.getResponseBodyAsBytes()))
            }
            case _ => sender ! HttpException("Failed to download Image.")
          }
        }
        case Left(e) => sender ! HttpException("Failed to download Image. " + e.getMessage())
      }
    }

    /**
     * Upload file given Image Bytes
     */
    case UserFile(userName @ _, fileName @ _, _, imageBytes @ _) => {
      val userBucket = s3.bucket(userName).getOrElse(s3.createBucket(userName))
      val contentLength = imageBytes.get.length.toLong
      val metadata = new ObjectMetadata();
      metadata.setContentLength(contentLength);
      val result = userBucket.putObject(fileName, imageBytes.get, metadata)
      sender ! result.contentMd5
    }
  }
}