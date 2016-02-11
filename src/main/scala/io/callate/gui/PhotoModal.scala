package io.callate.gui

import scala.swing._
import scala.swing.BorderPanel.Position._

import io.callate.main.Main

class PhotoModal(total: Int) extends MainFrame {
  title = "Descargando fotos..."
  resizable = false
  val progressBar = new ProgressBar()
  val progressPane = new ScrollPane(progressBar)
  val progressText = new Label()

  progressBar.preferredSize = new Dimension(400, 20)
  progressText.peer.setText("0 / " + total)

  contents = new BorderPanel {
    layout(progressPane) = Center
    layout(progressText) = South
  }

  progressBar.peer.setMaximum(total)
  progressBar.peer.setMinimum(0)

  peer.pack()
  peer.setLocationRelativeTo(null)


  def newPhoto() = {
    progressBar.peer.setValue(progressBar.peer.getValue + 1)
    progressText.peer.setText(progressBar.peer.getValue + " / " + total)

    if (progressBar.peer.getValue == progressBar.peer.getMaximum) {
      Dialog.showMessage(contents.head,
        "Fotos descargadas correctamente (carpeta 'Fotos')",
        title="Fotos",
        Dialog.Message.Info)
      Main.finishedPhotos()
    }
  }
}
