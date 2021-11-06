package model.quiz

import scala.annotation.tailrec
import scala.io.Source

case object QuizPositionLoader extends App{

  loadQuizPositions

  def loadQuizPositions: List[QuizPosition] = {
    val file = Source.fromFile("quiz_backup/scala_qp.txt")
    val qa = file.iter.mkString.split(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>").toList
    val res = qa.map(x => x.split("==========================================").toList)

    val quizPositions = res.map(qa => QuizPosition(
      question = qa.head,
      answer = qa(1)
    ))
    quizPositions.foreach(qp => println(qp.question.trim + "\n" + qp.answer.trim))
    quizPositions
  }

}
