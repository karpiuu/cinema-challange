package cinema.domain.room.timeslot

import io.jvm.uuid.UUID

import java.time.OffsetDateTime
import scala.concurrent.duration.Duration
import scala.concurrent.duration._

case class Unavailable(
  id: UUID,
  startTime: OffsetDateTime,
  duration: Duration
) extends Timeslot

object Unavailable {

  def apply(startTime: OffsetDateTime, endTime: OffsetDateTime): Unavailable = {
    val duration = (endTime.toEpochSecond - startTime.toEpochSecond).seconds
    Unavailable(UUID.random, startTime, duration)
  }

}
