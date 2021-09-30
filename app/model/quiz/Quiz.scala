package model.quiz

import model.{Entity, MetaData}

case class Quiz(id: String, quizPositions: List[QuizPosition], category: Category, metaData: MetaData = MetaData.empty) extends Entity
