package model.quiz

import com.sksamuel.elastic4s.{Hit, HitReader}
import common.IdGenerator
import model.quiz.CategoryType.General
import model.{Entity, MetaData}
import play.api.libs.json.Json

import scala.util.Try

case class QuizPosition(
    id: String,
    question: String,
    answer: String,
    answerLength: Int = -1,
    category: Category = Category.general,
    metaData: MetaData = MetaData.empty
) extends Entity

object QuizPosition {

  implicit val jsValueFormat = Json.using[Json.WithDefaultValues].format[QuizPosition]

  implicit object QuizPositionReader extends HitReader[QuizPosition] {
    override def read(hit: Hit): Try[QuizPosition] = {
      val source = hit.sourceAsMap
      Try(
        QuizPosition(source("question").toString, source("answer").toString, source("category").asInstanceOf[Category])
      )
    }
  }

  def apply(question: String, answer: String, category: Category): QuizPosition =
    new QuizPosition(
      id = IdGenerator.generator.nextId(),
      question = question,
      answer = answer,
      answerLength = answer.length,
      category = category
    )

  def apply(question: String, answer: String): QuizPosition =
    new QuizPosition(
      id = IdGenerator.generator.nextId(),
      question = question,
      answer = answer,
      answerLength = answer.length,
      category = Category.general
    )

}
