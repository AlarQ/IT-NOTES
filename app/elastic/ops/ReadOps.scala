package elastic.ops

import com.sksamuel.elastic4s.ElasticApi.idsQuery
import com.sksamuel.elastic4s.ElasticClient
import com.sksamuel.elastic4s.ElasticDsl.{SearchHandler, search}
import com.sksamuel.elastic4s.circe._
import com.sksamuel.elastic4s.requests.searches.SearchResponse
import elastic.response.{ArticleResponse, QuizPositionResponse}
import io.circe.generic.auto._
import model.Entity
import model.article.Article
import model.quiz.QuizPosition

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

trait ReadOps {

  def searchAllQuizPositions(implicit index: String, elasticClient: ElasticClient) =
    elasticClient
      .execute {
        search(index)
      }
      .map(resp =>
        QuizPositionResponse(
          resp.result.to[QuizPosition],
          resp.result.totalHits
        )
      )

  def searchQuizPositionById[T <: Entity](
      entityId: String
  )(implicit
      index: String,
      elasticClient: ElasticClient
  ): Future[Option[QuizPosition]] =
    elasticClient
      .execute {
        search(index).query(idsQuery(entityId))
      }
      .map(_.result)
      .map(res => mapResponse(index, res))

  def mapResponse[T <: Entity](index: String, resp: SearchResponse) =
    resp.to[QuizPosition].headOption

  def searchAllArticles(implicit index: String, elasticClient: ElasticClient) =
    elasticClient
      .execute {
        search(index)
      }
      .map(resp =>
        ArticleResponse(
          resp.result.to[Article],
          resp.result.totalHits
        )
      )

  def searchArticleById[T <: Entity](
      entityId: String
  )(implicit
      index: String,
      elasticClient: ElasticClient
  ): Future[Option[Article]] =
    elasticClient
      .execute {
        search(index).query(idsQuery(entityId))
      }
      .map(_.result)
      .map(res => res.to[Article].headOption)

}
