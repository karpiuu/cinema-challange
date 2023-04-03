package cinema.domain.room.timeslot

import cats.data.Validated
import cinema.domain.core.Validator
import cinema.domain.core.Violation
import cinema.domain.movie.Movie
import cinema.domain.room.timeslot.attribute.Require3DGlasses
import cinema.domain.room.timeslot.attribute.ShowingAttribute
import cinema.domain.core.Validator.Violations

import java.time.OffsetDateTime
import scala.concurrent.duration.Duration

case class Showing(
  id: Int,
  movieId: Int,
  startTime: OffsetDateTime,
  duration: Duration,
  attributes: List[ShowingAttribute] = Nil
) extends Timeslot {

  private def withAttribute(attribute: Option[ShowingAttribute]): Showing =
    copy(attributes = attributes ++ attribute)

}

object Showing {

  private def validate(movie: Movie) = Validator[Showing]
    .validate(_.startTime.getHour < 8)(ShowingStartTimeViolation)
    .validate(_.endTime.getHour > 22)(ShowingEndTimeViolation)
    .validateIf(movie.isPremier)(_.startTime.getHour < 17)(PremierShowingStartTimeViolation)
    .validateIf(movie.isPremier)(_.endTime.getHour > 21)(PremierShowingEndTimeViolation)

  private def create(startTime: OffsetDateTime, movie: Movie): Showing = {
    Showing(0, movie.id, startTime, movie.duration)
      .withAttribute(Option.when(movie.is3D)(Require3DGlasses))
  }

  def apply(startTime: OffsetDateTime, movie: Movie): Either[Violations, Showing] = {
    validate(movie)(create(startTime, movie))
  }

}
