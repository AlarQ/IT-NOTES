package model

import com.sksamuel.elastic4s.Index

object SearchableEntities {

  def resolveIndexAndJson(entity: Entity) =
    (resolveIndex(entity), resolveJson(entity))

  // TODO EB
  def resolveJson(entity: Entity) = entity.getClass.getSimpleName match {
    case _ => ""
  }

  def resolveIndex(entity: Entity): Index = Index(entity.getClass.getSimpleName.toLowerCase)


}
