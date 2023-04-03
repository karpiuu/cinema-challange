package cinema.domain.room.timeslot

import cinema.domain._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import java.time.OffsetDateTime
import java.util.concurrent.TimeUnit
import scala.concurrent.duration.Duration
import scala.concurrent.duration.FiniteDuration

class TimeslotTest extends AnyWordSpec with Matchers {

  "Timeslot " should {
    "overlap" when {
      "first ends after second starts" in {
        val first  = timeslot(14 :: 30, 15 :: 30)
        val second = timeslot(15 :: 15, 16 :: 30)

        first overlapsWith second shouldBe true
        second overlapsWith first shouldBe true
      }

      "first starts before second end" in {
        val first  = timeslot(15 :: 30, 17 :: 30)
        val second = timeslot(14 :: 30, 16 :: 30)

        first overlapsWith second shouldBe true
        second overlapsWith first shouldBe true
      }

      "first includes entire second timeslot" in {
        val first  = timeslot(14 :: 30, 17 :: 30)
        val second = timeslot(15 :: 30, 16 :: 30)

        first overlapsWith second shouldBe true
        second overlapsWith first shouldBe true
      }

      "second includes entire first timeslot" in {
        val first  = timeslot(15 :: 30, 16 :: 30)
        val second = timeslot(14 :: 30, 17 :: 30)

        first overlapsWith second shouldBe true
        second overlapsWith first shouldBe true
      }
    }
  }

  "Timeslot" should {
    "be separated" when {
      "first is before second" in {
        val first  = timeslot(12 :: 30, 14 :: 30)
        val second = timeslot(16 :: 30, 18 :: 30)

        first overlapsWith second shouldBe false
        second overlapsWith first shouldBe false
      }

      "first ends when second starts" in {
        val first  = timeslot(12 :: 30, 14 :: 30)
        val second = timeslot(14 :: 30, 16 :: 30)

        first overlapsWith second shouldBe false
        second overlapsWith first shouldBe false
      }
    }
  }

  def timeslot(startDate: OffsetDateTime, endDate: OffsetDateTime): Timeslot = new Timeslot {
    override val startTime: OffsetDateTime = startDate
    override val duration: Duration = FiniteDuration(endDate.toEpochSecond - startDate.toEpochSecond, TimeUnit.SECONDS)
  }

}
