package net.arccode.actor

import akka.actor.{Actor, ActorLogging}
import com.typesafe.config.ConfigFactory
import net.arccode.protocol.remote.LocationProtocol.{ExecuteSelectLocationsByParentCode, SelectLocationsByParentCodeMsg}
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

import scala.util.{Failure, Success}


/**
  * 查询区域信息
  *
  * @author http://arccode.net
  * @since 2018-04-11
  */
class ClientActor extends Actor with ActorLogging {

  implicit val timeout = Timeout(2 seconds)

  override def receive = {

    // ask 模式
    case cmd: String if cmd.equals("ask") ⇒
      log.info("接收到[{}]指令: {}, 发送方 - {}, 接收方 - {}",
               "根据区域code查询其下一级别的区域",
               cmd,
               sender().path,
               self)

      val path = ConfigFactory
        .defaultApplication()
        .getString("remote.actor.selectLocationsByParentCode")

      log.info("查询区域码服务path: {}", path)

      val remoteActor = context.actorSelection(path)

      // 查询所有省, 直辖市, 特区
      (remoteActor ? ExecuteSelectLocationsByParentCode("0")).onComplete { p ⇒
        p match {
          case Success(result) ⇒
            log.info("{}", result)
          case Failure(t) ⇒
            log.info("{}", t)
        }
      }

    // tell 模式
    case cmd: String if cmd.equals("tell") ⇒
      log.info("接收到[{}]指令: {}, 发送方 - {}, 接收方 - {}",
               "根据区域code查询其下一级别的区域",
               cmd,
               sender().path,
               self)

      val path = ConfigFactory
        .defaultApplication()
        .getString("remote.actor.selectLocationsByParentCode")

      log.info("查询区域码服务path: {}", path)

      val remoteActor = context.actorSelection(path)

      // 查询所有省, 直辖市, 特区
      remoteActor ! ExecuteSelectLocationsByParentCode("0")

    case msg @ SelectLocationsByParentCodeMsg(values) ⇒
      log.info("接收到[{}]指令: {}, 发送方 - {}, 接收方 - {}",
               "根据区域code查询其下一级别的区域(响应)",
               msg,
               sender().path,
               self)

      log.info("结果: {}", values)

    case _ ⇒
      log.error("{}", "无法识别的指令.")
  }
}
