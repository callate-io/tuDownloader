package io.callate.gui

import java.awt.Color
import java.awt.Dimension
import java.awt.Image
import java.awt._
import java.awt.event.{MouseEvent, MouseAdapter}
import java.io.IOException
import java.net.{URISyntaxException, URI}
import javax.swing.ImageIcon

import scala.swing.Dialog
import scala.swing.Label
import scala.swing._
import scala.swing.BorderPanel.Position._

object About extends Dialog {
  title = "tuDownloader"
  resizable = false
  modal = true

  val imagePanel = new BorderPanel{
    layout(new Label {
      icon = new ImageIcon(new ImageIcon(getClass.getClassLoader.getResource("logo.png")).getImage.getScaledInstance(200, 200, Image.SCALE_SMOOTH))
      preferredSize = new Dimension(100, 100)
    }) = Center
  }

  val createdBy = new Label { text = "tuDownloader v1.0 - "}
  val callateName = new Label {
    text = "callate.io"
    foreground = Color.blue
  }
  callateName.peer.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR))
  callateName.peer.addMouseListener(new MouseAdapter() {
    override def mouseClicked(e: MouseEvent) {
      if (e.getClickCount > 0) {
        if (Desktop.isDesktopSupported) {
          val desktop: Desktop = Desktop.getDesktop
          try {
            val uri: URI = new URI("http://callate.io")
            desktop.browse(uri)
          } catch {
            case _: IOException =>
            case _: URISyntaxException =>
          }
        }
      }
    }
  })


  val nurLabel = new Label {
    text = "@subnurmality"
    foreground = Color.blue
  }
  nurLabel.peer.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR))
  nurLabel.peer.addMouseListener(new MouseAdapter() {
    override def mouseClicked(e: MouseEvent) {
      if (e.getClickCount > 0) {
        if (Desktop.isDesktopSupported) {
          val desktop: Desktop = Desktop.getDesktop
          try {
            val uri: URI = new URI("http://twitter.com/subnurmality")
            desktop.browse(uri)
          } catch {
            case _: IOException =>
            case _: URISyntaxException =>
          }
        }
      }
    }
  })

  val toroLabel = new Label {
    text = "@wynkth"
    foreground = Color.blue
  }
  toroLabel.peer.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR))
  toroLabel.peer.addMouseListener(new MouseAdapter() {
    override def mouseClicked(e: MouseEvent) {
      if (e.getClickCount > 0) {
        if (Desktop.isDesktopSupported) {
          val desktop: Desktop = Desktop.getDesktop
          try {
            val uri: URI = new URI("http://twitter.com/wynkth")
            desktop.browse(uri)
          } catch {
            case _: IOException =>
            case _: URISyntaxException =>
          }
        }
      }
    }
  })

  val nurFlowPanel = new FlowPanel {
    contents += new Label("UI")
    contents += nurLabel
  }

  val toroFlowPanel = new FlowPanel {
    contents += new Label("Code")
    contents += toroLabel
  }

  val flowPanel = new FlowPanel {
  contents += createdBy
  contents += callateName
  }

  val borderLayout = new BorderPanel {
    layout(flowPanel) = North
    layout(toroFlowPanel) = Center
    layout(nurFlowPanel) = South
  }

  val gridPanel = new BorderPanel {
    layout(imagePanel) = Center
    layout(borderLayout) = South
  }
  // Add the grid, set the size
  contents = new BorderPanel {
    layout(gridPanel) = Center
  }
  size = new Dimension(320, 260)

  peer.setLocationRelativeTo(null)
}