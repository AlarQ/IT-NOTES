package common

import common.HtmlConverter._
import org.scalatest.GivenWhenThen
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

class HtmlConverterSpec extends AnyFunSpec with GivenWhenThen with Matchers {

  describe("Html Converter for code snippets") {

    it("should convert one line code snippet") {

      Given("text with code snippet")
      val codeSnippetText = "bla blab bla <|code here|> basd asdasd das"

      When("text is converter by HtmlConverter")
      val convertedCodeSnippetText = codeSnippetText.toHTML

      Then("converted text should have proper code tags")
      val expectedCodeSnippetText = "bla blab bla <pre><code>code here</pre></code> basd asdasd das"
      convertedCodeSnippetText shouldBe expectedCodeSnippetText
    }

    it("should convert multiple line code snippets") {

      Given("text with code snippet")
      val codeSnippetText = "bla blab bla <|code here asd sa" +
        "asdasddasd asdasdsd" +
        "asdasdasd|> basd asdasd das"

      When("text is converter by HtmlConverter")
      val convertedCodeSnippetText = codeSnippetText.toHTML

      Then("converted text should have proper code tags")
      val expectedCodeSnippetText = "bla blab bla <pre><code>code here asd sa" +
        "asdasddasd asdasdsd" +
        "asdasdasd</pre></code> basd asdasd das"
      convertedCodeSnippetText shouldBe expectedCodeSnippetText
    }

    it("should convert multiple code snippets") {

      Given("text with multiple code snippets")
      val codeSnippetText = "bla blab bla <|code here|> basd  <|and here too|> asdasd " +
        "saasdas <|and here|>"

      When("text is converter by HtmlConverter")
      val convertedCodeSnippetText = codeSnippetText.toHTML

      Then("converted text should have proper code tags")
      val expectedCodeSnippetText = "bla blab bla <pre><code>code here</pre></code> basd  <pre><code>and here too</pre></code> asdasd " +
        "saasdas <pre><code>and here</pre></code>"
      convertedCodeSnippetText shouldBe expectedCodeSnippetText
    }
  }

  describe("Html Converter for lists") {

    it("should convert list") {

      Given("text with list")
      val listText = "bla blab bla l#" +
        "E> item1" +
        "E> item2" +
        "E> item3" +
        "#l"

      When("text is converter by HtmlConverter")
      val convertedListText = listText.toHTML

      Then("converted text should have proper list tags")
      val expectedListText = "bla blab bla <ul>" +
        "<li>item1</li>" +
        "<li>item2</li>" +
        "<li>item3</li>" +
        "</ul>"
      convertedListText shouldBe expectedListText
    }


    it("should convert multiple lists") {

      Given("text with list")
      val listText = "bla blab bla l#" +
        "E> item1" +
        "E> item2" +
        "E> item3" +
        "#l" +
        "bla blab bla l#" +
        "E> item1" +
        "E> item2" +
        "E> item3" +
        "#l"

      When("text is converter by HtmlConverter")
      val convertedListText = listText.toHTML

      Then("converted text should have proper list tags")
      val expectedListText = "bla blab bla <ul>" +
        "<li>item1</li>" +
        "<li>item2</li>" +
        "<li>item3</li>" +
        "</ul>" +
        "bla blab bla <ul>" +
        "<li>item1</li>" +
        "<li>item2</li>" +
        "<li>item3</li>" +
        "</ul>"
      convertedListText shouldBe expectedListText
    }


    // TODO
    it("should convert nested lists") {

      Given("text with list")
      val listText = "bla blab bla l#" +
        "E> item1 asdasds" +
        "l#" +
        "E> item1" +
        "E> item2" +
        "E> item3" +
        "#l" +
        "E> item2" +
        "E> item3" +
        "#l"

      When("text is converter by HtmlConverter")
      val convertedListText = listText.toHTML

      Then("converted text should have proper list tags")
      val expectedListText = "bla blab bla <ul>" +
        "<li>item1 asdasds" +
        "<ul>" +
        "<li>item1</li>" +
        "<li>item2</li>" +
        "<li>item3</li>" +
        "</ul>" +
        "</li>" +
        "<li>item2</li>" +
        "<li>item3</li>" +
        "</ul>"
      convertedListText shouldBe expectedListText
    }
  }
}
