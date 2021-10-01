package graphql.commands

import sangria.ast.Document
import sangria.macros._

object Queries {

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

  def getArticles: String =
    """
     query getAllArticles{
      articles{
        hits{
          id
          title
          content
        }
      }
    }
  """
}
