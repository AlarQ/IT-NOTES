package elastic.response

import model.quiz.QuizPosition

case class QuizPositionResponse(hits: Seq[QuizPosition], total: Long) extends SearchResponse[QuizPosition]
