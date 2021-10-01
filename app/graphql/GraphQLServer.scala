package graphql

import com.sksamuel.elastic4s.ElasticProperties
import elastic.ElasticRepository
import graphql.resolvers.{ArticleResolver, QuizPositionResolver}
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

  val graphqlSchema = QuizPositionSchema(QuizPositionResolver(elastic),
    ArticleResolver(elastic))

  def executeGraphQLQuery(
      query: String,
      variables: Option[JsObject] = None,
      operation: Option[String] = None
  ): Future[JsValue] =
    QueryParser.parse(query.replace("localhost:", "localhost;")) match {
      case Success(query: Document) =>
        Executor
          .execute(
            schema = graphqlSchema.schema,
            queryAst = query,
            userContext = MainContext(elastic),
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
