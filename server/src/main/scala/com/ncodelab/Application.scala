package com.ncodelab

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server._
import akka.stream.ActorMaterializer
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import sangria.parser.QueryParser
import sangria.execution.{ErrorWithResolver, Executor, QueryAnalysisError}
import sangria.marshalling.sprayJson._
import spray.json._

import scala.util.{Failure, Success}
import com.ncodelab.configuration.{Http => HttpConf}
import com.ncodelab.data.TaskRepoImpl
import com.ncodelab.schema.TaskSchemaDefinition
import com.ncodelab.schema.MyCTX
import com.typesafe.scalalogging.StrictLogging
import sangria.renderer.SchemaRenderer

import ch.megard.akka.http.cors.CorsDirectives._

object Application extends App with StrictLogging {

  implicit val system = ActorSystem("sangria-server")
  implicit val materializer = ActorMaterializer()

  import system.dispatcher

  val schema = SchemaRenderer.renderSchema(TaskSchemaDefinition.TaskSchema)

  val taskRepo = new TaskRepoImpl
  logger.debug(schema)

  val route: Route = cors() {
    (post & path("graphql")) {
      entity(as[JsValue]) { requestJson ⇒
        val JsObject(fields) = requestJson

        val JsString(query) = fields("query")

        val operation = fields.get("operationName") collect {
          case JsString(op) => op
        }

        val vars: JsValue = fields.get("variables") match {
          case Some(obj: JsObject) => obj
          case Some(JsString("null")) => JsObject.empty
          case Some(JsString(s)) if s.trim.nonEmpty => s.parseJson
          case _ => JsObject.empty
        }

        QueryParser.parse(query) match {

          // query parsed successfully, time to execute it!
          case Success(queryAst) ⇒
            complete(Executor.execute(TaskSchemaDefinition.TaskSchema, queryAst, MyCTX(taskRepo),
              variables = vars,
              operationName = operation)
              .map(OK → _)
              .recover {
                case error: QueryAnalysisError =>
                  val resolvedError = error.resolveError
                  logger.error(resolvedError.toString())
                  BadRequest -> resolvedError
                case error: ErrorWithResolver =>
                  val resolvedError = error.resolveError
                  logger.error(resolvedError.toString())
                  InternalServerError -> resolvedError
              })

          // can't parse GraphQL query, return error
          case Failure(error) ⇒
            logger.error("Error!", error)
            complete(BadRequest, JsObject("error" → JsString(error.getMessage)))
        }
      }
    } ~
      get {
        getFromResource("graphiql.html")
      }
  }

  val httpConf = HttpConf.forConfig()

  Http().bindAndHandle(route, httpConf.host, httpConf.port)
}
