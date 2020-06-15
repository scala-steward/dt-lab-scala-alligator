package somind.dtlab.routes

import java.util.Date

import spray.json._
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import ch.megard.akka.http.cors.scaladsl.CorsDirectives._
import akka.http.scaladsl.server.{Directives, Route}
import com.typesafe.scalalogging.LazyLogging
import somind.dtlab.HttpSupport
import somind.dtlab.models.{JsonSupport, Name}
import tech.navicore.lotsofnames.LotsOfPeople

object TypeApiRoute
    extends JsonSupport
    with LazyLogging
    with Directives
    with HttpSupport {

  def apply: Route = {
    path(urlpath / Segment) { sourceId =>
      logRequest(s"$urlpath / $sourceId") {
        handleErrors {
          cors(corsSettings) {
            get {
              val response = Name(java.util.UUID.randomUUID(),
                                  new Date(),
                                  sourceId,
                                  LotsOfPeople(sourceId))
              complete(
                HttpEntity(ContentTypes.`application/json`,
                           response.toJson.prettyPrint))
            }
          }
        }
      }
    }
  }
}
