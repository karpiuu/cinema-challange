package cinema.domain.core

import cats.Monoid
import cats.data.NonEmptyList

abstract class Violation {
  val reason: String
}

/**
 * Validator check if object passes specified tests.
 *
 * Implicit ValidatorDecorator helps defining validators.
 *
 * Example:
 *
 * val validator = Validator[Int]
 *  .validate(_ < 0)(mustBePositiveViolation)
 *  .validateIf(_ % 2 == 0)(_ > 100)(otherViolation)
 */
trait Validator[T] extends Monoid[Validator[T]] {
  def apply(t: T): Either[NonEmptyList[Violation], T]

  override def empty: Validator[T] = Validator.apply[T]

  override def combine(val1: Validator[T], val2: Validator[T]): Validator[T] = CombinedValidator(val1, val2)

  def ++(validator: Validator[T]): Validator[T] = combine(this, validator)
}

class EmptyValidator[T] extends Validator[T] {
  override def apply(t: T): Either[NonEmptyList[Violation], T] = Right(t)
}

class BaseValidator[T](test: T => Boolean, violation: Violation) extends Validator[T] {

  override def apply(t: T): Either[NonEmptyList[Violation], T] =
    if (test(t)) Left(NonEmptyList.of(violation)) else Right(t)

}

case class CombinedValidator[T](val1: Validator[T], val2: Validator[T]) extends Validator[T] {

  def apply(t: T): Either[NonEmptyList[Violation], T] = {
    (val1(t), val2(t)) match {
      case (Right(_), Right(_))    => Right(t)
      case (v @ Left(_), Right(_)) => v
      case (Right(_), v @ Left(_)) => v
      case (Left(v1), Left(v2))    => Left(v1 ++ v2.toList)
    }
  }

}

object Validator {
  type Violations = NonEmptyList[Violation]

  def apply[T]: Validator[T] = new EmptyValidator[T]

  implicit class ValidatorDecorator[T](val validator: Validator[T]) {

    def validate(test: T => Boolean)(violation: Violation): Validator[T] = {
      val obj = new BaseValidator[T](test, violation)
      Validator[T].combine(obj, validator)
    }

    def validateIf(condition: Boolean)(test: T => Boolean)(violation: Violation): Validator[T] = {
      if (condition) validate(test)(violation) else validator
    }

  }

}
