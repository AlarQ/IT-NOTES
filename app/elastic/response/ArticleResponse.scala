package elastic.response

import model.article.Article

case class ArticleResponse(hits: Seq[Article], total: Long) extends SearchResponse[Article]
