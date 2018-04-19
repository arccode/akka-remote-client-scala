package net.arccode

import akka.actor.{ActorSystem, Props}
import com.typesafe.config.ConfigFactory
import net.arccode.actor.ClientActor

/**
  * 系统启动入口
  *
  * @author http://arccode.net
  * @since 2018-04-11
  */
object Main extends App {

  implicit val system = ActorSystem("akka-remote-client-scala")

  system.actorOf(Props[ClientActor], "clientActor") ! "tell"

}
