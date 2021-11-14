package model.quiz

import common.HtmlConverter.HTMLEnrichment
import model.article.ElasticForArticleLoader._

import scala.io.Source

case object QuizPositionLoader {

  load

  // TODO change
  def load = {
    indexQuizPositions(loadQuizPositions)
  }

  def loadQuizPositions: List[QuizPosition] = {
    val file = Source.fromFile("quiz_backup/scala.txt")
    val qa = file.iter.mkString.split(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>").toList
    val res = qa.map(x => x.split("==========================================").toList)

    val quizPositions = res.map(qa =>
      QuizPosition(
        question = qa.head.toHTML,
        answer = qa(1).toHTML
      )
    )
    file.close()
    quizPositions
  }

  def indexQuizPositions(quizPositions: List[QuizPosition]) = {
    quizPositions.foreach(qp => elasticRepo.indexEntity(qp))
  }
}
