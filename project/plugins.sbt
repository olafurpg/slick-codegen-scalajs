resolvers ++= Seq(
  "Typesafe repository" at "https://repo.typesafe.com/typesafe/releases/"
  , "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
  , "Flyway" at "http://flywaydb.org/repo"
  , "Atlassian Releases" at "https://maven.atlassian.com/public/"
)

addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.4.2")

addSbtPlugin("com.typesafe.sbt" % "sbt-scalariform" % "1.3.0")

addSbtPlugin("org.flywaydb" % "flyway-sbt" % "3.2.1")

addSbtPlugin("org.scala-js" % "sbt-scalajs" % "0.6.4")

addSbtPlugin("com.vmunier" % "sbt-play-scalajs" % "0.2.8")

addSbtPlugin("com.github.tototoshi" % "sbt-slick-codegen" % "1.2.0")

libraryDependencies += "org.postgresql" % "postgresql" % "9.4-1201-jdbc41"

