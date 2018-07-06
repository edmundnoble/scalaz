package scalaz
package effect

import scala.List

import Predef._

trait SafeApp {

  /**
   * The main function of the application, which will be passed the command-line
   * arguments to the program.
   */
  def run[E](args: List[String]): IO[E, Unit]
}
