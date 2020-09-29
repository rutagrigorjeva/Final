
object Final extends App {

  println("Current ratio calculation")
  val definitionCurrentR =
  """The current ratio is a liquidity ratio that measures a company's ability to pay short-term obligations
  |or those due within one year. It tells investors and analysts how a company can maximize the current assets
  |on its balance sheet to satisfy its current debt and other payables.
  """.stripMargin
  println(definitionCurrentR)

  var filePath = "C:\\Users\\rutag\\IdeaProjects\\FinalProject\\balance_sheets.csv"
  val rawSplit = getParsedLines(filePath)
  val ourBalance = getBalanceSeq(rawSplit.slice(1, rawSplit.size))
  val total = ourBalance.size
  println(s"$total entities in Latvia have delivered financial statements in last financial year")

  /**
   * describe what this function does
   * @param splitLineSeq - describe what is taken as a paremeter
   * @return describe what is returned
   */
  def getBalanceSeq(splitLineSeq: Seq[Seq[String]]): Seq[Balance] = {
    splitLineSeq.map(t => Balance(t(0).toInt, t(1).toInt, t(2).toInt, t(3).toInt, t(4).toInt,
      t(5).toInt, t(6).toInt, t(7).toInt, t(8).toInt, t(9).toInt, t(10).toInt, t(11).toInt,
      t(12).toInt, t(13).toInt, t(14).toInt, t(15).toInt, t(16).toInt, t(17).toInt))
  }

  val benchmark =
    """
   |A good current ratio is between 1.2 to 2, which means that the business has
   |2 times more current assets than liabilities to covers its debts.
   """.stripMargin
  println(benchmark)


  def currentRatio(balance: Balance): Long = {
    balance.total_current_assets / balance.current_liabilities
  } // function to calculate current ratio

  /**
   *
   * @param seq
   * @return
   */
  def createRatios(seq: Seq[Balance]) = {
    seq.foreach(balance => balance.current_ratio = currentRatio(balance))
    seq
  }

  val acceptableCR = ourBalance.filter(_.current_liabilities > 0)
  val withRatios = createRatios(acceptableCR)
//  createRatios(acceptableCR)
//  val withRatios = acceptableCR;
  val acceptableRatio = acceptableCR.filter(_.current_ratio >= 2)

  val countEntities = acceptableRatio.size
  println(s"$countEntities entities in Latvia have achieved current ratio equal or above 2 in last financial year")
  println()
  // first filtering values with any liabilities as not valid to divide with 0
  // then filtering values that conform benchmark value
  // acknowledge how many entities have reached benchmark value

  val highest = acceptableRatio.sortBy(_.current_ratio).reverse

  val top = highest.slice(0, 50)

  top.foreach(item => {
    val data =
      s"""item.statement_id: ${item.statement_id}
        | current_liabilities: ${item.current_liabilities}
        | total_current_assets: ${item.total_current_assets}
        | current_ratio: ${item.current_ratio}""".stripMargin

    println(data)
  })

//  val environmentVars = System.getenv()
//  val sqlite_home = environmentVars.get("SQLITE_HOME").replace("\\", "/")
//  val dbname = "ratios.db"
//  println(s"Connecting to DB $dbname")
//  val url = s"jdbc:sqlite:$sqlite_home/db/$dbname"
//  val conn = DriverManager.getConnection(url)

  val conn = CreateDB.connectToDb();
  conn.createStatement().execute("DELETE FROM RATIOS")


  top.foreach(storeItemIntoDB)

  def storeItemIntoDB(item: Balance) = {
    val insertStatement =
      s"""INSERT INTO RATIOS (
         |statement_id,
         |current_ratio,
         |total_current_assets,
         |current_liabilities)
         |VALUES (${item.statement_id},${item.current_ratio},${item.total_current_assets},${item.current_liabilities})""".stripMargin;
    val statement = conn.createStatement()
    val resultSet = statement.execute(insertStatement)
  }

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