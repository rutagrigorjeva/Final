object Final extends App {


  var count = 0
  println("Balance")
  var filePath = "C:\\Users\\rutag\\IdeaProjects\\FinalProject\\balance_sheets.csv"

  val rawSplit = getParsedLines(filePath)

  val ourBalances = getBalanceSeq(rawSplit.slice(1,rawSplit.size-1))


  ourBalances.filter(_.current_liabilities>0).filter(currentRatio(_)>=2).foreach(Balance => println(currentRatio(Balance)))


  //  val bufferedSource = io.Source.fromFile("C:\\Users\\rutag\\IdeaProjects\\FinalProject\\balance_sheets.csv")
  //  for (line <- bufferedSource.getLines) {
  //    val cols = line.split(";").map(_.trim)
  //    // do whatever you want with the columns here
  //
  //    println(cols.size)
  //    count += 1;
  //
  //  }
  //  bufferedSource.close
  //  println(count)

  def currentRatio(balance: Balance): Double = {
    balance.total_current_assets/balance.current_liabilities
  }

  def getBalanceSeq(splitLineSeq: Seq[Seq[String]]): Seq[Balance] = {
    splitLineSeq.map(t => Balance(t(0).toInt, t(1).toInt, t(2).toInt, t(3).toInt, t(4).toInt, t(5).toInt, t(6).toInt, t(7).toInt, t(8).toInt, t(9).toInt, t(10).toInt, t(11).toInt, t(12).toInt, t(13).toInt, t(14).toInt, t(15).toInt, t(16).toInt, t(17).toInt))
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