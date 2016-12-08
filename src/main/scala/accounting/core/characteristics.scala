package accounting.core

import accounting.core.group.GroupElement

/**
  * Created by Igor Wolkov on 25.11.16.
  */
object characteristics {

  trait Characteristic[T] {
    def characteristic: GroupElement[T]
  }

  trait EigenCharacteristic[T] {
    def `eigen characteristic`: GroupElement[T]
  }

}
