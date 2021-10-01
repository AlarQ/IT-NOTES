package model

import com.sksamuel.elastic4s.Index
import io.circe.generic.auto._
import io.circe.syntax._
import model.article.Article
import model.quiz.QuizPosition

object SearchableEntities {

  def resolveIndexAndJson(entity: Entity) =
    (resolveIndex(entity), resolveJson(entity))

  // TODO EB
  def resolveJson(entity: Entity) =
    entity.getClass.getSimpleName match {
      case "QuizPosition" => entity.asInstanceOf[QuizPosition].asJson
      case "Article" => entity.asInstanceOf[Article].asJson
      case _              => throw new ClassCastException
    }

  def resolveIndex(entity: Entity): Index = Index(entity.getClass.getSimpleName.toLowerCase)

}
