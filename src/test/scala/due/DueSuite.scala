package due

import java.time.LocalDateTime
import org.scalatest.FunSuite

class DueSuite extends FunSuite {
  import Parser._

  test("Requires the correct format for inputs") {
    val invalidSamples = List(
      "something",
      "date, time",
      "2016-12-16T10:10:10, valid date but invalid timespan"
    )

    invalidSamples foreach { sample =>
      assert(None == parse(sample))
    }
  }

  test("Correctly parses input lines") {
    val date = "2016-12-16T10:11:00";
    val minutes = (4*8 + 3) * 60

    assert(parse(s"$date, 4d 3h") == Some(Input(LocalDateTime.parse(date), minutes)))
  }
}
