package accounting.core

import accounting.core.event.Event

/**
  * Created by Igor Wolkov on 08.12.16.
  */
// TODO: Move to ??? model?
// TODO: Make it a Monad
// TODO: It's not a monad because a filter has nothing sense
// TODO: Change signature to Result[T, E <: Event[T], +U](event: E, state: U)
// TODO: Make it trait. Add bad result which will handle exception for particular event
object result {

  case class Result[+E <: Event[_], +U](event: E, state: U) {

    //    // TODO: prove that it's a valid map
    @inline final def map[S <: Event[_], V](f: (E, U) => (S, V)): Result[S, V] = f(event, state) match {
      case (e, s) => Result[S, V](e, s)
    }

    //    // TODO: prove that it's a valid flatMap
    @inline final def flatMap[S <: Event[_], V](f: (E, U) => Result[S, V]): Result[S, V] = f(event, state)

    //    @inline final def withFilter(p: (Event[T], U) => Boolean): WithFilter = new WithFilter(p)
    //
    //    @inline final def filter(p: (Event[T], U) => Boolean): Option[A] =
    //      if (isEmpty || p(this.get)) this else None
    //
    //    // TODO: Fix it
    //    class WithFilter(p: (Event[T], U) => Boolean) {
    //      def map[S, V](f: (Event[T], U) => (Event[S], V)): Result[S, V]  = self filter p map f
    //      def flatMap[B](f: A => Option[B]): Option[B] = self filter p flatMap f
    //      def foreach[U](f: A => U): Unit = self filter p foreach f
    //      def withFilter(q: A => Boolean): WithFilter = new WithFilter(x => p(x) && q(x))
    //    }
  }

}