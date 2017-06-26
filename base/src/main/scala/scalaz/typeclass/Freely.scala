package scalaz
package typeclass

trait Freely[A, Repr, TCFrom[_], TCTo[_]] {
  implicit def freelyGenerated(implicit ev: TCFrom[A]): TCTo[Repr]

  def lift(a: A)(implicit ev: TCFrom[A]): Repr

  def foldMap[B: TCTo](repr: Repr)(trans: A => B): B

  def retract(repr: Repr)(implicit to: TCTo[A]): A = foldMap(repr)(identity)
}

trait FreelyH[F[_], Repr[_], TCFrom[_[_]], TCTo[_[_]]] {
  implicit def freelyGenerated(implicit ev: TCFrom[F]): TCTo[Repr]

  def lift[A]()(implicit ev: TCFrom[]): Repr

  def foldMap[G[_]: TCTo, A](repr: Repr[A])(trans: F ~> G): G[A]

  def retract[A](repr: Repr[A])(implicit to: TCTo[F]): F[A] = foldMap(repr)(~>.identity)
}