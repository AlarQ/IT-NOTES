package graphql

import com.sksamuel.elastic4s.ElasticProperties
import elastic.ElasticRepository
import graphql.resolvers.QuizPositionResolver
import graphql.schemas.QuizPositionSchema
import play.api.libs.json._
import play.api.mvc.Results.{BadRequest, InternalServerError, Ok}
import play.api.mvc._
import sangria.ast.Document
import sangria.execution._
import sangria.marshalling.playJson._
import sangria.parser.QueryParser

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}


object GraphQLServer {

  val HOST = sys.env.getOrElse("ELASTICSEARCH_HOST", "elasticsearch")
  val elastic = new ElasticRepository(
    ElasticProperties(s"http://localhost:9200")
  )

  val graphiql = QuizPositionSchema(QuizPositionResolver(elastic))

  def executeGraphQLQuery(query: String, variables: Option[JsObject] = None, operation: Option[String] = None): Future[Result] =
    QueryParser.parse(query.replace("localhost:", "localhost;")) match {
      case Success(query: Document) =>
        Executor.execute(
          schema = graphiql.schema,
          queryAst = query,
          userContext = MainContext(elastic),
          operationName = operation,
          variables = variables.getOrElse(Json.obj()),
        ).map(Ok(_))
          .recover {
            case error: QueryAnalysisError => BadRequest(error.resolveError)
            case error: ErrorWithResolver => InternalServerError(error.resolveError)
          }
      case Failure(ex) => Future(BadRequest(s"${ex.getMessage}"))

    }

}