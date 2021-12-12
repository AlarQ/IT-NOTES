package elastic.ops

import com.sksamuel.elastic4s.ElasticApi.{RichFuture, deleteById}
import com.sksamuel.elastic4s.ElasticClient
import com.sksamuel.elastic4s.ElasticDsl.{DeleteByIdHandler, IndexHandler, indexInto}
import com.sksamuel.elastic4s.circe._
import com.sksamuel.elastic4s.requests.common.RefreshPolicy
import elastic.Index.Index
import model.Entity
import model.SearchableEntities.resolveIndexAndJson
import play.api.Logging

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

trait WriteOps extends Logging {

  def indexEntity(entity: Entity)(implicit elasticClient: ElasticClient): Boolean = {
    val (index, json) = resolveIndexAndJson(entity)
    println(s"indexing entity: $entity")
    logger.info(s"Indexing entity $index with id ${entity.id}")
    val response = elasticClient.execute {
      indexInto(index).id(entity.id) doc json refresh (RefreshPolicy.IMMEDIATE)
    }.await

    response.body.isDefined
  }

  def deleteEntity(entityId: String)(implicit elasticClient: ElasticClient, index:Index): Future[Boolean] = {
    elasticClient.execute{
      deleteById(index.toString,entityId)
    }.map(_.isSuccess)
  }

}
