package model

import com.sksamuel.elastic4s.Index
import model.quiz.QuizPosition

object SearchableEntities {

  def resolveIndexAndJson(entity: Entity) =
    (resolveIndex(entity), resolveJson(entity))

  // TODO EB
  def resolveJson(entity: Entity) = entity.getClass.getSimpleName match {
    case "QuizPosition" => entity.asInstanceOf[QuizPosition]
    case _ => throw new ClassCastException
  }

  def resolveIndex(entity: Entity): Index = Index(entity.getClass.getSimpleName.toLowerCase)


}
