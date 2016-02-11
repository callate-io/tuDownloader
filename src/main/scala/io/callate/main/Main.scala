package io.callate.main

import java.io.{FileNotFoundException, File}
import java.net.{URL, HttpURLConnection}
import java.nio.file.Paths
import javax.swing.JMenuBar

import io.callate.model.{Utils, TuDownloader}
import io.callate.gui._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits._

import scala.swing.{Swing, SimpleSwingApplication}

object Main extends App {
  if (System.getProperty("os.name").equals("Mac OS X")) {
    new OSX()
  }
  val loginUI = Login
  var menuUI: MenuPanel = null
  var photoModal: PhotoModal = null
  val tuDownloader = new TuDownloader()
  var albumsTuple: List[(String, String, Int)] = null

  loginUI.visible = true

  def login(email: String, password: String) = {
    try {
      if (tuDownloader.login(email, password)) {
        loginUI.visible = false

        albumsTuple = tuDownloader.getAlbums.map(x => (x._1, x._2._1, x._2._2)).toList

        menuUI = new MenuPanel(albumsTuple)
        menuUI.visible = true

      } else {
        loginUI.error()
      }
    } catch {
      case e: Exception => loginUI.error()
    }

  }

  def downloadAlbums(albumIds: List[String], total: Int) = {
    val folder = "Fotos"
    if (!new File(folder).exists) {
      Utils.mkdir(folder)
    }

    photoModal = new PhotoModal(total)

    menuUI.visible = false
    photoModal.visible = true

    Future {
      for (id <- albumIds) {
        val albumName = tuDownloader.getAlbums.get(id).get._1
        if (!new File(folder, albumName).exists()) {
          Utils.mkdirs(List(folder, albumName))
        }
        var photoCount = 1
        for (photoURL <- tuDownloader.getPhotosAlbumURLs(id)) {
            val photoImg = tuDownloader.getPhotoUrl(photoURL)
            val httpURLConnection: HttpURLConnection = new URL(photoImg).openConnection().asInstanceOf[HttpURLConnection]
            httpURLConnection.setRequestMethod("GET")
            httpURLConnection.connect()
            // Photos that result in 404 requests
            if (httpURLConnection.getResponseCode != 404) {
              val outputFile = Paths.get(folder, albumName, photoCount + ".jpg")
              Utils.fileDownloader(httpURLConnection.getInputStream, outputFile)
              photoCount += 1
            }
            photoModal.newPhoto()
        }
      }
    }
  }

  def finishedPhotos() = {
    photoModal.visible = false
    menuUI.visible = true
  }

  def downloadPrivateMessages() = {
    Utils.fileDownloader(tuDownloader.getPrivateMessagesURL, "Mensajes privados.zip")
  }
}
