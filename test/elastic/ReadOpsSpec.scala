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
    elasticRepo.searchAll("quizposition").onComplete {
      case Success(response) =>
        response.hits.foreach(println)

      case Failure(exception) => throw exception
    }
    Thread.sleep(2000)

  }
}
