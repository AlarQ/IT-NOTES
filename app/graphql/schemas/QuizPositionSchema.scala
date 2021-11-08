package graphql.schemas

import elastic.response.{ArticleResponse, QuizPositionResponse}
import graphql.MainContext
import graphql.resolvers.{ArticleResolver, QuizPositionResolver}
import model.MetaData
import model.article.Article
import model.quiz.Category.Category
import model.quiz.{Category, QuizPosition}
import sangria.ast.StringValue
import sangria.macros.derive.{ReplaceField, deriveObjectType}
import sangria.schema._

import java.time.LocalDateTime

case class QuizPositionSchema(quizPositionResolver: QuizPositionResolver,
                              articleResolver: ArticleResolver) {

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

//  implicit val categoryType: ObjectType[Unit, Category] =
//    deriveObjectType[Unit, Category]()
  implicit val quizPositionType: ObjectType[Unit, QuizPosition] =
    deriveObjectType[Unit, QuizPosition]()
  implicit val quizPositionResponseType: ObjectType[Unit, QuizPositionResponse] =
    deriveObjectType[Unit, QuizPositionResponse]()

  implicit val articleType: ObjectType[Unit, Article] =
    deriveObjectType[Unit, Article]()
  implicit val articleResponse: ObjectType[Unit, ArticleResponse] =
    deriveObjectType[Unit, ArticleResponse]()

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
      arguments = idArg :: Nil,
      resolve = c => quizPositionResolver.getQuizPositionById(c.arg(idArg))
    ),
    Field(
      "articles",
      articleResponse,
      resolve = _ => {
        articleResolver.getAllArticles
      }
    ),
    Field(
      "article",
      OptionType(articleType),
      arguments = idArg :: Nil,
      resolve = c => articleResolver.getArticleById(c.arg(idArg))
    )
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
