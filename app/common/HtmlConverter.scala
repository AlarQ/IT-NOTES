package common

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.scaladsl.{Flow, Keep, Sink, Source}

import java.util.concurrent.TimeUnit
import scala.concurrent.Await
import scala.concurrent.duration.Duration

case object HtmlConverter {

  implicit val actorSystem = ActorSystem("htmlConverter")

  trait HTMLConverter[T] {
    def convert(value: T): String
  }

  object HTMLConverter {
    def convert[T](value: T)(implicit converter: HTMLConverter[T]): String = converter.convert(value)

    def apply[T](implicit converter: HTMLConverter[T]) = converter
  }

  implicit case object StringConverter extends HTMLConverter[String] {

    override def convert(value: String): String = {
      val future = Source
        .single[String](value)
        .viaMat(convertCodeSnippets)(Keep.right)
        .viaMat(convertLists)(Keep.right)
        .toMat(Sink.head)(Keep.right)
        .run()

      Await.result(future, Duration.apply(1, TimeUnit.SECONDS))
    }

    def convertCodeSnippets: Flow[String, String, NotUsed] = {
      Flow[String]
        .map(value => {
          val pattern = "<\\|([^>]*)\\|>"
          value.replaceAll(pattern, "<pre><code>$1</pre></code>")
        })
    }

    def convertLists: Flow[String, String, NotUsed] = {

      Flow[String]
        .map(value => {
          val listPattern = "l#([^#]*)#l"
          value.replaceAll(listPattern ,"<ul>$1</ul>")
            .replaceAll("E> ","</li><li>")
            .replaceAll("<ul></li>","<ul>")
            .replaceAll("</ul>","</li></ul>")
        })

    }
  }

  implicit class HTMLEnrichment[T](value: T) {
    def toHTML(implicit converter: HTMLConverter[T]): String = converter.convert(value)
  }
}
