package graphql.schemas

import elastic.ESRepo
import elastic.response.ArticleResponse
import graphql.MainContext
import graphql.resolvers.ArticleResolver
import graphql.schemas.CommonSangriaDefinitions._
import model.article.Article
import sangria.macros.derive.deriveObjectType
import sangria.schema.{Field, ObjectType, OptionType}

object ArticleSchema {

  lazy val articleResolver = ArticleResolver(ESRepo.elasticRepo)

  implicit val articleType: ObjectType[Unit, Article] =
    deriveObjectType[Unit, Article]()
  implicit val articleResponse: ObjectType[Unit, ArticleResponse] =
    deriveObjectType[Unit, ArticleResponse]()

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
}
