package model

import common.IdGenerator

case class Category(
             id: String,
             name: String,
             parentCategory: Option[Category] = None,
             childCategories: List[Category] = Nil,
             metadata: MetaData) extends Entity

object Category{
  def apply(name: String, parentCategory: Option[Category], childCategories: List[Category]): Category =
    new Category(id = IdGenerator.generator.nextId(), name, parentCategory, childCategories, MetaData.empty)
}

