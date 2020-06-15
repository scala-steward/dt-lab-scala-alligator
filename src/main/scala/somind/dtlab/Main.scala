package somind.dtlab

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import com.typesafe.scalalogging.LazyLogging
import somind.dtlab.models.JsonSupport
import somind.dtlab.routes._

import scala.concurrent.ExecutionContextExecutor

object Main extends LazyLogging with JsonSupport with HttpSupport {

  def main(args: Array[String]) {

    implicit val system: ActorSystem = ActorSystem("DtLab-system")
    implicit val materializer: ActorMaterializer = ActorMaterializer()
    implicit val executionContext: ExecutionContextExecutor = system.dispatcher

    val route =
      HealthCheck ~
      ActorApiRoute.apply ~
      OperatorApiRoute.apply ~
      LinkApiRoute.apply ~
      TypeApiRoute.apply

    Http().bindAndHandle(route, "0.0.0.0", port)
  }
}

