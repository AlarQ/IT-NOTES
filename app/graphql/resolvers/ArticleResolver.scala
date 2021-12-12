package graphql.resolvers

import common.HtmlConverter.HTMLEnrichment
import elastic.response.ArticleResponse
import elastic.{ElasticRepository, Index}
import model.article.Article
import model.quiz.Category

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

case class ArticleResolver(elastic: ElasticRepository) {

  implicit val esClient = elastic.elasticClient
  implicit val index = Index.article

  def getAllArticles: Future[ArticleResponse] = elastic.searchAll.map(_.asInstanceOf[ArticleResponse])

  def getArticleById(id: String): Future[Option[Article]] =
    elastic.searchEntityById(id).map(_.map(_.asInstanceOf[Article]))

  def createArticle(title: String, content: String, category: String): Boolean = {
    println("in resolver")
    elastic.indexEntity(
      Article(title = title.toHTML, content = content.toHTML, category = Category.withName(category))
    )
  }

  def deleteArticle(articleId: String)= elastic.deleteEntity(articleId)
}
