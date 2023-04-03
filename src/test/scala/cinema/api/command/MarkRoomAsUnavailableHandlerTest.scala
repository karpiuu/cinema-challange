package cinema.api.command

import cinema.domain._
import org.mockito.Mockito.reset
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.when

class MarkRoomAsUnavailableHandlerTest extends HandlerTestSupport {

  before {
    when(mockedRoom.markRoomAsUnavailable(14 :: 30, 16 :: 30)).thenReturn(Right(updatedRoom))
  }

  after {
    reset(mockedRoom)
  }

  "Schedule service" should {
    "save room with unavailable timeslot into db" in {
      val movieRepo = movieRepository
      val roomRepo  = roomRepository
      val handler   = MarkRoomAsUnavailableHandler(movieRepo, roomRepo)
      val command   = MarkRoomAsUnavailable(14 :: 30, 16 :: 30, anyRoomId)

      handler.handle(command)

      verify(mockedRoom, times(1)).markRoomAsUnavailable(14 :: 30, 16 :: 30)
      verify(roomRepo, times(1)).save(updatedRoom)
    }
  }

}
