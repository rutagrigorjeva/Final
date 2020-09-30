
object Final extends App {

  println("Current ratio calculation")

  println(
    """The current ratio is a liquidity ratio that measures a company's ability to pay short-term obligations
      |or those due within one year. It tells investors and analysts how a company can maximize the current assets
      |on its balance sheet to satisfy its current debt and other payables.
  """.stripMargin)


  var filePath = "C:\\Users\\rutag\\IdeaProjects\\FinalProject\\balance_sheets.csv"
  val rawSplit = getParsedLines(filePath)
  val ourBalance = getBalanceSeq(rawSplit.slice(1, rawSplit.size))
  //counting balances
  val total = ourBalance.size
  println(s"All togheter $total entities in Latvia have delivered financial statements in last financial year")

  /**
   * function takes Seq[Seq[String]] which is seq of rows containing seq of values and converts it to seq of Balance
   *
   * @param splitLineSeq - Seq[Seq[String]]
   * @return Seq[Balance]
   */
  def getBalanceSeq(splitLineSeq: Seq[Seq[String]]): Seq[Balance] = {
    splitLineSeq.map(t => Balance(t(0).toInt, t(1).toInt, t(2).toInt, t(3).toInt, t(4).toInt,
      t(5).toInt, t(6).toInt, t(7).toInt, t(8).toInt, t(9).toInt, t(10).toInt, t(11).toInt,
      t(12).toInt, t(13).toInt, t(14).toInt, t(15).toInt, t(16).toInt, t(17).toInt))
  }

  println(
    """
      |A good current ratio is between 1.2 to 2, which means that the business has
      |2 times more current assets than liabilities to covers its debts.
   """.stripMargin)

  /**
   * Function calculates current ratio for balance
   *
   * @param balance
   * @return Long
   */
  def currentRatio(balance: Balance): Long = {
    balance.total_current_assets / balance.current_liabilities
  }

  /**
   * Function calculates the ratio for each balance and assign the result to the same balance instance for later usage
   *
   * @param seq Seq[Balance]
   * @return seq Seq[Balance] with calculated current_ratios
   */
  def createRatios(seq: Seq[Balance]) = {
    seq.foreach(balance => balance.current_ratio = currentRatio(balance))
    seq
  }

  // filtering balances without any liabilities as not valid to divide with 0
  val acceptableCR = ourBalance.filter(_.current_liabilities > 0)

  val withRatios = createRatios(acceptableCR)

  // filtering values that conform benchmark value
  val acceptableRatio = acceptableCR.filter(_.current_ratio >= 2)

  // acknowledge how many entities have reached benchmark value
  val countEntities = acceptableRatio.size
  println(s"$countEntities entities in Latvia have achieved current ratio equal or above 2 in last financial year" +
    s"")
  println()
  println(
    """If the current ratio is too high it may indicate that the company is not efficiently using
      |its current assets or its short-term financing facilities.""".stripMargin)

  println("")


  //sort ratio results in descending order
  val rank = acceptableRatio.sortBy(_.current_ratio).reverse

  val topsToChoose = 99;

  // filtering $topsToChoose entities with highest current ratio
  val top = rank.slice(0, topsToChoose)

  println(
  s"""Acknowledging $topsToChoose companies with highest current ratios and preparing to store ratios,
  |along with current assets and current liabilities.
  |""".stripMargin)

  //connect to db
  val conn = CreateDB.connectToDb();

  //clean the existing values from DB table
  conn.createStatement().execute("DELETE FROM RATIOS")

  //store top ratios into db
  top.foreach(storeItemIntoDB)

  println("Information is being stored")

  /**
   * Function for inserting values from Balance case class into DB table RATIOS
   * @param item
   */
  def storeItemIntoDB(item: Balance) = {
    val insertStatement =
      s"""INSERT INTO RATIOS (
         |statement_id,
         |current_ratio,
         |total_current_assets,
         |current_liabilities)
         |VALUES (${item.statement_id},${item.current_ratio},${item.total_current_assets},${item.current_liabilities})""".stripMargin;

    conn.createStatement().execute(insertStatement)
  }

  /**
   * Reads a CSV file into SEQ[ROWS] where each ROW is SEQ[VALUES] of that row
   *
   * @param fileName - the csv file to be read and parsed
   * @return Seq[Seq[String]]
   */
  def getParsedLines(fileName: String) = {
    var myListBuf = scala.collection.mutable.ListBuffer[Seq[String]]()
    val bufferedSource = io.Source.fromFile(fileName)
    for (line <- bufferedSource.getLines) {
      val splitLine = line.split(";")
      myListBuf += splitLine
    }
    bufferedSource.close
    myListBuf.toSeq
  }
}