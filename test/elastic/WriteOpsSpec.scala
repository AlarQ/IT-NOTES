package elastic

import elastic.ElasticTestRepo._
import model.article.Article
import model.quiz.{Category, QuizPosition}
import org.scalatest.BeforeAndAfter
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class WriteOpsSpec extends AnyFlatSpec with Matchers with BeforeAndAfter {

  val quizPosition: QuizPosition = QuizPosition(question = "aaa", answer = "bbb")
  val article: Article = Article(title = "first article", content = "Some content")

  "quizPosition doc" should "be indexed" in {
    elasticRepo.indexEntity(quizPosition) shouldBe true
  }

  "article doc" should "be indexed" in {
    elasticRepo.indexEntity(article) shouldBe true
  }
}
