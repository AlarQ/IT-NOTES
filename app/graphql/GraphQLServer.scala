package graphql

import graphql.schemas.GraphQLSchema
import play.api.libs.json._
import sangria.ast.Document
import sangria.execution._
import sangria.marshalling.playJson._
import sangria.parser.QueryParser

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

object GraphQLServer {

  def executeGraphQLQuery(
      query: String,
      variables: Option[JsObject] = None,
      operation: Option[String] = None
  ): Future[JsValue] =
    QueryParser.parse(query.replace("localhost:", "localhost;")) match {
      case Success(query: Document) =>
        Executor
          .execute(
            schema = GraphQLSchema.schema,
            queryAst = query,
            userContext = MainContext.getContext,
            operationName = operation,
            variables = variables.getOrElse(Json.obj()),
          )
          .recover {
            case error: QueryAnalysisError =>
              println(error.resolveError)
              JsFalse
            case error: ErrorWithResolver =>
              println(error.resolveError)
              JsFalse
          }
      case Failure(ex) => Future(JsString(ex.toString))

    }

}
