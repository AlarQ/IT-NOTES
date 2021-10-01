package elastic

import io.circe.{Decoder, Encoder}
import play.api.libs.json.{Format, JsResult, JsValue, Json}

object Index extends Enumeration {

  type Index = Value
  val quizposition, quiz, article = Value

  val lowerCase = this.getClass.getSimpleName.toLowerCase

  implicit val genderDecoder: Decoder[Index.Value] = Decoder.decodeEnumeration(Index)
  implicit val genderEncoder: Encoder[Index.Value] = Encoder.encodeEnumeration(Index)

  implicit val format = new Format[Index.Value] {
    override def writes(o: Index.Value): JsValue = Json.toJson(o.toString)
    override def reads(json: JsValue): JsResult[Index.Value] = json.validate[String].map(Index.withName)
  }
}
