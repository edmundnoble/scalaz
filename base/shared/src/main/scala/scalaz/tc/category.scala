package scalaz
package tc

trait CategoryClass[=>:[_, _]] extends ComposeClass[=>:] {
  def id[A]: A =>: A
}
