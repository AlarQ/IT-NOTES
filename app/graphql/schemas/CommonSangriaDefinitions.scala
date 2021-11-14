package graphql.schemas

import model.MetaData
import model.quiz.Category
import model.quiz.Category.Category
import sangria.ast.StringValue
import sangria.macros.derive.{deriveObjectType, ReplaceField}
import sangria.schema.{Argument, EnumType, EnumValue, Field, ObjectType, ScalarType, StringType}

import java.time.LocalDateTime

object CommonSangriaDefinitions {

  implicit val GraphQLDateTime = ScalarType[LocalDateTime](
    "DateTime",
    coerceOutput = (dt, _) => dt.toString,
    coerceInput = {
      case StringValue(dt, _, _, _, _) =>
        Some(LocalDateTime.parse(dt)).toRight(DateTimeCoerceViolation)
      case _ => Left(DateTimeCoerceViolation)
    },
    coerceUserInput = {
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

  implicit val TestEnumType: EnumType[Category] = EnumType(
    "CategoryType",
    Some("CategoryType..."),
    List(
      EnumValue("General", value = Category.GENERAL),
      EnumValue("Scala", value = Category.SCALA)
    )
  )

  val idArg = Argument("id", StringType)

}
