package model.quiz

import common.IdGenerator
import model.{Category, Entity, MetaData}
import play.api.libs.json.Json

case class QuizPosition(id:String, question: String, answer: String) extends Entity

object QuizPosition{

  implicit val format2 = Json.format[QuizPosition]


  def apply(question: String, answer: String): QuizPosition =
    new QuizPosition(IdGenerator.generator.nextId(),question, answer)
}