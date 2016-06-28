package com.OpenImaJTestAPI.api.routing

import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory
import spray.http._
import scala.concurrent.Future
import spray.routing.{ Directive0, SimpleRoutingApp }
import spray.http.StatusCodes._
import spray.http.HttpRequest
import scala.Some
import spray.http.HttpResponse
import MediaTypes._
import spray.routing.directives.CachingDirectives._
import scala.concurrent.duration.Duration
import spray.http.Uri._
import com.google.common.base.CharMatcher
import scala.collection._
import mutable.ListBuffer
import spray.http.HttpHeaders.RawHeader
import com.google.common.util.concurrent.RateLimiter
import spray.routing.directives.BasicDirectives
import java.util.concurrent.TimeUnit
import com.google.common.cache.{ CacheLoader, CacheBuilder }
import spray.routing.RequestContext

trait RateLimitDirectives extends BasicDirectives {
  val rateLimit: Double = 1

  private val rateLimiters = CacheBuilder.newBuilder().maximumSize(5000).expireAfterAccess(10, TimeUnit.MINUTES).build(
    new CacheLoader[RemoteAddress, RateLimiter] {
      def load(key: RemoteAddress) = {
        RateLimiter.create(rateLimit)
      }
    })

  def rateLimit(ip: RemoteAddress): Directive0 = {
    mapInnerRoute {
      inner =>
        ctx =>
          if (isUser(ctx) || rateLimiters.get(ip).tryAcquire())
            inner(ctx.withHttpResponseHeadersMapped(headers => RawHeader("X-RateLimit-Limit", rateLimit.toString) :: headers))
          else {
            ctx.complete(TooManyRequests, s"You have exceeded your rate limit of $rateLimit requests/second.")
          }
    }
  }

  def isUser(ctx: RequestContext): Boolean = {
    false
  }
}