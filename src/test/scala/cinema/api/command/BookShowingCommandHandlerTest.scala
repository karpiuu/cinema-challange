package cinema.api.command

import cinema.domain._
import cinema.domain.movie.Movie
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.reset
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.when

class BookShowingCommandHandlerTest extends HandlerTestSupport {

  before {
    when(mockedRoom.bookShowing(14 :: 30, anyMovie)).thenReturn(Right(updatedRoom))
  }

  after {
    reset(mockedRoom)
  }

  "Schedule service" should {
    "save room with updated booking timeslots into db" in {
      val movieRepo = movieRepository
      val roomRepo  = roomRepository
      val handler   = BookShowingCommandHandler(movieRepo, roomRepo)
      val command   = BookShowingCommand(14 :: 30, anyMovieId, anyRoomId)

      handler.handle(command)

      verify(mockedRoom, times(1)).bookShowing(14 :: 30, anyMovie)
      verify(roomRepo, times(1)).save(updatedRoom)
    }
  }

}
