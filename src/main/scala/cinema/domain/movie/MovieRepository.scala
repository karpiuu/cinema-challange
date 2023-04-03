package cinema.domain.movie

import cinema.domain.core.Repository
import io.jvm.uuid.UUID

trait MovieRepository extends Repository[Movie] {
  override def get(id: UUID): Movie
  override def save(movie: Movie): Movie
}
