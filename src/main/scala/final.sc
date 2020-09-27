object Final extends App {

  val bufferedSource = io.Source.fromFile("C:\\Users\\rutag\\IdeaProjects\\FinalProject\\balance_sheets.csv")
  for (line <- bufferedSource.getLines) {
    val cols = line.split(",").map(_.trim)

  }
  bufferedSource.close
}