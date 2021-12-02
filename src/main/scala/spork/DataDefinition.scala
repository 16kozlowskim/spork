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


sealed trait MapReduce[A] extends (() => A)

trait MapData[K1, V1] extends MapReduce[Map[K1, V1]] {


  def map[K2, V2: Monoid](f: (K1, V1) => Seq[(K2, V2)]): Seq[(K2, V2)]
}

