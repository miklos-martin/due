package due

import java.time.{ DayOfWeek, LocalDateTime }

object WorkingHours {
  import DayOfWeek._

  def fivePerEight(date: LocalDateTime): Boolean = {
    isWeekDay(date) && isBetween9And5(date)
  }

  def isWeekDay(date: LocalDateTime): Boolean = !Set(SATURDAY, SUNDAY)(date.getDayOfWeek)

  def isBetween9And5(date: LocalDateTime): Boolean = {
    val nineAm = date.withHour(9).withMinute(0)
    val fivePm = date.withHour(17).withMinute(0)

    date.isAfter(nineAm) && date.compareTo(fivePm) <= 0
  }
}
