package model.quiz

import io.circe.{Decoder, Encoder}
import play.api.libs.json.{Format, JsResult, JsValue, Json}

object Category extends Enumeration {

  type Category = Value
  val GENERAL, SCALA, ELASTICSEARCH, KAFKA, GRAPHQL, LINUX, DESIGN_PATTERNS, GIT, DOCKER, ALGORITHMS = Value

  implicit val genderDecoder: Decoder[Category.Value] = Decoder.decodeEnumeration(Category)
  implicit val genderEncoder: Encoder[Category.Value] = Encoder.encodeEnumeration(Category)


  implicit val format = new Format[Category.Value] {
    override def writes(o: Category.Value): JsValue = Json.toJson(o.toString)

    override def reads(json: JsValue): JsResult[Category.Value] = json.validate[String].map(Category.withName)
  }
}
