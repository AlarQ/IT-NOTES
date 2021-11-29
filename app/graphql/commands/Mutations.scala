package graphql.commands

object Mutations {

  def createQuizPosition(question: String, answer: String, category: String) = {
    s"mutation createQP{\n  " +
      s"createQuizPosition(question:${'"'} $question${'"'}      " +
      s" answer: ${'"'}$answer${'"'} \n        " +
      s"category: ${'"'}$category${'"'})\n      }"
  }

  def deleteQuizPosition(qpId: String): String = {
    s"mutation delete{\n  deleteQuizPosition(id:${'"'}$qpId${'"'})\n}"
  }

}
