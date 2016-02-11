package io.callate.gui

import com.apple.eawt.{Application, AboutHandler}
import com.apple.eawt.AppEvent.AboutEvent


class OSX {
  System.setProperty("apple.awt.application.name", "tuDownloader")
  val app = Application.getApplication
  app.setAboutHandler(new AboutHandler {
    override def handleAbout(aboutEvent: AboutEvent): Unit = {
      About.open()
    }
  })
}
