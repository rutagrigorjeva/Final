import java.sql.{Connection, DriverManager, PreparedStatement}

object CreateDB extends App {

  val createTableRatiosSql =
    """CREATE TABLE IF NOT EXISTS RATIOS (
      |statement_id INTEGER PRIMARY KEY,
      |current_ratio INTEGER NOT NULL,
      |total_current_assets INTEGER NOT NULL,
      |current_liabilities INTEGER NOT NULL)""".stripMargin;

  // connects to db and creates the table RATIOS
  connectToDb().createStatement().execute(createTableRatiosSql)

  def connectToDb(): Connection = {
    val sqlite_home = System.getenv().get("SQLITE_HOME").replace("\\", "/")
    val dbname = "ratios.db"
    val url = s"jdbc:sqlite:$sqlite_home/db/$dbname"
    println(s"Connecting to DB $url")
    DriverManager.getConnection(url)
  }
}
