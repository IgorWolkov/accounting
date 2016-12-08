package accounting.core

import accounting.core.characteristics._
import accounting.core.event.Event
import accounting.core.group.GroupElement
import accounting.core.result.Result

import scala.language.implicitConversions

/**
  * Created by Igor Wolkov on 04.12.16.
  */
object flow extends App {

  trait Flow[T] {
    self: SelfType =>

    def flow: List[PartialFunction[Result[Event[T], Self], Result[Event[T], Self]]]
  }

  trait Tree[T] {
    self: SelfType =>

    def children: List[Self]
  }

  trait CharacterisedTree[T] extends Tree[T] with Characteristic[T] with SelfType {
    type Self <: CharacterisedTree[T]

    implicit val element: GroupElement[T]

    def characteristic: GroupElement[T] = `children's characteristics`

    protected def `children's characteristics`: GroupElement[T] =
      (children foldLeft GroupElement[T].neutral) {
        (element: GroupElement[T], characterised: Self) => element ⊙ characterised.characteristic
      }
  }

  trait EigenCharacterisedTree[T] extends CharacterisedTree[T] with EigenCharacteristic[T] {
    type Self <: EigenCharacterisedTree[T]

    override def characteristic: GroupElement[T] = `children's characteristics` ⊙ `eigen characteristic`
  }

  trait ProcessableCharacterisedTree[T] extends CharacterisedTree[T] with Flow[T] {

    type Self <: ProcessableCharacterisedTree[T]

    /*TODO: Add scala doc*/
    def `apply event`(event: Event[T]): Result[Event[T], Self] =
      flow find {
        _.isDefinedAt(Result(event, self))
      } map {
        _.apply(Result(event, self))
      } getOrElse(/*TODO: Use custom exception*/throw new RuntimeException)

    protected def `apply event to children`(cover: Event[T]): Result[Event[T], List[ProcessableCharacterisedTree[T]]] = {
      (children foldLeft Result[Event[T], List[ProcessableCharacterisedTree[T]]](cover, Nil)) {
        case (result, child) => (child `apply event` result.event) flatMap((e, s) => Result(e, s +: result.state))
      }
    }
  }

  trait EigenProcessableCharacterisedTree[T] extends ProcessableCharacterisedTree[T] with EigenCharacterisedTree[T] {
    override type Self <: EigenProcessableCharacterisedTree[T]
  }

}
