package cinema.domain.room

import cinema.domain.Minute
import cinema.domain.room.RoomTestSupportHelper.CleaningWithoutId
import cinema.domain.room.RoomTestSupportHelper.ShowingWithoutId
import cinema.domain.room.RoomTestSupportHelper.TimeslotWrapper
import cinema.domain.room.timeslot.Cleaning
import cinema.domain.room.timeslot.Showing
import io.jvm.uuid.UUID

import scala.concurrent.duration.DurationInt

class BookShowingTest extends RoomTestSupport {

  "Room" should {
    "book showing correctly" when {
      "booked timeslots are empty" in {
        val showing =
          Showing(UUID.random, anyMovieId, 15 :: 30, 2.hours).withoutId

        val room = anyRoom.bookShowing(15 :: 30, anyMovie)

        room.value.bookedTimeslots.withoutId should contain(showing)
      }

      "it fits into timeslot" in {
        val roomWithShowings =
          Room(UUID.random, cleaningDuration, preBookedSlots)

        val showing =
          Showing(UUID.random, anyMovieId, 14 :: 10, 2.hours).withoutId

        val room = roomWithShowings.bookShowing(14 :: 10, anyMovie)

        room.value.bookedTimeslots should have length (preBookedSlots.length + 2)
        room.value.bookedTimeslots.withoutId should contain(showing)
        room.value.bookedTimeslots.withoutId should contain(Cleaning(16 :: 10, cleaningDuration).withoutId)
      }
    }

    "add cleaning time after showing" in {
      val room = anyRoom.bookShowing(15 :: 30, anyMovie)

      room.value.bookedTimeslots.withoutId should contain(Cleaning(17 :: 30, cleaningDuration).withoutId)
    }

    "fail" when {
      "booking showing but timeslot is occupied" in {
        val roomWithShowings =
          Room(UUID.random, cleaningDuration, preBookedSlots)

        val room = roomWithShowings.bookShowing(15 :: 10, anyMovie)

        assert(room.isLeft)
      }

      "there isn't enough time for cleaning" in {
        val roomWithShowings =
          Room(UUID.random, cleaningDuration, preBookedSlots)

        val room = roomWithShowings.bookShowing(14 :: 30, anyMovie)

        assert(room.isLeft)
      }
    }
  }

}
