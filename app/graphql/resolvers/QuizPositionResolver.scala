package graphql.resolvers

import elastic.ElasticRepository
import elastic.response.{ArticleResponse, QuizPositionResponse}
import model.Entity
import model.article.Article
import model.quiz.QuizPosition

import scala.concurrent.Future

case class QuizPositionResolver(elastic: ElasticRepository) {

  implicit val esClient = elastic.elasticClient
  implicit val index = "quizposition"

  def getAllQuizPositions: Future[QuizPositionResponse] = elastic.searchAllQuizPositions

  def getQuizPositionById(id: String): Future[Option[QuizPosition]] = elastic.searchQuizPositionById(id)
}
