package cinema.domain.room

import cinema.domain.core.Repository
import io.jvm.uuid.UUID

trait RoomRepository extends Repository[Room] {
  override def get(id: UUID): Room
  override def save(room: Room): Room
}
