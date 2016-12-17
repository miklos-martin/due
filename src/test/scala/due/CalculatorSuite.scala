package due

import java.time.LocalDateTime
import org.scalatest.FunSuite

class CalculatorSuite extends FunSuite {
  import Calculator._
  import Parser._

  trait TestDates {
    val monday10am = LocalDateTime.parse("2016-12-19T10:00:00")
    val monday4pm = LocalDateTime.parse("2016-12-19T16:00:00")
    val tuesday10am = LocalDateTime.parse("2016-12-20T10:00:00")
    val fridayNoon = LocalDateTime.parse("2016-12-16T12:00:00")
    val friday4pm = LocalDateTime.parse("2016-12-16T16:00:00")

    val dueDate = calculateDueDate(WorkingHours.fivePerEight)(_, _)
  }

  test("requires start date to be in within working hours") {
    new TestDates {
      assertThrows[IllegalArgumentException] {
        dueDate(friday4pm.plusHours(2), 0)
      }
    }
  }

  test("requires minutes to be non negative") {
    new TestDates {
      assertThrows[IllegalArgumentException] {
        dueDate(friday4pm, -1)
      }
    }
  }

  test("0 turnaround yields the same date") {
    new TestDates {
      assert(dueDate(fridayNoon, 0) == fridayNoon)
    }
  }

  test("within workingday") {
    new TestDates {
      assert(dueDate(fridayNoon, 10) == fridayNoon.plusMinutes(10))
    }
  }

  test("overlap next day") {
    new TestDates {
      assert(dueDate(monday4pm, 120) == tuesday10am)
    }
  }

  test("overlap next week") {
    new TestDates {
      assert(dueDate(friday4pm, 120) == monday10am)
    }
  }
}
