package model

case class Category(name: String, parentCategory: Category, childCategories: Category) extends Entity