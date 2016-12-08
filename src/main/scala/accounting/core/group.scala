package accounting.core

/**
  * Created by Igor Wolkov on 25.11.16.
  */
object group {
  // TODO: Add map, flatMap and withFilter
  trait GroupElement[T] {
    require((this ⊙ neutral) == this, "Rule 'a ⊙ e = a' has been violated")
    require((this ⊙ this.inverse) == neutral, "Rule 'a ⊙ a⁻¹ = e' has been violated")

    def element: T
    def ⊙(that: GroupElement[T]): GroupElement[T]
    def neutral: GroupElement[T]

    def `is neutral` = this == neutral

    protected def inverse: GroupElement[T]
  }

  object GroupElement {
    def apply[T](implicit element: GroupElement[T]) = element
  }

}
