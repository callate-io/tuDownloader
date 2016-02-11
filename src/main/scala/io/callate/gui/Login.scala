package io.callate.gui

import io.callate.main.Main
import scala.swing._
import scala.swing.BorderPanel.Position._
import event._

object Login extends MainFrame {
  title = "tuDownloader"
  resizable = false

  if (System.getProperty("os.name") != "Mac OS X") {
    menuBar = new MenuBar {
      contents += new MenuItem(Action("Acerca de..") {
        About.open()
      })
    }
  }

  // Define components
  val emailLabel = new Label {
    text = "Email: "
  }

  val passLabel = new Label {
    text = "Contraseña: "
  }

  val button = new Button {
    text = "Conectar"
    enabled = true
    tooltip = "Haz click para iniciar sesión"
  }

  val emailText = new TextField {
    columns = 20
    text = ""
  }

  val passText = new PasswordField {
    columns = 20
    text = ""
  }

  val gridPanel = new GridPanel(5, 1) {
    contents += emailLabel
    contents += emailText
    contents += passLabel
    contents += passText
    contents += button
  }

  // Define alignments
  emailLabel.horizontalAlignment = Alignment.Left
  passLabel.horizontalAlignment = Alignment.Left

  // Add the grid, set the size
  contents = new BorderPanel {
    layout(gridPanel) = Center
  }

  // Listen to events
  listenTo(button)
  listenTo(emailText.keys)
  listenTo(passText.keys)

  // Add the reactions to the events
  reactions += {
    case ButtonClicked(component) if component == button => Main.login(emailText.text, new String(passText.password))
    case KeyPressed(_, Key.Enter, _, _) => Main.login(emailText.text, new String(passText.password))
  }

  // Set frame location
  peer.setLocationRelativeTo(null)

  def error() = {
      Dialog.showMessage(contents.head,
        "Error al conectar, asegúrate de que has introducido los datos correctamente",
        title="Error",
        Dialog.Message.Error)
  }
}