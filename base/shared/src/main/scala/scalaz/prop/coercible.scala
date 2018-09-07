package scalaz
package prop

import scala.{ Any, Predef }
import Predef.=:=

import Predef._
import data.∀

/**
 * The data type `Coercible[A, B]` witnesses that the types `A` and `B` have the same
 * runtime representation; a cast from `A` to `B` will not fail at runtime.
 * Note that this doesn't mean in all circumstances that `A` can be substituted with `B`;
 * see the docs for why this isn't the same as `A === B`.
 */
sealed trait Coercible[A, B] { ab =>
  import Coercible._

  /**
   * To create an instance of `Coercible[A, B]` you must show that you can convert
   * `A`'s to `B`'s, and that at runtime the operation is equivalent to a cast.
   */
  def apply(a: A): B

  def coerce(a: A): B =
    apply(a)

  /**
   * Coercibility is a transitive relation and its witnesses can be composed
   * in a chain much like functions.
   *
   * @see [[compose]]
   */
  final def andThen[C](bc: B Coercible C): A Coercible C = {
    val _ = bc
    unsafeForce[A, C]
  }

  /**
   * Coercibility is a transitive relation and its witnesses can be composed
   * in a chain much like functions.
   *
   * @see [[andThen]]
   */
  final def compose[Z](za: Z Coercible A): Z Coercible B = {
    val _ = za
    unsafeForce[Z, B]
  }

  /**
   * Equality is symmetric relation and therefore can be flipped around.
   * Flipping is its own inverse, so `x.flip.flip == x`.
   */
  final def flip: B Coercible A =
    unsafeForce[B, A]

  /**
   * Given `A Coercible B` we can convert `(X => A)` into `(X => B)`.
   */
  def onCvF[X]: Coercible[X => A, X => B] =
    unsafeForce[X => A, X => B]

  /**
   * Given `A Coercible B` we can convert `(B => X)` into `(A => X)`.
   */
  def onCtF[X]: Coercible[A => X, B => X] =
    unsafeForce[A => X, B => X]

  /**
   * Given `A Coercible B`, provide `A =:= B`.
   */
  def toPredef: A =:= B =
    implicitly[A =:= A].asInstanceOf[A =:= B]

  /**
   * Given `A Coercible B`, prove `A <~< B`.
   */
  def toAs: A <~< B = {
    As.refl[A].asInstanceOf[A <~< B]
  }
}

object Coercible {

  def apply[A, B](implicit ev: A Coercible B): A Coercible B = ev

  private[this] final case class Refl[A]() extends Coercible[A, A] {
    def apply(a: A): A = a
  }

  private val refl_ = ∀.of[λ[α => α Coercible α]].from(new Refl)

  /**
   * Equality is reflexive relation.
   */
  implicit def refl[A]: A Coercible A = refl_[A]

  implicit def cv[F[+_], A, B](implicit rec: A Coercible B): F[A] Coercible F[B] = {
    val _ = rec
    unsafeForce[F[A], F[B]]
  }

  implicit def ct[F[-_], A, B](implicit rec: A Coercible B): F[A] Coercible F[B] = {
    val _ = rec
    unsafeForce[F[A], F[B]]
  }

  /**
   * It can be convenient to convert a [[=:=]] value into a `Coercible` value.
   * This is not strictly valid as while it is almost certainly true that
   * `A =:= B` implies `A Coercible B` it is not the case that you can create
   * evidence of `A Coercible B` except via a coercion. Use responsibly.
   */
  def fromPredef[A, B](ev: A =:= B): A Coercible B = {
    val _ = ev
    unsafeForce[A, B]
  }

  def fromIs[A, B](ev: A Is B): A Coercible B = {
    val _ = ev
    unsafeForce[A, B]
  }

  def fromIsCovariant[F[_], A, B](isCv: IsCovariant[F])(rec: A Coercible B): F[A] Coercible F[B] = {
    val _ = (isCv, rec)
    unsafeForce[F[A], F[B]]
  }

  def fromIsContravariant[F[_], A, B](isCt: IsContravariant[F])(rec: A Coercible B): F[A] Coercible F[B] = {
    val _ = (isCt, rec)
    unsafeForce[F[A], F[B]]
  }

  /**
   * Unsafe coercion between types. `unsafeForce` abuses `asInstanceOf` to
   * explicitly coerce types. It is unsafe.
   */
  def unsafeForce[A, B]: A Coercible B =
    refl[Any].asInstanceOf[A Coercible B]
}
