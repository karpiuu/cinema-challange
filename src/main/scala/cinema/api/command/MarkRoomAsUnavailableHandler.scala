package cinema.api.command

import cinema.domain.movie.MovieRepository
import cinema.domain.room.Room
import cinema.domain.room.RoomRepository

case class MarkRoomAsUnavailableHandler(
  movieRepository: MovieRepository,
  roomRepository: RoomRepository
) extends CommandHandler {
  override type CommandType = MarkRoomAsUnavailable

  def handle(command: CommandType): Unit = {
    val room: Room = roomRepository.get(command.roomId)
    for {
      updatedRoom <- room.markRoomAsUnavailable(command.startTime, command.endTime)
    } yield roomRepository.save(updatedRoom)
  }

}
