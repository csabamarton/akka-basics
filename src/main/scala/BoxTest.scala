import akka.actor.Actor.Receive
import akka.actor.{Actor, ActorRef, ActorSystem, Props}

case object StartRoundMessage

case object AttackMessage

case object DefenseMessage

case object FinishRoundMessage

class Attack(defense: ActorRef) extends Actor {
  var count = 0

  def incrementAndPrint() = {
    count += 1
    println("Attack")
  }

  def receive: Receive = {
    case StartRoundMessage =>
      incrementAndPrint()
      defense ! AttackMessage
    case DefenseMessage =>
      incrementAndPrint()

      if (count > 99) {
        sender ! FinishRoundMessage
        println("Attacking has been stopped")
        context.stop(self)
      } else {
        sender ! AttackMessage
      }
  }
}

class Defense extends Actor {
  def receive: Receive = {
    case AttackMessage =>
      println("   Defense")
      sender ! DefenseMessage
    case FinishRoundMessage =>
      println("Defense has been stopped")
      context.stop(self)
  }
}

object BoxTest extends App {
  val system = ActorSystem("BoxSystem")
  val defense = system.actorOf(Props[Defense], name = "DefenseActor")
  val attack = system.actorOf(Props(new Attack(defense)), name = "AttackActor")

  attack ! StartRoundMessage
}
