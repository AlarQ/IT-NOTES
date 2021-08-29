package common

import com.softwaremill.id.pretty.{PrettyIdGenerator, StringIdGenerator}

object IdGenerator {
  val generator: StringIdGenerator = PrettyIdGenerator.singleNode
}
