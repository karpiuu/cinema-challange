package cinema.domain.room.timeslot

import java.time.OffsetDateTime
import java.time.temporal.ChronoUnit.MILLIS
import scala.concurrent.duration.Duration

trait Timeslot {
  val startTime: OffsetDateTime
  val duration: Duration
  lazy val endTime: OffsetDateTime = startTime.plus(duration.toMillis, MILLIS)

  final def overlapsWith(timeslot: Timeslot): Boolean =
    endTime.isAfter(timeslot.startTime) && timeslot.endTime.isAfter(startTime)

}
