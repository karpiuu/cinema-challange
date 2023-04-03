package cinema.domain.room.timeslot

import io.jvm.uuid.UUID

import java.time.OffsetDateTime
import scala.concurrent.duration.Duration

case class Cleaning(
  id: UUID,
  startTime: OffsetDateTime,
  duration: Duration
) extends Timeslot

object Cleaning {

  def apply(startTime: OffsetDateTime, duration: Duration): Cleaning = {
    new Cleaning(UUID.random, startTime, duration)
  }

}
