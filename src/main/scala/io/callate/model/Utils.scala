package io.callate.model

import java.io.{InputStream, FileOutputStream, BufferedOutputStream, File}
import java.net.URL
import java.nio.file.{Path, Files}

import scala.sys.process._

object Utils {
  def fileDownloader(url: String, filename: String) = {
    new URL(url) #> new File(filename) !!
  }

  def fileDownloader(is: InputStream, filename: Path) = {
    Files.copy(is, filename)
  }

  def mkdir(path: String) = new java.io.File(path).mkdirs

  def mkdirs(path: List[String]) =
    path.tail.foldLeft(new File(path.head)){(a,b) => a.mkdir; new File(a,b)}.mkdir
}
