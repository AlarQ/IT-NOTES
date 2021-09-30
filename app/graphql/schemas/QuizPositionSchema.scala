package graphql.schemas

import elastic.response.QuizPositionResponse
import graphql.MainContext
import graphql.resolvers.QuizPositionResolver
import model.MetaData
import model.quiz.CategoryType.CategoryType
import model.quiz.{Category, CategoryType, QuizPosition}
import sangria.ast.StringValue
import sangria.macros.derive.{ReplaceField, deriveObjectType}
import sangria.schema._

import java.time.LocalDateTime

case class QuizPositionSchema(quizPositionResolver: QuizPositionResolver) {

  implicit val GraphQLDateTime = ScalarType[LocalDateTime]( //1
    "DateTime", //2
    coerceOutput = (dt, _) => dt.toString, //3
    coerceInput = { //4
      case StringValue(dt, _, _, _, _) =>
        Some(LocalDateTime.parse(dt)).toRight(DateTimeCoerceViolation)
      case _ => Left(DateTimeCoerceViolation)
    },
    coerceUserInput = { //5
      case s: String =>
        Some(LocalDateTime.parse(s)).toRight(DateTimeCoerceViolation)
      case _ => Left(DateTimeCoerceViolation)
    }
  )

  implicit val metaDataType: ObjectType[Unit, MetaData] =
    deriveObjectType[Unit, MetaData](
      ReplaceField(
        "createdAt",
        Field("createdAt", GraphQLDateTime, resolve = _.value.createdAt)
      )
    )

  implicit val TestEnumType: EnumType[CategoryType] = EnumType(
    "CategoryType",
    Some("CategoryType..."),
    List(
      EnumValue("General", value = CategoryType.General),
      EnumValue("Scala", value = CategoryType.Scala)
    )
  )

  implicit val categoryType: ObjectType[Unit, Category] =
    deriveObjectType[Unit, Category]()
  implicit val quizPositionType: ObjectType[Unit, QuizPosition] =
    deriveObjectType[Unit, QuizPosition]()
  implicit val quizPositionResponseType: ObjectType[Unit, QuizPositionResponse] =
    deriveObjectType[Unit, QuizPositionResponse]()

  val idArg = Argument("id", StringType)

  val queries: List[Field[MainContext, Unit]] = List(
    Field(
      "quizPositions",
      quizPositionResponseType,
      resolve = _ => {
        quizPositionResolver.getAllQuizPositions
      }
    )
//    Field(
//      "quizPosition",
//      OptionType(quizPositionType),
//      arguments = idArg :: Nil,
//      resolve = c => quizPositionResolver.getQuizPositionById(c.arg(idArg))
//    )
  )

  val schema: Schema[MainContext, Unit] = sangria.schema.Schema(
    query = ObjectType(
      "Query",
      fields(
        queries: _*
      )
    )
  )
}
