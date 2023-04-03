package cinema.domain.room

import cinema.domain.core.Violation

object ShowingTimeslotViolation extends Violation {
  override val reason: String = "Event overlaps with already booked timeslots"
}

object CleaningTimeslotViolation extends Violation {
  override val reason: String = "Cleaning overlaps with already booked timeslots"
}

object UnavailableTimeslotViolation extends Violation {
  override val reason: String = "Unavailable overlaps with already booked timeslots"
}
