// --------------------------------------
// -------- 通用配置 ---------------------
// --------------------------------------
lazy val commonSettings = Seq(
  version := "0.0.2",
  scalaVersion := "2.12.2",
  javaOptions in Universal := Seq(
    "-Dconfig.file=conf/application.conf"
  ),
  javacOptions ++= Seq("-encoding", "UTF-8")
)

lazy val akkaRemoteClientScala =
  project
    .in(file("."))
    .settings(
      commonSettings,
      name := "akka-remote-client-scala",
      libraryDependencies ++= {
        sys.props += "package.type" → "jar"
        Seq(
          // akka生态相关
          "com.typesafe.akka" %% "akka-actor" % "2.5.3",
          "com.typesafe.akka" %% "akka-stream" % "2.5.3",

          // akka 远程调用
          "com.typesafe.akka" %% "akka-remote" % "2.5.3",

          // 日志相关
          "com.typesafe.akka" %% "akka-slf4j" % "2.5.3",
          "ch.qos.logback" % "logback-classic" % "1.2.3",

          // 测试相关
          "org.scalatest" %% "scalatest" % "3.0.3" % "test",
          "com.typesafe.akka" %% "akka-testkit" % "2.5.3" % "test",
          "com.typesafe.akka" %% "akka-http-testkit" % "10.0.11" % "test",

          // 远程调用序列化和反序列化
          "com.twitter" %% "chill-akka" % "0.9.2",

          // 内部包
          "net.arccode" % "rest-api-protocol" % "0.0.1"
        )
      }
    )