package graphql.commands

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
