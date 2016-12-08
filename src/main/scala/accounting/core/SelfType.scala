package accounting.core

/**
  * Created by Igor Wolkov on 08.12.16.
  */
trait SelfType {
  type Self
  val self: Self
}
