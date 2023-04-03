package cinema.domain.timeslot

import cinema.domain.movie.Movie
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers

import scala.concurrent.duration._
import cinema.domain._
import cinema.domain.timeslot.attribute.Require3DGlasses

class ShowingTests extends AnyFreeSpec with Matchers {

  "A Showing" - {
    "should correctly include 3D glasses to movies" in {
      val normalMovie = Movie(1, "Shrek", 2.hours)
      val movieIn3D   = Movie(2, "Avatar", 2.hours, is3D = true)

      val normalShowing     = Showing(12 :: 30, normalMovie)
      val showingFor3DMovie = Showing(12 :: 30, movieIn3D)

      normalShowing.isValid shouldBe true
      showingFor3DMovie.isValid shouldBe true

      normalShowing.map(_.attributes should not contain (Require3DGlasses))
      showingFor3DMovie.map(_.attributes should contain(Require3DGlasses))
    }

    "should correctly validate hours for premier movies" in {
      val premierMovie = Movie(2, "Shrek 9", 2.hours, isPremier = true)

      val earlyShowing   = Showing(16 :: 30, premierMovie)
      val lateShowing    = Showing(20 :: 30, premierMovie)
      val correctShowing = Showing(18 :: 30, premierMovie)

      earlyShowing.isInvalid shouldBe true
      lateShowing.isInvalid shouldBe true

      correctShowing.isValid shouldBe true
    }
  }

}