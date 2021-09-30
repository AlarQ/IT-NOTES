package model.quiz

import io.circe.{Decoder, Encoder}
import play.api.libs.json.{Format, JsResult, JsValue, Json}


object CategoryType extends Enumeration {

  type CategoryType = Value
  val General, Scala, Elasticsearch, Kafka, GraphQL, Linux, DesignPatterns,Git,Docker,Algorithms = Value

  implicit val genderDecoder: Decoder[CategoryType.Value] = Decoder.decodeEnumeration(CategoryType)
  implicit val genderEncoder: Encoder[CategoryType.Value] = Encoder.encodeEnumeration(CategoryType)

  implicit val format = new Format[CategoryType.Value] {
    override def writes(o: CategoryType.Value): JsValue = Json.toJson(o.toString)
    override def reads(json: JsValue): JsResult[CategoryType.Value] = json.validate[String].map(CategoryType.withName)
  }
}
