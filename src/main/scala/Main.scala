import akka.actor.{Actor, ActorSystem, Props}

class HelloActor(myName: String) extends Actor {
  def receive: Receive = {
    case "hello" => println(s"Hello from $myName")
    case _ => print(s"Huh?, said $myName")
  }
}

object Main extends App {
  val system = ActorSystem("HelloSystem")
  //val helloActor = system.actorOf(Props[HelloActor], name = "HelloActor")
  val helloActor = system.actorOf(Props(new HelloActor("Csaba")), name = "HelloActor")
  helloActor ! "hello"
  helloActor ! "Bom dia!"

}
