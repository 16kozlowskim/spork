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



sealed trait DataDefinition[K, V] extends (() => Future[Map[K, V]]) {
  def map[L, W: Monoid](f: (K, V) => Seq[(K2, V2)]): Seq[(K2, V2)]


}


abstract class MappedData[K, V] extends DataDefinition[A] {

}



sealed trait MapReduce[A] extends (() => A)

trait MapData[K1, V1] extends MapReduce[Map[K1, V1]] {


  def map[K2, V2: Monoid](f: (K1, V1) => Seq[(K2, V2)]): Seq[(K2, V2)]
}

