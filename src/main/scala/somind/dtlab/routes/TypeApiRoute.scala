package somind.dtlab.routes

import spray.json._
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import ch.megard.akka.http.cors.scaladsl.CorsDirectives._
import akka.http.scaladsl.server.{Directives, Route}
import com.typesafe.scalalogging.LazyLogging
import somind.dtlab.HttpSupport
import somind.dtlab.models._
import somind.dtlab.observe.Observer

object TypeApiRoute
    extends JsonSupport
    with LazyLogging
    with Directives
    with HttpSupport {

  def apply: Route = {
    path(urlpath / "type"/ Segment) { sourceId =>
      logRequest(s"$urlpath / $sourceId") {
        handleErrors {
          cors(corsSettings) {
            get {
              val response = DtType(List("one", "two", "three"))
              Observer("type_route_get")
              complete(
                HttpEntity(ContentTypes.`application/json`,
                           response.toJson.prettyPrint))
            } ~ post {
                decodeRequest {
                  entity(as[DtType]) { dt =>
                    logger.debug(s"creating new type: $dt")
                    Observer("type_route_post")
                    val response = dt
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

}
