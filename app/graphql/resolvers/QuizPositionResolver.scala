package graphql.resolvers

import elastic.{ElasticRepository, Index}
import elastic.response.{ArticleResponse, QuizPositionResponse}
import model.Entity
import model.article.Article
import model.quiz.QuizPosition

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

case class QuizPositionResolver(elastic: ElasticRepository) {

  implicit val esClient = elastic.elasticClient
  implicit val index = Index.quizposition

  def getAllQuizPositions: Future[QuizPositionResponse] = elastic.searchAllQuizPositions

  def getQuizPositionById(id: String): Future[Option[QuizPosition]] = elastic.searchEntityById(id).map(_.map(_.asInstanceOf[QuizPosition]))
}
