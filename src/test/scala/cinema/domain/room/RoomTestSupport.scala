package cinema.domain.room

import cinema.domain._
import cinema.domain.movie.Movie
import cinema.domain.room.timeslot.Cleaning
import cinema.domain.room.timeslot.Showing
import cinema.domain.room.timeslot.Timeslot
import cinema.domain.room.timeslot.Unavailable
import io.jvm.uuid.UUID
import org.scalatest.EitherValues
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.concurrent.duration.DurationInt
import scala.concurrent.duration.FiniteDuration

trait RoomTestSupport extends AnyWordSpec with Matchers with EitherValues {
  val cleaningDuration: FiniteDuration = 30.minutes

  val anyMovieId: UUID = UUID.random
  val anyRoom: Room    = Room(UUID.random, cleaningDuration, List.empty)
  val anyMovie: Movie  = Movie(anyMovieId, "Shrek", 2.hour)

  val preBookedSlots: List[Timeslot] =
    Showing(UUID.random, UUID.random, 12 :: 30, 1.hours) ::
      Cleaning(UUID.random, 13 :: 30, cleaningDuration) ::
      Showing(UUID.random, UUID.random, 16 :: 40, 1.hours) ::
      Cleaning(UUID.random, 17 :: 40, cleaningDuration) :: Nil

}

object RoomTestSupportHelper {

  sealed trait TimeslotWithoutId {
    val timeslot: Timeslot

    def withoutId: TimeslotWithoutId
  }

  implicit class CleaningWithoutId(cleaning: Cleaning) extends TimeslotWithoutId {

    override def equals(that: Any): Boolean =
      that match {
        case obj: CleaningWithoutId =>
          cleaning.startTime == obj.timeslot.startTime &&
          cleaning.duration  == obj.timeslot.duration
        case _ => false
      }

    override val timeslot: Cleaning = cleaning

    override implicit def withoutId: CleaningWithoutId = this
  }

  implicit class ShowingWithoutId(showing: Showing) extends TimeslotWithoutId {

    override def equals(that: Any): Boolean =
      that match {
        case obj: ShowingWithoutId =>
          showing.startTime == obj.timeslot.startTime &&
          showing.duration  == obj.timeslot.duration &&
          showing.movieId   == obj.timeslot.movieId
        case _ => false
      }

    override val timeslot: Showing = showing

    override implicit def withoutId: ShowingWithoutId = this
  }

  implicit class UnavailableWithoutId(unavailable: Unavailable) extends TimeslotWithoutId {

    override def equals(that: Any): Boolean =
      that match {
        case obj: UnavailableWithoutId =>
          unavailable.startTime == obj.timeslot.startTime &&
          unavailable.duration  == obj.timeslot.duration
        case _ => false
      }

    override val timeslot: Unavailable = unavailable

    override implicit def withoutId: UnavailableWithoutId = this
  }

  implicit class TimeslotWrapper(list: List[Timeslot]) {

    def withoutId: List[TimeslotWithoutId] = {
      list.map {
        case showing: Showing         => ShowingWithoutId(showing)
        case unavailable: Unavailable => UnavailableWithoutId(unavailable)
        case cleaning: Cleaning       => CleaningWithoutId(cleaning)
      }
    }

  }

}
