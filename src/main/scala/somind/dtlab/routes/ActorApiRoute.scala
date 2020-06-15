package somind.dtlab.routes

import java.util.Date

import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.{Directives, Route}
import ch.megard.akka.http.cors.scaladsl.CorsDirectives._
import spray.json._
import com.typesafe.scalalogging.LazyLogging
import somind.dtlab.HttpSupport
import somind.dtlab.models._
import tech.navicore.lotsofnames.LotsOfPeople

object ActorApiRoute
    extends JsonSupport
    with LazyLogging
    with Directives
    with HttpSupport {

  def apply: Route =
    path(urlpath) {
      logRequest(urlpath) {
        handleErrors {
          cors(corsSettings) {
            get {
              logger.debug(s"get $urlpath")
              complete(
                HttpEntity(ContentTypes.`application/json`,
                           "{\"msg\": \"Say hello to Lots of Names API\"}\n"))
            } ~
              post {
                decodeRequest {
                  entity(as[Array[Query]]) { q =>
                    val response = q.map(m => {
                      Name(java.util.UUID.randomUUID(),
                           new Date(),
                           m.sourceId,
                           LotsOfPeople(m.sourceId))
                    })
                    complete(HttpEntity(ContentTypes.`application/json`,
                                        response.toJson.prettyPrint))
                  }
                }
              }

          }
        }
      }
    }
}
