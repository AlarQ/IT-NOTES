package graphql.resolvers

import elastic.ElasticRepository
import elastic.response.{ArticleResponse, QuizPositionResponse}
import model.article.Article
import model.quiz.QuizPosition

import scala.concurrent.Future

case class ArticleResolver(elastic: ElasticRepository) {

  implicit val esClient = elastic.elasticClient
  implicit val index = "article"

  def getAllArticles: Future[ArticleResponse] = elastic.searchAllArticles

  def getArticleById(id: String): Future[Option[Article]] = elastic.searchArticleById(id)
}
