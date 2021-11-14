package graphql.resolvers

import elastic.response.ArticleResponse
import elastic.{ElasticRepository, Index}
import model.article.Article

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

case class ArticleResolver(elastic: ElasticRepository) {

  implicit val esClient = elastic.elasticClient
  implicit val index = Index.article

  def getAllArticles: Future[ArticleResponse] = elastic.searchAll.map(_.asInstanceOf[ArticleResponse])

  def getArticleById(id: String): Future[Option[Article]] =
    elastic.searchEntityById(id).map(_.map(_.asInstanceOf[Article]))
}
