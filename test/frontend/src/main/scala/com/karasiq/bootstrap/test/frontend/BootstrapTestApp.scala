package com.karasiq.bootstrap.test.frontend


import com.karasiq.bootstrap.Bootstrap
import com.karasiq.bootstrap.BootstrapImplicits._
import com.karasiq.bootstrap.buttons._
import com.karasiq.bootstrap.grid.GridSystem.{col, row}
import com.karasiq.bootstrap.navbar.{NavigationBar, NavigationTab}
import com.karasiq.bootstrap.table.{PagedTable, TableRow, TableStyles}
import org.scalajs.dom
import org.scalajs.jquery._
import rx._

import scala.scalajs.js
import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport
import scalatags.JsDom.all._

// TODO: Panels
@JSExport
object BootstrapTestApp extends JSApp {
  private def testButtons: dom.Element = {
    val successButton = ButtonBuilder(ButtonStyle.success)("Win 10000000$").render
    val dangerButton = ButtonBuilder(ButtonStyle.danger)("Format C:\\", onclick := { () ⇒ dom.alert("Boom") }).render

    val toggleButton = Bootstrap.button("Toggle me").toggleButton
    val disabledButton = Bootstrap.button("Heavy computation").disabledButton

    Obs(disabledButton.state, "disabled-button-click-handler") {
      if (disabledButton.state()) {
        dom.setTimeout(() ⇒ {
          dom.alert("Answer: 123")
          disabledButton.state.update(false)
        }, 1000)
      }
    }

    Bootstrap
      .jumbotron(ButtonToolbar(ButtonGroup(ButtonGroupSize.default, successButton, dangerButton), ButtonGroup(ButtonGroupSize.large, toggleButton, disabledButton)).render)
      .render
  }

  private def testPagedTable: dom.Element = {
    val tables = div().render
    def testRow(i: Int): TableRow = {
      TableRow(Seq(i, i + 1, i + 2), onclick := js.ThisFunction.fromFunction1[dom.Element, Unit] { (th: dom.Element) ⇒
        th.classList.add("success")
      })
    }
    val pagedTable = PagedTable(10, (1 to 45).map(testRow))
    pagedTable.setHeading(Seq("First", "Second", "Third"))
    tables.appendChild(div(div(textAlign.center, pagedTable.pagination), pagedTable.render(TableStyles.bordered, TableStyles.hover, TableStyles.striped)).render)
    pagedTable.currentPage.update(2)
    tables
  }

  @JSExport
  override def main(): Unit = {
    jQuery(() ⇒ {
      // Create navigation elements
      val navigationBar = new NavigationBar()
      navigationBar.setTabs(
        NavigationTab("Table", Bootstrap.newId, "briefcase", this.testPagedTable, active = true),
        NavigationTab("Buttons", Bootstrap.newId, "log-in", this.testButtons)
      )

      // Bootstrap container
      val container = div(id := "main-container", `class` := "container")(
        row(col.md(12)(
          navigationBar.content
        ))
      ).render

      // Render page
      val body = jQuery(dom.document.body)
      body.append(navigationBar.navbar("Scala.js Bootstrap Test").render)
      body.append(container)
    })
  }
}