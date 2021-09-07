package elastic

import elastic.ElasticTestRepo._
import model.Category
import model.quiz.QuizPosition
import org.scalatest.BeforeAndAfter
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class WriteOpsSpec extends AnyFlatSpec with Matchers with BeforeAndAfter {

  val quizPosition: QuizPosition = QuizPosition(question = "aaa", answer = "bbb")

  "QuizPosition doc" should "be indexed" in {
    elasticRepo.indexEntity(quizPosition) shouldBe true
  }
}
