package elastic.ops

import com.sksamuel.elastic4s.ElasticClient
import com.sksamuel.elastic4s.ElasticDsl.{SearchHandler, must, search}
import com.sksamuel.elastic4s.requests.searches.queries.PrefixQuery
import elastic.query.Filter

import scala.concurrent.ExecutionContext.Implicits.global

trait ReadOps {

  // todo fix
  def searchEntityByFilter(index: String, filter: Filter)(implicit elasticClient: ElasticClient) =
    elasticClient.execute {
      search(index).bool(must(PrefixQuery("name", filter.name)))
    }
  //.map(resp => ProductResponse(resp.result.to[Product], resp.result.totalHits))

  def searchAll(index: String)(implicit elasticClient: ElasticClient) =
    elasticClient.execute {
      search(index)
    }
  //.map(resp => ProductResponse(resp.result.to[], resp.result.totalHits))
}
