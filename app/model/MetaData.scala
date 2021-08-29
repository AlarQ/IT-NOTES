package model

import java.time.LocalDateTime

// TODO EB ustawiać przy tworzeniu i modyfikacji
case class MetaData(createdAt:LocalDateTime, modifiedAt:Option[LocalDateTime] = None)

case object MetaData{
  // TODO EB jak to zrobić przy użyciu cats
  def empty: MetaData = MetaData(LocalDateTime.now())

}