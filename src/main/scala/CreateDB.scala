import java.sql.{Connection, DriverManager, PreparedStatement}

object CreateDB extends App {

  val conn = connectToDb()

  //lets make a table!
  val sql =
    """CREATE TABLE IF NOT EXISTS RATIOS (
      |statement_id INTEGER PRIMARY KEY,
      |current_ratio INTEGER NOT NULL,
      |total_current_assets INTEGER NOT NULL,
      |current_liabilities INTEGER NOT NULL)""".stripMargin;

  val statement = conn.createStatement()
  val resultSet = statement.execute(sql)

  def connectToDb(): Connection = {
    val environmentVars = System.getenv()
    //  environmentVars.forEach((k,v) => println(k,v))

    //  println("SCALA_HOME", environmentVars.get("SCALA_HOME"))
    println("SQLITE_HOME", environmentVars.get("SQLITE_HOME"))
    //  for ((k,v) <- environmentVars) println(s"key: $k, value: $v")

    //  val properties = System.getProperties()
    //  for ((k,v) <- properties) println(s"key: $k, value: $v")
    val sqlite_home = environmentVars.get("SQLITE_HOME").replace("\\", "/")

    val dbname = "ratios.db"
    println(s"Creating DB $dbname")
    val url = s"jdbc:sqlite:$sqlite_home/db/$dbname"
    DriverManager.getConnection(url)
  }
}




