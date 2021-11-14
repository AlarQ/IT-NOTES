package elastic.ops

import com.sksamuel.elastic4s.ElasticApi.idsQuery
import com.sksamuel.elastic4s.ElasticDsl.{search, SearchHandler}
import com.sksamuel.elastic4s.circe._
import com.sksamuel.elastic4s.requests.searches.SearchResponse
import com.sksamuel.elastic4s.{ElasticClient, Response}
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
      .map(res => mapSingleResponse(index, res))

  def mapSingleResponse[T <: Entity](index: Index, resp: Response[SearchResponse]) =
    index match {
      case Index.quizposition => resp.result.to[QuizPosition].headOption
      case Index.quiz         => resp.result.to[Quiz].headOption
      case Index.article      => resp.result.to[Article].headOption
    }

  def searchAll(implicit index: Index, elasticClient: ElasticClient) =
    elasticClient
      .execute {
        search(index.toString).limit(100)
      }
      .map(resp => mapResponse(index, resp))

  def mapResponse(index: Index, resp: Response[SearchResponse]): elastic.response.SearchResponse[Entity] =
    index match {
      case Index.quizposition =>
        QuizPositionResponse(
          resp.result.to[QuizPosition],
          resp.result.totalHits
        )
      case Index.article =>
        ArticleResponse(
          resp.result.to[Article],
          resp.result.totalHits
        )
    }
}
