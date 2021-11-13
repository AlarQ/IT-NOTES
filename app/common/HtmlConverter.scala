package common

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.scaladsl.{Flow, Keep, Sink, Source}

import java.util.concurrent.TimeUnit
import scala.concurrent.Await
import scala.concurrent.duration.Duration


case object HtmlConverter {

  implicit val actorSystem = ActorSystem("htmlSerialize")

  trait HTMLSerializer[T] {
    def serialize(value: T): String
  }

  object HTMLSerializer {
    def serialize[T](value: T)(implicit serializer: HTMLSerializer[T]): String = serializer.serialize(value)

    def apply[T](implicit serializer: HTMLSerializer[T]) = serializer
  }

  implicit case object StringSerializer extends HTMLSerializer[String] {

    override def serialize(value: String): String = {
      val future = Source.single[String](value)
        .viaMat(serializeCodeSnippets)(Keep.right)
        .toMat(Sink.head)(Keep.right).run()


      Await.result(future, Duration.apply(1, TimeUnit.SECONDS))
    }

    def serializeCodeSnippets: Flow[String, String, NotUsed] = {
      Flow[String]
        .map(value => {
          val pattern = "<\\|([\\S\\s]*)\\|>"
          value.replaceAll(pattern, "<pre><code>$1</pre></code>")
        })
    }

    def serializeLists: Flow[String, String, NotUsed] = {
      def serializeItems(value: String) = {
        val items = value.split("E>").toList.map(_.trim)
          .map(_.replace("l#", ""))
          .map(_.replace("#l", ""))

        items.map(item => s"<li>$item</li>\n").mkString
      }

      Flow[String]
        .map(value => {
          val listPattern = "l#([\\S\\s]*)#l".r
            val newval= listPattern.replaceFirstIn(value,"list")
            newval
        })

    }
  }

  def serializeEndOfLines: Flow[String,String, NotUsed] = {
    Flow[String]
      .map(_.replaceAll("\r","<br>"))
  }

  implicit class HTMLEnrichment[T](value: T) {
    def toHTML(implicit serializer: HTMLSerializer[T]): String = serializer.serialize(value)
  }

}

