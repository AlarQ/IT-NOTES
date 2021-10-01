package model

import com.sksamuel.elastic4s.Index
import io.circe.generic.auto._
import io.circe.syntax._
import model.quiz.QuizPosition

object SearchableEntities {

  def resolveIndexAndJson(entity: Entity) =
    (resolveIndex(entity), resolveJson(entity))

  // TODO EB
  def resolveJson(entity: Entity) =
    entity.getClass.getSimpleName match {
      case "QuizPosition" => entity.asInstanceOf[QuizPosition].asJson
      case _              => throw new ClassCastException
    }

  def resolveIndex(entity: Entity): Index = Index(entity.getClass.getSimpleName.toLowerCase)

}
