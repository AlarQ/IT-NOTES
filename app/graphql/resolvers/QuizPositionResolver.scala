package graphql.resolvers

import elastic.ElasticRepository
import elastic.response.QuizPositionResponse
import model.quiz.QuizPosition

import scala.concurrent.Future

case class QuizPositionResolver(elastic: ElasticRepository) {

  implicit val esClient = elastic.elasticClient
  implicit val index = "quizposition"

  def getAllQuizPositions: Future[QuizPositionResponse] = elastic.searchAll

  def getQuizPositionById(id: String): Future[Option[QuizPosition]] = elastic.searchById(id)
}
