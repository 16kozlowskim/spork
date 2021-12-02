package spork

import akka.actor.Actor
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, ActorSystem, Behavior}
import spork.Master.Result



object MapReducer {

  def apply[K, V](): Behavior[Master.Input[K, V]] =
    Behaviors.receive {
      case (_, message) =>
        message.respondTo ! Master.Result(message.k, message.v)
        Behaviors.same
    }
}

object Master {

  case class Result[K, V](k: K, v: V)
  case class Input[K, V, L, W](k: K, v: V, respondTo: ActorRef[Result[K, V]])

  def apply[K, V, L, W](data: Map[K, V], numMapReducers: Int, mapFunction: ((K, V)) => Seq[(L, W)]): Behavior[Result[K, V]] = {
    Behaviors.setup { context =>
      val mapReducers = (1 to numMapReducers) map { i =>
        context.spawn(MapReducer[K, V](), s"MapReducer-$i")
      }
      val roundRobinMapReducers = Iterator.continually(mapReducers).flatten

      data.zip(roundRobinMapReducers) foreach  {
        case ((k, v), mapReducer) => mapReducer ! Input(k, v, context.self)
      }

      Behaviors.receiveMessage { case Result(k, v) =>
        println(s"Result for key $k is $v")
        Behaviors.same
      }
    }
  }
}

object Actors extends App {

  val data = Map(
    (1, "hello my name is michal"),
    (2, "it was the best of times"),
    (3, "hello my name is michal"),
    (4, "it was the best of times"),
    (5, "hello my name is michal"),
    (6, "it was the best of times"),
  )

  val mapFunction: ((Int, String)) => Seq[(String, Int)] = {
    case (_, v) =>
      v.split(" ").map(word => (word, 1))
  }

  val numMapReducers = 4
  val master= ActorSystem(Master(data, numMapReducers, mapFunction), "AkkaQuickStart")

}