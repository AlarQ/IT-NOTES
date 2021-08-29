package model.quiz

import model.{Category, Entity}

case class QuizPosition(question: String, answer: String, category: Category, isShort: Boolean) extends Entity
