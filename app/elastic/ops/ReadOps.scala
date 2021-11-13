package elastic.ops

import com.sksamuel.elastic4s.ElasticApi.{idsQuery, indexInto}
import com.sksamuel.elastic4s.ElasticClient
import com.sksamuel.elastic4s.ElasticDsl.{IndexHandler, SearchHandler, search}
import com.sksamuel.elastic4s.circe._
import com.sksamuel.elastic4s.requests.common.RefreshPolicy
import com.sksamuel.elastic4s.requests.searches.SearchResponse
import common.HtmlConverter.HTMLEnrichment
import elastic.Index
import elastic.Index.Index
import elastic.response.{ArticleResponse, QuizPositionResponse}
import io.circe.generic.auto._
import model.Entity
import model.article.Article
import model.quiz.{Quiz, QuizPosition}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

trait ReadOps {

  def searchAllQuizPositions(implicit index: Index, elasticClient: ElasticClient) =
    elasticClient
      .execute {
        search(index.toString).limit(100)
      }
      .map(resp =>
        QuizPositionResponse(
          resp.result.to[QuizPosition],
          resp.result.totalHits
        )
      )

  def searchEntityById[T <: Entity](
      entityId: String
  )(implicit
      index: Index,
      elasticClient: ElasticClient
  ): Future[Option[Entity]] =
    elasticClient
      .execute {
        search(index.toString).limit(100).query(idsQuery(entityId))
      }
      .map(_.result)
      .map(res => mapResponse(index, res))

  def mapResponse[T <: Entity](index: Index, resp: SearchResponse) = index match {
    case Index.quizposition => resp.to[QuizPosition].headOption
    case Index.quiz => resp.to[Quiz].headOption
    case Index.article => resp.to[Article].headOption
  }


  def searchAllArticles(implicit index: Index, elasticClient: ElasticClient) =
    elasticClient
      .execute {
        search(index.toString).limit(100)
      }
      .map(resp =>
        ArticleResponse(
          resp.result.to[Article],
          resp.result.totalHits
        )
      )

}
