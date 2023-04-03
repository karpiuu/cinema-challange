package cinema.api.command

import io.jvm.uuid.UUID

import java.time.OffsetDateTime

/**
 * Command for domain usage
 */
sealed trait Command

case class BookShowingCommand(
  startTime: OffsetDateTime,
  movieId: UUID,
  roomId: UUID
) extends Command

case class MarkRoomAsUnavailable(
  startTime: OffsetDateTime,
  endTime: OffsetDateTime,
  roomId: UUID
) extends Command
