package graphql.schemas

import graphql.MainContext
import graphql.schemas.ArticleSchema._
import graphql.schemas.QuizPositionSchema._
import sangria.schema._

case object GraphQLSchema {

  val schema: Schema[MainContext, Unit] = sangria.schema.Schema(
    query = ObjectType(
      "Query",
      fields(
        (quizPositionQueries ++ articleQueries): _*,
      )
    ),
    mutation = Some(
      ObjectType(
        "Mutation",
        fields(
          quizPositionMutations: _*
        )
      )
    )
  )
}
