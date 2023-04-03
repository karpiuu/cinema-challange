package cinema.domain.core

import io.jvm.uuid.UUID

/**
 * Implement repository template for aggregates in domain.
 * Final repository implementation should be located in infrastructure layer.
 */
trait Repository[T] {
  def get(id: UUID): T
  def save(t: T): T
}
