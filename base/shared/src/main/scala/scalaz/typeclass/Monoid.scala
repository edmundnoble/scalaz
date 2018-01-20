package scalaz
package typeclass

sealed trait Monoid[A] extends Monoid.Class[A]

object Monoid {

  trait Class[A] extends Semigroup.Class[A] {
    def semigroup: Semigroup[A]
    def empty: A
  }

  trait Template[A] extends Monoid[A] with Semigroup[A] {
    final override def semigroup = this
  }

  def apply[T](implicit T: Monoid[T]): Monoid[T] = T

}