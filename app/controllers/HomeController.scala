package controllers

import com.google.inject.{Inject, Singleton}
import graphql.GraphQLServer
import graphql.commands.Mutations.{createArticle, createQuizPosition}
import graphql.commands.{Mutations, Queries}
import model.article.Article
import model.quiz.QuizPosition
import play.api.libs.json._
import play.api.mvc._

import java.util.concurrent.TimeUnit
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success, Try}

@Singleton
class HomeController @Inject() (val controllerComponents: ControllerComponents) extends BaseController {

  def index = Action(Ok(views.html.main()))

  // ---------- QUIZ POSITION ----------

  def quizPositions = {
    val queryResult = GraphQLServer.executeGraphQLQuery(Queries.getQuizPositions)
    val x = Await.result(queryResult, Duration(5, TimeUnit.SECONDS))
    val quizPositions = (x \ "data" \ "quizPositions" \ "hits").as[List[QuizPosition]]
    // TODO handle empty response x
    Action(Ok(views.html.quizposition.quizPositions(quizPositions)))
  }

  def quizPosition(id: String, question: String, answer: String) =
    Action(Ok(views.html.quizposition.quizPosition(question, answer)))

  def addQuizPositionForm = Action(Ok(views.html.quizposition.quizPositionForm()))

  def addQuizPosition =
    Action { request =>
      val postVals = request.body.asFormUrlEncoded
      postVals.map { args =>
        val question = args("question").head
        val answer = args("answer").head
        val category = args("category").head

        val createQuizPositionQuery = createQuizPosition(question, answer, category)
        GraphQLServer.executeGraphQLQuery(createQuizPositionQuery)
      }
      // TODO add some notification
      Ok(views.html.main())
    }

  def deleteQuizPosition(qpId: String)= Action{
    val deleteOp =  Mutations.deleteQuizPosition(qpId)
    GraphQLServer.executeGraphQLQuery(deleteOp)
    // TODO add some notification
    Ok(views.html.main())
  }

  // ---------- ARTICLE ----------

  def articles = {
    val queryResult = GraphQLServer.executeGraphQLQuery(Queries.getArticles)
    val x = Await.result(queryResult, Duration(5, TimeUnit.SECONDS))
    val articles = (x \ "data" \ "articles" \ "hits").as[List[Article]]
    // TODO handle empty response x
    Action(Ok(views.html.article.articles(articles)))
  }

  def article(id: String, title: String, content: String) = Action(Ok(views.html.article.article(title, content)))

  def addArticleForm = Action(Ok(views.html.article.articleForm()))

  def addArticle =
    Action { request =>
      val postVals = request.body.asFormUrlEncoded
      postVals.map { args =>
        val title = args("title").head
        val content = args("content").head
        val category = args("category").head

        val createArticleQuery = createArticle(title, content, category)
         GraphQLServer.executeGraphQLQuery(createArticleQuery)
      }
      // TODO add some notification
      Ok(views.html.main())
    }

  def deleteArticle(articleId: String)= Action{
    val deleteOp =  Mutations.deleteArticle(articleId)
    GraphQLServer.executeGraphQLQuery(deleteOp)
    // TODO add some notification
    Ok(views.html.main())
  }

  // ---------- GRAPHQL ----------

  def graphiql = Action(Ok(views.html.graphiql()))

  def graphqlBody: Action[JsValue] =
    Action.async(parse.json) { implicit request: Request[JsValue] =>
      val extract: JsValue => (String, Option[String], Option[JsObject]) = query =>
        (
          (query \ "query").as[String],
          (query \ "operationName").asOpt[String],
          (query \ "variables").toOption.flatMap {
            case JsString(vars) => Some(parseVariables(vars))
            case obj: JsObject  => Some(obj)
            case _              => None
          }
        )

      val maybeQuery: Try[(String, Option[String], Option[JsObject])] = Try {
        request.body match {
          case arrayBody @ JsArray(_)   => extract(arrayBody.value(0))
          case objectBody @ JsObject(_) => extract(objectBody)
          case otherType =>
            throw new Error {
              s"/graphql endpoint does not support request body of type [${otherType.getClass.getSimpleName}]"
            }
        }
      }

      maybeQuery match {
        case Success((query, operationName, variables)) =>
          GraphQLServer.executeGraphQLQuery(query, variables, operationName).map(Ok(_))

        case Failure(error) =>
          Future.successful {
            BadRequest(error.getMessage)
          }
      }
    }

  def parseVariables(variables: String): JsObject =
    if (variables.trim.isEmpty || variables.trim == "null") Json.obj()
    else Json.parse(variables).as[JsObject]

}
