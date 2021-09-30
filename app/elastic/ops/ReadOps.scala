package elastic.ops

import com.sksamuel.elastic4s.ElasticApi.idsQuery
import com.sksamuel.elastic4s.ElasticClient
import com.sksamuel.elastic4s.ElasticDsl.{SearchHandler, search}
import com.sksamuel.elastic4s.circe._
import com.sksamuel.elastic4s.requests.searches.SearchResponse
import elastic.response.QuizPositionResponse
import io.circe.generic.auto._
import model.Entity
import model.quiz.{Quiz, QuizPosition}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

trait ReadOps {

  def searchAll(implicit index: String, elasticClient: ElasticClient) =
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

  def searchById[T <: Entity](
      entityId: String
  )(implicit
      index: String,
      elasticClient: ElasticClient
  ): Future[IndexedSeq[Entity]] =
    elasticClient
      .execute {
        search(index).query(idsQuery(entityId))
      }
      .map(_.result)
      .map(res => mapResponse(index, res))

  def mapResponse[T <: Entity](index: String, resp: SearchResponse) = {
    index match {
      case "quizposition" => resp.to[QuizPosition]
      case "quiz"         => resp.to[Quiz]
    }
  }
}
