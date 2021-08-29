package elastic.response

import model.Entity

trait SearchResponse[T <: Entity] {
  val hits: Seq[T]
  val total: Long
}
