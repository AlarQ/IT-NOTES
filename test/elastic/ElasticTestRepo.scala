package elastic

import com.sksamuel.elastic4s.ElasticProperties


object ElasticTestRepo {
  val elasticRepo = new ElasticRepository(
    ElasticProperties(s"http://localhost:9200")
  )

  implicit val esClient = elasticRepo.elasticClient

}
