package model.article

import com.sksamuel.elastic4s.ElasticProperties
import elastic.ElasticRepository


object ElasticForArticleLoader {
  val elasticRepo = new ElasticRepository(
    ElasticProperties(s"http://localhost:9200")
  )

  implicit val esClient = elasticRepo.elasticClient

}
