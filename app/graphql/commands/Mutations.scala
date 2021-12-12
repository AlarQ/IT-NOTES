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


  def createArticle(title: String, content: String, category: String) = {
    s"mutation createQP{\n  " +
      s"createArticle(title:${'"'} $title${'"'}      " +
      s" content: ${'"'}$content${'"'} \n        " +
      s"category: ${'"'}$category${'"'})\n      }"
  }

  def deleteArticle(articleId: String): String = {
    s"mutation delete{\n  deleteArticle(id:${'"'}$articleId${'"'})\n}"
  }

}
