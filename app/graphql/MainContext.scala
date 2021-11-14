package graphql

import elastic.ESRepo.elasticRepo
import elastic.ElasticRepository

case class MainContext(elastic: ElasticRepository)

case object MainContext {

  // TODO use

  lazy val getContext = MainContext(elasticRepo)
}
