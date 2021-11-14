package elastic

import com.sksamuel.elastic4s.ElasticProperties

object ESRepo {
  val HOST = sys.env.getOrElse("ELASTICSEARCH_HOST", "elasticsearch")
  val elasticRepo = ElasticRepository(
    ElasticProperties(s"http://localhost:9200")
  )
}
