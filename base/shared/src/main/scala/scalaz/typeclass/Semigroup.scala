package scalaz
package typeclass

trait Semigroup[A] extends Semigroup.Class[A]

object Semigroup {

  trait Class[A] {
    def append(a1: A, a2: => A): A
  }

  def apply[T](implicit T: Semigroup[T]): Semigroup[T] = T
}