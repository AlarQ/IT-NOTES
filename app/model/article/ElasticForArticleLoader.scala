package model.article

import com.sksamuel.elastic4s.ElasticProperties
import elastic.{ElasticRepository, Index}


object ElasticForArticleLoader {
  val elasticRepo = new ElasticRepository(
    ElasticProperties(s"http://localhost:9200")
  )

  implicit val index = Index.article

  implicit val esClient = elasticRepo.elasticClient

}
