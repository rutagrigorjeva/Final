
import java.io.File

object vers extends App {
  println(System.getProperty("user.dir"))

  def getListOfFiles(dir: String): List[File] = {
    val d = new File(dir)
    if (d.exists && d.isDirectory) {
      d.listFiles.filter(_.isFile).toList
    } else {
      List[File]()
    }
  }

  val f_list = getListOfFiles("C:\\Users\\rutag\\IdeaProjects\\FinalProject\\balance_sheets.csv")
  f_list.foreach(println)
  getListOfFiles("C:\\Users\\rutag\\IdeaProjects\\FinalProject\\balance_sheets.csv").foreach(println)
  getListOfFiles("C:\\Users\\rutag\\IdeaProjects\\FinalProject\\balance_sheets.csv").foreach(println)

  def getLineCount(fileName: String): Int = {
    var count = 0
    val bufferedSource = io.Source.fromFile(fileName)
    for (line <- bufferedSource.getLines) {
      count += 1
    }
    bufferedSource.close
    count
  }

  def getRowSplitSize(fileName: String): Int = {
    var count = 0
    val bufferedSource = io.Source.fromFile(fileName)
    for (line <- bufferedSource.getLines) {
      val splitLine = line.split(";")
      count += splitLine.size
    }
    bufferedSource.close
    count
  }

  def getLinesWithSplitSize(fileName: String, splitSize: Int): Int = {
    var count = 0
    val bufferedSource = io.Source.fromFile(fileName)
    for (line <- bufferedSource.getLines) {
      val splitLine = line.split(";")
      if (splitLine.size == splitSize) {
        println(line)
        count += 1
      }
    }
    bufferedSource.close
    count //return how many lines are there
  }

  def getLineSplits(fileName: String): Seq[Int] = {
    var myListBuf = scala.collection.mutable.ListBuffer[Int]()
    val bufferedSource = io.Source.fromFile(fileName)
    for (line <- bufferedSource.getLines) {
      val splitLine = line.split(";")
      myListBuf += splitLine.size
    }
    bufferedSource.close
    myListBuf.toSeq //return how many lines are there
  }

  def getParsedLines(fileName: String) = {
    var myListBuf = scala.collection.mutable.ListBuffer[Seq[String]]()
    val bufferedSource = io.Source.fromFile(fileName)
    for (line <- bufferedSource.getLines) {
      val splitLine = line.split(";")
      myListBuf += splitLine
    }
    bufferedSource.close
    myListBuf.toSeq //return how many lines are there
  }
}