package cinema.domain.room

import cinema.domain.core.Validator.Violations
import cinema.domain.core.Validator
import cinema.domain.core.Violation
import cinema.domain.movie.Movie
import cinema.domain.room.timeslot.Cleaning
import cinema.domain.room.timeslot.Showing
import cinema.domain.room.timeslot.Timeslot
import cinema.domain.room.timeslot.Unavailable
import io.jvm.uuid.UUID

import java.time.OffsetDateTime
import scala.concurrent.duration.Duration

//TODO Maybe should be named screen?
case class Room(
  id: UUID,
  cleaningDuration: Duration,
  bookedTimeslots: List[Timeslot]
) {

  private def validateTimeslot(timeslot: Timeslot)(violation: Violation) = Validator[Timeslot]
    .validate(isTimeslotBooked)(violation)(timeslot)

  def bookShowing(startTime: OffsetDateTime, movie: Movie): Either[Violations, Room] = {
    for {
      showing      <- Showing(startTime, movie)
      validShowing <- validateTimeslot(showing)(ShowingTimeslotViolation)
      cleaning = Cleaning(validShowing.endTime, cleaningDuration)
      validCleaning <- validateTimeslot(cleaning)(CleaningTimeslotViolation)
    } yield copy(bookedTimeslots = validShowing :: validCleaning :: bookedTimeslots)
  }

  def markRoomAsUnavailable(startTime: OffsetDateTime, endTime: OffsetDateTime): Either[Violations, Room] = {
    for {
      validUnavailable <- validateTimeslot(Unavailable(startTime, endTime))(UnavailableTimeslotViolation)
    } yield copy(bookedTimeslots = validUnavailable :: bookedTimeslots)
  }

  private def isTimeslotBooked(timeslot: Timeslot): Boolean = {
    bookedTimeslots.exists(_.overlapsWith(timeslot))
  }

}
