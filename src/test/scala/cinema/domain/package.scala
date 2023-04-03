package cinema

import java.time.OffsetDateTime

package object domain {

  implicit class Minute(minute: Int) {

    def ::(hour: Hour): OffsetDateTime = {
      OffsetDateTime
        .parse("2023-04-01T00:00:00Z")
        .withHour(hour.value)
        .withMinute(minute)
    }

  }

  implicit class Hour(hour: Int) {
    val value: Int = hour
  }

}
