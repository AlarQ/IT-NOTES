package model.article

import model.article.ElasticForArticleLoader._

import scala.io.Source

case object ArticleLoader extends App {

  load

  def load = indexArticles(loadFilesToArticles)

  def loadFilesToArticles: List[Article] = {
    val files = new java.io.File("article_backup").listFiles.toList
    files
      .map(file => {
        val fileData: List[String] = file.getName.split('_').toList
        fileData match {
          case category :: title :: Nil =>
            val lines = Source.fromFile(file)
            val desc =
              try lines.mkString
              finally lines.close
            Article(title = title, content = desc)
          case _ => Article("x", "x")
        }
      })
      .filter(_.title != "x")
  }

  def indexArticles(articles: List[Article]) = {
    articles.foreach(article => elasticRepo.indexEntity(article))
  }

}
