package scalaz
package effect

import Predef._
import prop.IsCovariant

class CovariantResolutionTest {
  implicitly[IsCovariant[IO[Int, ?]]]
}
