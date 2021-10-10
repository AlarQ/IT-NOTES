package graphql.resolvers

import elastic.{ElasticRepository, Index}
import elastic.response.{ArticleResponse, QuizPositionResponse}
import model.article.Article
import model.quiz.QuizPosition

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

case class ArticleResolver(elastic: ElasticRepository) {

  implicit val esClient = elastic.elasticClient
  implicit val index = Index.article

  def getAllArticles: Future[ArticleResponse] = elastic.searchAllArticles

  def getArticleById(id: String): Future[Option[Article]] = elastic.searchEntityById(id).map(_.map(_.asInstanceOf[Article]))
}
