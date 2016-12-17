package due

import java.time.LocalDateTime

object Calculator {
  def calculateDueDate(isWithinWorkingHours: LocalDateTime => Boolean)(submitDate: LocalDateTime, minutesItTakes: Int): LocalDateTime = {
    require(isWithinWorkingHours(submitDate))
    require(minutesItTakes >= 0)

    if (minutesItTakes == 0) submitDate
    else
      minutesStream(submitDate)
        .filter(isWithinWorkingHours)
        .tail
        .take(minutesItTakes)
        .last
  }

  def minutesStream(startDate: LocalDateTime): Stream[LocalDateTime] =
    startDate #:: minutesStream(startDate.plusMinutes(1))
}
