package graphql.schemas

import elastic.ESRepo
import elastic.response.ArticleResponse
import graphql.MainContext
import graphql.resolvers.ArticleResolver
import graphql.schemas.CommonSangriaDefinitions._
import graphql.schemas.QuizPositionSchema.categoryArg
import model.article.Article
import sangria.macros.derive.deriveObjectType
import sangria.schema.{Argument, BooleanType, Field, ObjectType, OptionType, StringType}

object ArticleSchema {

  lazy val articleResolver = ArticleResolver(ESRepo.elasticRepo)

  implicit val articleType: ObjectType[Unit, Article] =
    deriveObjectType[Unit, Article]()
  implicit val articleResponse: ObjectType[Unit, ArticleResponse] =
    deriveObjectType[Unit, ArticleResponse]()

  val titleArg = Argument("title", StringType)
  val contentArg = Argument("content", StringType)

  val articleQueries: List[Field[MainContext, Unit]] = List(
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

  val articleMutations: List[Field[MainContext, Unit]] = List(
    Field(
      "createArticle",
      BooleanType,
      arguments = titleArg :: contentArg :: categoryArg :: Nil,
      resolve = c => {
        val title = c arg titleArg
        val content = c arg contentArg
        val category = c arg categoryArg
        articleResolver.createArticle(title, content, category)
      }
    ),
    Field(
      "deleteArticle",
      BooleanType,
      arguments = idArg :: Nil,
      resolve = c => {
        articleResolver.deleteArticle(c.arg(idArg))
      }
    )

  )
}
