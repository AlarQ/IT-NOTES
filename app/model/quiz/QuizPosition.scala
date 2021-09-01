package model.quiz

import common.IdGenerator
import model.{Category, Entity, MetaData}

case class QuizPosition(id:String, question: String, answer: String, isShort: Boolean = false) extends Entity

object QuizPosition{
  def apply(question: String, answer: String): QuizPosition =
    new QuizPosition(IdGenerator.generator.nextId(),question, answer, false)
}