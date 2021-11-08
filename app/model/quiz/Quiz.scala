package model.quiz

import model.quiz.Category.Category
import model.{Entity, MetaData}

case class Quiz(id: String, quizPositionIds: List[String], category: Category, metaData: MetaData = MetaData.empty)
    extends Entity
