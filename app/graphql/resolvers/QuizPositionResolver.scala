package graphql.resolvers

import elastic.ElasticRepository
import elastic.response.QuizPositionResponse

import scala.concurrent.Future

case class QuizPositionResolver(elastic: ElasticRepository) {

  implicit val esClient = elastic.elasticClient
  implicit val index = "quizposition"

  def getAllQuizPositions: Future[QuizPositionResponse] = elastic.searchAll

  def getQuizPositionById(id: String)= elastic.searchById(id)
}
