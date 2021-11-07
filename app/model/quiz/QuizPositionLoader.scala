package model.quiz

import model.article.ElasticForArticleLoader._

import scala.io.Source

case object QuizPositionLoader extends  App {

  load

  // TODO change
  def load = indexQuizPositions(Nil)

  def loadQuizPositions: List[QuizPosition] = {
    val file = Source.fromFile("quiz_backup/scala.txt")
    val qa = file.iter.mkString.split(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>").toList
    val res = qa.map(x => x.split("==========================================").toList)

    val quizPositions = res.map(qa => QuizPosition(
      question = qa.head,
      answer = qa(1)
    ))
    quizPositions
  }

  def indexQuizPositions(quizPositions: List[QuizPosition]) = {
    // TODO change qp
    val qp = List(
      QuizPosition(question = "q1",answer = "q1"),
      QuizPosition(question = "a2",answer = "q2"),
      QuizPosition(question = "a3",answer = "q3"),
      QuizPosition(question = "a4",answer = "q4"),
      QuizPosition(question = "a5",answer = "q5")
    )
    qp.foreach(qp => elasticRepo.indexEntity(qp))
  }


}
