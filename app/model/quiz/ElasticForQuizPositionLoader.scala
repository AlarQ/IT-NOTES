package model.quiz

import com.sksamuel.elastic4s.ElasticProperties
import elastic.ElasticRepository


object ElasticForQuizPositionLoader {
  val elasticRepo = new ElasticRepository(
    ElasticProperties(s"http://localhost:9200")
  )

  implicit val esClient = elasticRepo.elasticClient

}
