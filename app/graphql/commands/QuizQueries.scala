package graphql.commands

import sangria.ast.Document
import sangria.macros._

object QuizQueries {

  def getQuizPositions: String =
    """
     query getAllQuizPositions{
      quizPositions{
        hits{
          id
          question
          answer
        }
      }
    }
  """
}
