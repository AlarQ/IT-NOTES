package model.quiz

import com.sksamuel.elastic4s.HitReader
import common.IdGenerator
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, Encoder}
import model.quiz.CategoryType.{CategoryType, General}
import model.{Entity, MetaData}
import play.api.libs.json.Json

case class Category(
    id: String,
    categoryType: CategoryType,
    parentCategory: Option[Category] = None,
    childCategories: List[Category] = Nil,
    metaData: MetaData
) extends Entity {}

object Category {

  implicit val fooDecoder: Decoder[Category] = deriveDecoder[Category]
  implicit val fooEncoder: Encoder[Category] = deriveEncoder[Category]

  implicit val jsValueFormat = Json.format[Category]

  val general = Category(categoryType = General, parentCategory = None, childCategories = Nil)

  def apply(categoryType: CategoryType, parentCategory: Option[Category], childCategories: List[Category]): Category =
    new Category(
      id = IdGenerator.generator.nextId(),
      categoryType = categoryType,
      parentCategory = parentCategory,
      childCategories = childCategories,
      metaData = MetaData.empty
    )
}
