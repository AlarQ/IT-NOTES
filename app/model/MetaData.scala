package model

import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, Encoder}
import play.api.libs.json.Json
import sangria.validation.Violation

import java.time.LocalDateTime
case object DateTimeCoerceViolation extends Violation {
  override def errorMessage: String = "Error during parsing DateTime"
}
// TODO EB ustawiać przy tworzeniu i modyfikacji
case class MetaData(createdAt: LocalDateTime, modifiedAt: Option[LocalDateTime] = None)

case object MetaData {

  implicit val fooDecoder: Decoder[MetaData] = deriveDecoder[MetaData]
  implicit val fooEncoder: Encoder[MetaData] = deriveEncoder[MetaData]

  implicit val jsValueFormat = Json.format[MetaData]

  def empty: MetaData = MetaData(LocalDateTime.now)

}
