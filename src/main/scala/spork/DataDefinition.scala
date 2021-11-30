package spork

import cats.Monoid
import scala.concurrent.Future

object DataDefinition extends App {
  trait Gen[A] {
    def retList: List[A]
  }
  val a = new Gen[List[Int]] {
    override def retList: List[List[Int]] = ???
  }
}

sealed trait DataDefinition[K1, V1] extends (() => Future[Map[K1, V1]]) {

  def map[K2, V2: Monoid](f: (K1, V1) => Seq[(K2, V2)]): DataDefinition[K1, Seq[(K2, V2)]]
  def reduce[]
}
