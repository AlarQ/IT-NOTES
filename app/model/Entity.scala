package model

import common.IdGenerator

trait Entity{
  def id: String = IdGenerator.generator.nextId()
  def metadata: MetaData =  MetaData.empty
}

