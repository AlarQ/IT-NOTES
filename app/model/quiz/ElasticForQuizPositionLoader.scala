package model.quiz

import com.sksamuel.elastic4s.ElasticProperties
import elastic.ElasticRepository

object ElasticForQuizPositionLoader {
  val elasticRepo = new ElasticRepository(
    ElasticProperties("https://ip18nxx5a9:uqk3oeu05g@alder-477352390.us-east-1.bonsaisearch.net:443")
  )

  implicit val esClient = elasticRepo.elasticClient

}
