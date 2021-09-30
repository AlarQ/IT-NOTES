package model.article

import common.IdGenerator
import model.quiz.Category
import model.{Entity, MetaData}

case class Article(id: String, title: String, content: String, category: Category, metaData: MetaData) extends Entity

object Article{
  def apply(title: String, content: String, category: Category): Article =
    new Article(IdGenerator.generator.nextId(),title, content, category, MetaData.empty)
}