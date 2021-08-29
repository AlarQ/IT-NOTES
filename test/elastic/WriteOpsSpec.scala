package elastic

import elastic.ElasticTestRepo._
import org.scalatest.BeforeAndAfter
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class WriteOpsSpec extends AnyFlatSpec with Matchers with BeforeAndAfter{

  val product: Product = Product(name = "cheeeese")

  "Product doc" should "be indexed" in {
    elasticRepo.indexEntity(product) shouldBe true
  }
}
