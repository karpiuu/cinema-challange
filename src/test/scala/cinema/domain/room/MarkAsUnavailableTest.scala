package cinema.domain.room

import cinema.domain.Minute
import cinema.domain.room.RoomTestSupportHelper.TimeslotWrapper
import cinema.domain.room.RoomTestSupportHelper.UnavailableWithoutId
import cinema.domain.room.timeslot.Unavailable
import io.jvm.uuid.UUID
import org.mockito.ArgumentMatchers.any

import scala.concurrent.duration.DurationInt

class MarkAsUnavailableTest extends RoomTestSupport {

  "Room" should {
    "mark room as unavailable" when {
      "it fits into timeslot" in {
        val roomWithShowings =
          Room(UUID.random, cleaningDuration, preBookedSlots)

        val unavailable = Unavailable(any[UUID], 14 :: 30, 2.hours).withoutId

        val room = roomWithShowings.markRoomAsUnavailable(14 :: 30, 16 :: 30)

        val t = room.value.bookedTimeslots.withoutId

        room.value.bookedTimeslots should have length (preBookedSlots.length + 1)
        room.value.bookedTimeslots.withoutId should contain(unavailable)
      }
    }

    "fail" when {
      "marking room as unavailable but timeslot is occupied" in {
        val roomWithShowings =
          Room(UUID.random, cleaningDuration, preBookedSlots)

        val room = roomWithShowings.markRoomAsUnavailable(12 :: 30, 20 :: 30)

        assert(room.isLeft)
      }
    }
  }

}
