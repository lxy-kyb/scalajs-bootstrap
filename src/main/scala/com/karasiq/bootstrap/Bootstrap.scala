package com.karasiq.bootstrap

import com.karasiq.bootstrap.buttons.ButtonBuilder
import org.scalajs.dom

import scalatags.JsDom.all._

object Bootstrap {
  /**
    * Jumbotron
    * @see [[https://getbootstrap.com/components/#jumbotron]]
    */
  def jumbotron: ConcreteHtmlTag[dom.html.Div] = div(`class` := "jumbotron")

  /**
    * Default button
    */
  def button: ConcreteHtmlTag[dom.html.Button] = ButtonBuilder().build

  /**
    * Glyphicon
    * @param name Icon name
    * @see [[https://getbootstrap.com/components/#glyphicons]]
    */
  def icon(name: String): ConcreteHtmlTag[dom.html.Span] = {
    span(`class` := s"glyphicon glyphicon-$name", aria.hidden := true)
  }

  private var idCounter: Int = 0

  /**
    * Generates unique element ID
    */
  def newId: String = {
    idCounter = idCounter + 1
    s"bs-auto-$idCounter"
  }
}