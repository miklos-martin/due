package due

import scala.util.Try
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Due {
  import Parser._
  import WorkingHours._

  val calculateDueDate = Calculator.calculateDueDate(fivePerEight)(_, _)

  def main(args: Array[String]) = {
    println("""|
               |Hey, gimme the submit date and the time the task will take
               |Enter empty line to QUIT
               |
               |Usage:
               |  SUBMIT_DATE, TURNAROUND_TIME => DUE_DATE
               |
               |e.g.
               |
               |  > 2016-12-16T10:31:00, 4d 3h
               |  2016-12-22T13:31:00
               |""".stripMargin)

    prompt

    Iterator.continually(readLine)
      .takeWhile(defined _)
      .foreach(handle _)
  }

  def prompt = print("> ")

  def defined(line: String): Boolean = line != null && line.nonEmpty

  def handle(inputLine: String) = {
    val dueDate = parse(inputLine)
      .map(input => calculateDueDate(input.date, input.minutes))
      .getOrElse("Failed, try to correct your input")

    println(dueDate)
    prompt
  }
}

case class Input(date: LocalDateTime, minutes: Int)

object Parser {

  val timeUnitMultipliers = Map(
    'm' -> 1,
    'h' -> 60,
    'd' -> 60 * 8,
    'w' -> 60 * 8 * 5
  )

  def parse(line: String): Option[Input] = {
    val result = for {
      (dateString, intervalExpression) <- splitInputLine(line)
      date <- parseDate(dateString)
      minutes <- parseMinutes(intervalExpression)
    } yield Input(date, minutes)

    result.toOption
  }

  def splitInputLine(line: String): Try[(String, String)] = {
    Try {
      val splitted = line.split(",").map(_.trim)
      (splitted(0), splitted(1))
    }
  }

  def parseDate(dateString: String): Try[LocalDateTime] =
    Try(LocalDateTime.parse(dateString))

  def parseMinutes(intervalExpression: String): Try[Int] = Try {
    intervalExpression.split("\\s+")
      .map(simpleExpressionToMinutes(_))
      .sum
  }

  def simpleExpressionToMinutes(intervalExpression: String): Int =
    intervalExpression.init.toInt * timeUnitMultipliers(intervalExpression.last)
}
