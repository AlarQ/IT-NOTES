package model.article

import com.sksamuel.elastic4s.{Hit, HitReader}
import common.IdGenerator
import model.quiz.Category
import model.quiz.Category.Category
import model.{Entity, MetaData}
import play.api.libs.json.Json

import scala.util.Try

case class Article(
                    id: String,
                    title: String,
                    content: String,
                    category: Category = Category.GENERAL,
                    tags: List[String] = Nil,
                    metaData: MetaData = MetaData.empty
) extends Entity

object Article {

  implicit val jsValueFormat = Json.using[Json.WithDefaultValues].format[Article]

  implicit object ArticleReader extends HitReader[Article] {
    override def read(hit: Hit): Try[Article] = {
      val source = hit.sourceAsMap
      Try(
        Article(source("title").toString, source("content").toString, source("category").asInstanceOf[Category])
      )
    }
  }

  def apply(
      title: String,
      content: String
  ): Article = {
    new Article(
      id = IdGenerator.generator.nextId(),
      title = title,
      content = content
    )
  }

  def apply(
      title: String,
      content: String,
      category: Category
  ): Article = {
    new Article(
      id = IdGenerator.generator.nextId(),
      title = title,
      content = content,
      category = category
    )
  }
}
