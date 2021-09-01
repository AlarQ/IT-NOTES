package elastic.ops

import com.sksamuel.elastic4s.ElasticApi.idsQuery
import com.sksamuel.elastic4s.ElasticClient
import com.sksamuel.elastic4s.ElasticDsl.{SearchHandler, search}
import io.circe.generic.auto._
import com.sksamuel.elastic4s.circe._
import elastic.response.QuizPositionResponse
import model.Entity
import model.quiz.QuizPosition

import scala.concurrent.ExecutionContext.Implicits.global

trait ReadOps {

  def searchAll(implicit index: String,elasticClient: ElasticClient) =
    elasticClient.execute {
      search(index)
    }
    .map(resp => QuizPositionResponse(resp.result.to[QuizPosition], resp.result.totalHits))

  def searchById[T <: QuizPosition](entityId: String)(implicit index:String, elasticClient: ElasticClient) =
    elasticClient.execute{
      search(index).query(idsQuery(entityId))
    }.map(resp => resp.result.to[T]).map(_.headOption)
}
