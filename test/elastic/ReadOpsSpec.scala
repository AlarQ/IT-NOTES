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

//  "quizPosition doc" should "be retrieved with filter" in {
//    implicit val index = "quizposition"
//    elasticRepo.searchEntityById("BEDE-89048-AYNZ-36006").onComplete {
//      case Success(response) =>
//        response.foreach(println)
//
//      case Failure(exception) => throw exception
//    }
//    Thread.sleep(2000)
//  }
//
//  "article doc" should "be retrieved with filter" in {
//    implicit val index = "article"
//    elasticRepo.searchArticleById("BEGT-71367-GYSX-09767").onComplete {
//      case Success(response) =>
//        response.foreach(println)
//      case Failure(exception) => throw exception
//    }
//    Thread.sleep(2000)
//  }
}
