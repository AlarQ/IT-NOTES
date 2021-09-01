package model

import java.time.{LocalDateTime, OffsetDateTime}

// TODO EB ustawiać przy tworzeniu i modyfikacji
case class MetaData(createdAt: OffsetDateTime, modifiedAt: Option[OffsetDateTime] = None)

case object MetaData {
  // TODO EB jak to zrobić przy użyciu cats
  def empty: MetaData = MetaData(OffsetDateTime.now())

}