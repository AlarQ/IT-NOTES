package graphql.schemas

import elastic.ESRepo
import elastic.response.QuizPositionResponse
import graphql.MainContext
import graphql.resolvers.QuizPositionResolver
import graphql.schemas.CommonSangriaDefinitions._
import model.quiz.QuizPosition
import sangria.macros.derive.deriveObjectType
import sangria.schema.{Argument, BooleanType, Field, ObjectType, OptionType, StringType}

object QuizPositionSchema {
  lazy val quizPositionResolver = QuizPositionResolver(ESRepo.elasticRepo)

  implicit val quizPositionType: ObjectType[Unit, QuizPosition] =
    deriveObjectType[Unit, QuizPosition]()
  implicit val quizPositionResponseType: ObjectType[Unit, QuizPositionResponse] =
    deriveObjectType[Unit, QuizPositionResponse]()

  val questionArg = Argument("question", StringType)
  val answerArg = Argument("answer", StringType)
  val categoryArg = Argument("category", StringType)

  val quizPositionQueries: List[Field[MainContext, Unit]] = List(
    Field(
      "quizPositions",
      quizPositionResponseType,
      resolve = _ => {
        quizPositionResolver.getAllQuizPositions
      }
    ),
    Field(
      "quizPosition",
      OptionType(quizPositionType),
      arguments = idArg :: Nil,
      resolve = c => quizPositionResolver.getQuizPositionById(c.arg(idArg))
    )
  )

  val quizPositionMutations: List[Field[MainContext, Unit]] = List(
    Field(
      "createQuizPosition",
      BooleanType,
      arguments = questionArg :: answerArg :: categoryArg :: Nil,
      resolve = c => {
        val question = c arg questionArg
        val answer = c arg answerArg
        val category = c arg categoryArg
        quizPositionResolver.createQuizPosition(question, answer, category)
      }
    ),
    Field(
      "deleteQuizPosition",
      BooleanType,
      arguments = idArg :: Nil,
      resolve = c => {
        quizPositionResolver.deleteQuizPosition(c.arg(idArg))
      }
    )

  )
}
