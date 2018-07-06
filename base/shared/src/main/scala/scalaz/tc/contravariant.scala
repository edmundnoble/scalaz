package scalaz
package tc

trait ContravariantClass[F[_]] {
  def contramap[A, B](r: F[A])(f: B => A): F[B]
}
