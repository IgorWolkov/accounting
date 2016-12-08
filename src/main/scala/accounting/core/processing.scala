package accounting.core

import accounting.core.event.{Reverse, Cover, Event}
import accounting.core.flow.{EigenProcessableCharacterisedTree, ProcessableCharacterisedTree}
import accounting.core.group.GroupElement
import accounting.core.result.Result
import shapeless._
import shapeless.ops.hlist.ToTraversable

import scala.language.implicitConversions

/**
  * Created by Igor Wolkov on 08.12.16.
  */
object processing extends App {

  trait Rule[T, +E <: Event[T], Flow <: ProcessableCharacterisedTree[T]] {
    def apply[U >: E <: Event[T]](): PartialFunction[Result[U, Flow], Result[Event[T], Flow]]
  }

  object Covering extends Rule[Int, Cover[Int], ProcessableCharacterisedTree[Int]] {
    type Covering = Covering.type

    def apply[U >: Cover[Int] <: Event[Int]](): PartialFunction[Result[U, ProcessableCharacterisedTree[Int]], Result[Event[Int], ProcessableCharacterisedTree[Int]]] = {
      case r: Result[Cover[Int], ProcessableCharacterisedTree[Int]] =>
        Result[Event[Int], ProcessableCharacterisedTree[Int]](
          event = new Event[Int] {
            override def uuid: String = ???

            override def parentUuid: Option[String] = ???

            override def toString: String = "Covering"
          },
          state = null)
    }
  }

  object Reversing extends Rule[Int, Reverse[Int], ProcessableCharacterisedTree[Int]] {
    type Reversing = Reversing.type

    def apply[U >: Reverse[Int] <: Event[Int]](): PartialFunction[Result[U, ProcessableCharacterisedTree[Int]], Result[Event[Int], ProcessableCharacterisedTree[Int]]] = {
      case r: Result[Cover[Int], ProcessableCharacterisedTree[Int]] =>
        Result[Event[Int], ProcessableCharacterisedTree[Int]](
          event = new Event[Int] {
            override def uuid: String = ???

            override def parentUuid: Option[String] = ???

            override def toString: String = "Reversing"
          },
          state = null)
    }
  }

  import Covering._
  import Reversing._


  val hlist: Covering :: Reversing :: HNil = Covering :: Reversing :: HNil

  println("List: " + hlist.toList)


  class Record[T, H <: HList](rules: H)(implicit
                                        val toTraversableAux1: ToTraversable.Aux[H, List, Rule[T, Event[T], ProcessableCharacterisedTree[T]]])
    extends ProcessableCharacterisedTree[T] {

    override type Self = ProcessableCharacterisedTree[T]
    override val self: ProcessableCharacterisedTree[T] = this


    override implicit val element: GroupElement[T] = ???

    override val flow: List[PartialFunction[Result[Event[T], ProcessableCharacterisedTree[T]], Result[Event[T], ProcessableCharacterisedTree[T]]]] =
      rules.toList map { _.apply() }

    override def children: List[ProcessableCharacterisedTree[T]] = ???
  }

  class Principle(rules: Covering :: Reversing :: HNil) extends Record[Int, Covering :: Reversing :: HNil](rules)

  val c1 = new Record[Int, Covering :: Covering :: HNil](Covering :: Covering :: HNil)

  val c2 = new Record[Int, Covering :: Reversing :: HNil](Covering :: Reversing :: HNil)

  val c3 = new Record[Int, Reversing :: Reversing :: HNil](Reversing :: Reversing :: HNil)

  val principle = new Principle(Covering :: Reversing :: HNil)

  val res1 =
    c1.`apply event`(
      new Event[Int] {
        override def uuid: String = "uuid"

        override def parentUuid: Option[String] = Option("parent uuid")
      }
    )

  val res2 =
    c2.`apply event`(
      new Event[Int] {
        override def uuid: String = "uuid"

        override def parentUuid: Option[String] = Option("parent uuid")
      }
    )

  val res3 =
    c3.`apply event`(
      new Event[Int] {
        override def uuid: String = "uuid"

        override def parentUuid: Option[String] = Option("parent uuid")
      }
    )

  val res4 =
    principle.`apply event`(
      new Event[Int] {
        override def uuid: String = "uuid"

        override def parentUuid: Option[String] = Option("parent uuid")
      }
    )

  println(res1)
  println(res2)
  println(res3)
  println(res4)




}
