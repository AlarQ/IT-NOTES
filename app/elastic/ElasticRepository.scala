package elastic

import com.sksamuel.elastic4s.http.JavaClient
import com.sksamuel.elastic4s.{ElasticClient, ElasticProperties}
import elastic.ops.{ReadOps, WriteOps}

import scala.concurrent.ExecutionContext

case class ElasticRepository(props: ElasticProperties) extends WriteOps with ReadOps {
  implicit val ec: ExecutionContext = ExecutionContext.global

  implicit val elasticClient = ElasticClient(
    JavaClient(props)
  )
}
