package io.callate.gui

import javax.swing.table.AbstractTableModel

import io.callate.main.Main

import scala.swing._
import scala.swing.BorderPanel.Position._
import event._

class AlbumModel(var cells: Array[Array[Any]], val columns: Array[String]) extends AbstractTableModel {
  def getRowCount: Int = cells.length
  def getColumnCount: Int = columns.length
  def getValueAt(row: Int, col: Int): AnyRef = cells(row)(col).asInstanceOf[AnyRef]
  override def getColumnClass(column: Int) = getValueAt(0, column).getClass
  override def isCellEditable(row: Int, column: Int) = if (column == 2) true else false
  override def setValueAt(value: Any, row: Int, col: Int) {
    cells(row)(col) = value
    fireTableCellUpdated(row, col)
  }
  override def getColumnName(column: Int): String = columns(column).toString
}

class MenuPanel(val albums: List[(String, String, Int)]) extends MainFrame {
  if (System.getProperty("os.name") != "Mac OS X") {
    menuBar = new MenuBar {
      contents += new MenuItem(Action("Acerca de..") {
        About.open()
      })
    }
  }

  title = "tuDownloader"
  resizable = false
  // Define components
  val header = Array("Nombre del álbum", "Fotos", "Descargar")
  val items = albums.map(_.productIterator.toList.slice(1, 3).++(List(true)).toArray).toArray
  val albumsTable = new Table(items, header) {
    model = new AlbumModel(items, header)

    // Set widths
    peer.getColumnModel.getColumn(0).setPreferredWidth(400)
    peer.getColumnModel.getColumn(1).setPreferredWidth(100)
    peer.getColumnModel.getColumn(2).setPreferredWidth(100)
  }
  val albumsScroll = new ScrollPane(albumsTable)
  val downloadAlbums = new Button {
    text = "Descargar marcados"
    enabled = true
    tooltip = "Haz click para bajar los álbumes seleccionados"
  }
  val downloadPMs = new Button {
    text = "Descargar MPs"
    enabled = true
    tooltip = "Haz click para bajar tus mensajes privados"
  }
  val deselectAll = new Button {
    text = "Desmarcar todos"
    enabled = true
    tooltip = "Haz click para desmarcar todos tus álbumes"
  }
  val selectAll = new Button {
    text = "Marcar todos"
    enabled = true
    tooltip = "Haz click para marcar todos tus álbumes"
  }
  val buttonTopPanel = new FlowPanel {
    contents += deselectAll
    contents += selectAll
  }
  val buttonBottomPanel = new FlowPanel {
    contents += downloadAlbums
    contents += downloadPMs
  }
  val buttonPanel = new BorderPanel {
    layout(buttonTopPanel) = North
    layout(buttonBottomPanel) = South
  }
  val gridPanel = new BorderPanel {
    layout(albumsScroll) = Center
    layout(buttonPanel) = South
  }
  // Add the grid, set the size
  contents = new BorderPanel {
    layout(gridPanel) = Center
  }
  size = new Dimension(600, 400)
  // Listen to events
  listenTo(downloadAlbums)
  listenTo(downloadPMs)
  listenTo(deselectAll)
  listenTo(selectAll)

  // Add the reactions to the events
  reactions += {
    case ButtonClicked(component) if component == deselectAll => albums.indices.foreach(albumsTable.model.setValueAt(false, _, 2))
    case ButtonClicked(component) if component == selectAll => albums.indices.foreach(albumsTable.model.setValueAt(true, _, 2))
    case ButtonClicked(component) if component == downloadAlbums => downloadMarkedAlbumsIDs()
    case ButtonClicked(component) if component == downloadPMs =>
      Main.downloadPrivateMessages()
      Dialog.showMessage(contents.head,
        "Mensajes privados descargados correctamente (Mensajes privados.zip)",
        title="Mensajes privados",
        Dialog.Message.Info)

  }

  peer.setLocationRelativeTo(null)

  def downloadMarkedAlbumsIDs() = {
    val albumsTuple = albums.zipWithIndex.filter(p => albumsTable.model.getValueAt(p._2, 2).asInstanceOf[Boolean]).map(p => p._1)
    val numPhotos = albumsTuple.map(_._3).sum
    val albumsIds = albumsTuple.map(_._1)

    if (numPhotos != 0) {
      Main.downloadAlbums(albumsIds, numPhotos)
    }
  }
}