package com.karasiq.bootstrap.context

import scala.language.postfixOps

trait JSClassModifiers extends ClassModifiers { self: JSRenderingContext ⇒
  def addClass(element: Element, className: String): Unit = {
    element.classList.add(className)
  }

  def removeClass(element: Element, className: String): Unit = {
    element.classList.remove(className)
  }
}
