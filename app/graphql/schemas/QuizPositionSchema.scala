package graphql.schemas

import elastic.response.{QuizPositionResponse, SearchResponse}
import graphql.MainContext
import graphql.resolvers.QuizPositionResolver
import model.MetaData
import model.quiz.QuizPosition
import sangria.macros.derive.deriveObjectType
import sangria.schema._

import java.time.{LocalDateTime, OffsetDateTime}


case class QuizPositionSchema(quizPositionResolver: QuizPositionResolver) {

  implicit val quizPositionType: ObjectType[Unit, QuizPosition] = deriveObjectType[Unit, QuizPosition]()
  implicit val quizPositionResponseType: ObjectType[Unit, QuizPositionResponse] = deriveObjectType[Unit, QuizPositionResponse]()


  val idArg = Argument("id", StringType)

  val queries: List[Field[MainContext, Unit]] = List(
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
      arguments =  idArg :: Nil,
      resolve = c => quizPositionResolver.getQuizPositionById(c.arg(idArg))
    )
  )


  val schema: Schema[MainContext, Unit] = sangria.schema.Schema(
    query = ObjectType("Query",
      fields(
        queries: _*
      )
    )
  )
}
