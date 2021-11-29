package graphql.resolvers

import com.sksamuel.elastic4s.ElasticClient
import common.HtmlConverter.HTMLEnrichment
import elastic.response.QuizPositionResponse
import elastic.{ElasticRepository, Index}
import model.quiz.{Category, QuizPosition}

import java.io.FileWriter
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

case class QuizPositionResolver(elastic: ElasticRepository) {

  implicit val esClient: ElasticClient = elastic.elasticClient
  implicit val index: Index.Value = Index.quizposition

  def getAllQuizPositions: Future[QuizPositionResponse] = elastic.searchAll.map(_.asInstanceOf[QuizPositionResponse])

  def getQuizPositionById(id: String): Future[Option[QuizPosition]] =
    elastic.searchEntityById(id).map(_.map(_.asInstanceOf[QuizPosition]))

  def createQuizPosition(question: String, answer: String, category: String): Boolean = {
    val fw = new FileWriter("quiz_backup/scala.txt", true)
    try {
      fw.write(
        "\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n" +
          question + "\n" + "==========================================\n" +
          answer
      )
    } finally fw.close()

    elastic.indexEntity(
      QuizPosition(question = question.toHTML, answer = answer.toHTML, category = Category.withName(category))
    )
  }

  def deleteQuizPosition(qpId: String)= elastic.deleteEntity(qpId)
}
