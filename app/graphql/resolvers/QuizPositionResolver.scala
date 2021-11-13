package graphql.resolvers

import elastic.{ElasticRepository, Index}
import elastic.response.{ArticleResponse, QuizPositionResponse}
import model.Entity
import model.article.Article
import model.quiz.{Category, QuizPosition}
import model.article.ElasticForArticleLoader._
import common.HtmlConverter.HTMLEnrichment

import java.io.FileWriter
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

case class QuizPositionResolver(elastic: ElasticRepository) {

  implicit val esClient = elastic.elasticClient
  implicit val index = Index.quizposition

  def getAllQuizPositions: Future[QuizPositionResponse] = elastic.searchAllQuizPositions

  def getQuizPositionById(id: String): Future[Option[QuizPosition]] = elastic.searchEntityById(id).map(_.map(_.asInstanceOf[QuizPosition]))

  def createQuizPosition(question: String, answer: String, category: String) = {
    val fw = new FileWriter("quiz_backup/scala.txt", true)
    try {
      fw.write("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n" +
        question + "\n" + "==========================================\n" +
        answer)
    }
    finally fw.close()

    elastic.indexEntity(
      QuizPosition(question = question.toHTML, answer = answer.toHTML, category = Category.withName(category)))
  }
}
