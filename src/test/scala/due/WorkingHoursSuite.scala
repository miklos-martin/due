package due

import java.time.LocalDateTime
import org.scalatest.FunSuite

class WorkingHoursSuite extends FunSuite {
  import WorkingHours._

  test("the 5/8 checker yays M-F 9am-5pm") {
    val withinWorkingHourSamples = List(
      "2016-12-19T09:01:00",
      "2016-12-16T09:01:00",
      "2016-12-16T10:00:00",
      "2016-12-16T17:00:00"
    ).map(LocalDateTime.parse(_))

    withinWorkingHourSamples foreach { date =>
      assert(fivePerEight(date), s"Date ${date} didn't fall into 5/8 working hours")
    }
  }

  test("the 5/8 checker nays for weekends or 5pm-9am") {
    val outsideWorkingHoursSamples = List(
      "2016-12-18T10:00:00",
      "2016-12-19T09:00:00",
      "2016-12-19T17:01:00"
    ).map(LocalDateTime.parse(_))

    outsideWorkingHoursSamples foreach { date =>
      assert(!fivePerEight(date), s"Date ${date} did fall into 5/8 working hours")
    }
  }
}
