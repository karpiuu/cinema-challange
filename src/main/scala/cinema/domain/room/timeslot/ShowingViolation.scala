package cinema.domain.room.timeslot

import cinema.domain.core.Violation

object ShowingStartTimeViolation extends Violation {
  override val reason = "Showing should start after 8am"
}

object ShowingEndTimeViolation extends Violation {
  override val reason = "Showing should end before 10pm"
}

object PremierShowingStartTimeViolation extends Violation {
  override val reason = "Premier movies should start after 5pm"
}

object PremierShowingEndTimeViolation extends Violation {
  override val reason = "Premier movies should end before 9pm"
}
