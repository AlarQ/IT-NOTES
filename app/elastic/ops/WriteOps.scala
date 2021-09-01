package elastic.ops

import com.sksamuel.elastic4s.ElasticApi.RichFuture
import com.sksamuel.elastic4s.ElasticClient
import com.sksamuel.elastic4s.ElasticDsl.{IndexHandler, indexInto}
import com.sksamuel.elastic4s.requests.common.RefreshPolicy
import model.Entity
import io.circe.generic.auto._
import com.sksamuel.elastic4s.circe._
import model.SearchableEntities.resolveIndexAndJson

trait WriteOps {

  def indexEntity(entity: Entity)(implicit elasticClient: ElasticClient): Boolean = {
    val (index, json) = resolveIndexAndJson(entity)
    println(json)
    val response = elasticClient.execute {
      indexInto(index).id(entity.id) doc json refresh (RefreshPolicy.IMMEDIATE)
    }.await

    response.body.isDefined
  }


}
