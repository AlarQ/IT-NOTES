package elastic

import elastic.ElasticTestRepo._
import elastic.query.Filter
import org.scalatest.BeforeAndAfter
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

class ReadOpsSpec extends AnyFlatSpec with Matchers with BeforeAndAfter {

  val queryFilter = Filter(name = "book")
  
  "Product doc" should "be retrieved with filter" in {
    elasticRepo.searchEntityByFilter(queryFilter).onComplete {
      case Success(response) =>
        response.hits.foreach(println)
        response.total shouldBe 1
      case Failure(exception) => println(exception)
    }
    Thread.sleep(2000)

  }
}
