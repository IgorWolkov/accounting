package accounting.core

import accounting.core.group.GroupElement

/**
  * Created by Igor Wolkov on 25.11.16.
  */
object event {

  trait Event[+T] {
    def uuid: String
    def parentUuid: Option[String]
  }

  object EmptyEvent extends Event[Nothing] {
    override def uuid: String = ???
    override def parentUuid: Option[String] = ???
  }

  case class Introduce[T](element: GroupElement[T]) extends Event[T] {
    override def uuid: String = ???
    override def parentUuid: Option[String] = ???
  }

  case class Cover[T](element: GroupElement[T]) extends Event[T] {
    override def uuid: String = ???
    override def parentUuid: Option[String] = ???
  }

  case class Reverse[T]() extends Event[T] {
    def `event uuids`: Seq[String] = ???
    override def uuid: String = ???
    override def parentUuid: Option[String] = ???
  }

}
