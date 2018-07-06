package scalaz
package tc

import Predef._

import scala.List

trait MonoidClass[A] extends SemigroupClass[A] {
  def empty: A
}

object MonoidClass {
  implicit val stringMonoid: Monoid[String] = instanceOf(new MonoidClass[String] {
    def append(a1: String, a2: => String) = a1 + a2
    def empty                             = ""
  })
  implicit val unitMonoid: Monoid[Unit] = instanceOf(new MonoidClass[Unit] {
    def append(a1: Unit, a2: => Unit) = ()
    def empty                         = ()
  })
  implicit def listMonoid[A]: Monoid[List[A]] = instanceOf(new MonoidClass[List[A]] {
    def append(a1: List[A], a2: => List[A]): List[A] = a1 ++ a2
    def empty = List.empty
  })
}
