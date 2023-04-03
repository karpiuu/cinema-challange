package cinema.domain.movie

import io.jvm.uuid.UUID

import scala.concurrent.duration.Duration

case class Movie(
  id: UUID,
  name: String,
  duration: Duration,
  is3D: Boolean = false,
  isPremier: Boolean = false
)
