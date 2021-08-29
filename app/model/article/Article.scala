package model.article

import model.{Category, Entity}

case class Article(title:String,content: String,category:Category) extends Entity
