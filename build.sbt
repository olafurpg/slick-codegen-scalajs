import sbt.Project.projectToRef
import slick.codegen.SourceCodeGenerator
import slick.{ model => m }


lazy val clients = Seq(client)
lazy val scalaV = "2.11.7"
lazy val Scala211 = scalaV
lazy val slickV = "3.1.0"

lazy val customScalacOptions = Seq(
//  "-Ymacro-debug-lite",
  "-deprecation", // Emit warning and location for usages of deprecated APIs.
  "-feature", // Emit warning and location for usages of features that should be imported explicitly.
  "-unchecked", // Enable additional warnings where generated code depends on assumptions.
  "-Xfatal-warnings", // Fail the compilation if there are any warnings.
  "-Xlint", // Enable recommended additional warnings.
  "-Ywarn-adapted-args", // Warn if an argument list is modified to match the receiver.
//  "-Ywarn-dead-code", // Warn when dead code is identified.
  "-Ywarn-inaccessible", // Warn about inaccessible types in method signatures.
  "-Ywarn-nullary-override", // Warn when non-nullary overrides nullary, e.g. def foo() over def foo.
  "-Ywarn-numeric-widen" // Warn when numerics are widened.
)

lazy val databaseUrl = sys.env.getOrElse("DB_DEFAULT_URL", ???)
lazy val databaseUser = sys.env.getOrElse("DB_DEFAULT_USER", ???)
lazy val databasePassword = sys.env.getOrElse("DB_DEFAULT_PASSWORD", ???)
lazy val updateDb = taskKey[Seq[File]]("Runs flyway and Slick code codegeneration.")
lazy val clearScreenTask = TaskKey[Unit]("clear", "Clears the screen.")

def commonSettings: Project => Project =
    _.settings(
      organization       := "is.launaskil",
      version            := "0.0.1-SNAPSHOT",
      homepage           := Some(url("https://launaskil.is")),
      scalaVersion       := Scala211,
      scalacOptions     ++= customScalacOptions,
      updateOptions      := updateOptions.value.withCachedResolution(true),
      testFrameworks += new TestFramework("utest.runner.Framework"),
      clearScreenTask    := { println("\033[2J\033[;H") })

def hasDatabaseCodegen: Project => Project =
  _.settings(slickCodegenSettings:_*)
   .settings(
    libraryDependencies ++= Seq(
      "org.postgresql" % "postgresql" % "9.4-1201-jdbc41"
      , "com.typesafe.slick" %% "slick" % slickV
    ),
    sourceGenerators in Compile <+= slickCodegen,
    slickCodegenDatabaseUrl := databaseUrl,
    slickCodegenDatabaseUser := databaseUser,
    slickCodegenDatabasePassword := databasePassword,
    slickCodegenExcludedTables in Compile := Seq("schema_version"),
    slickCodegenOutputPackage := "is.launaskil.models"
  )


def addCommandAliases(m: (String, String)*) = {
  val s = m.map(p => addCommandAlias(p._1, p._2)).reduce(_ ++ _)
  (_: Project).settings(s: _*)
}

lazy val root = project.in(file("."))
  .configure(commonSettings, addCommandAliases(
  "C"  -> "root/clean",
  "t"  -> ";clear;  test:compile ; server/test ; client/test",
  "tt" -> ";clear; +server:compile ;+server/test",
  "cg" -> ";clear; reload ; serverCodegen/slickCodegen ; sharedCodegen/slickCodegen",
  "T"  -> "; clean ;t",
  "TT" -> ";+clean ;tt"))

lazy val serverCodegen = (project in file("serverCodegen"))
  .configure(commonSettings, hasDatabaseCodegen)
  .settings(
    slickCodegenCodeGenerator := Codegen.serverCodegen,
    slickCodegenOutputDir := file("server/app")
  )

lazy val sharedCodegen = (project in file("sharedCodegen"))
  .configure(commonSettings)
  .configure(hasDatabaseCodegen)
  .settings(
    slickCodegenCodeGenerator := Codegen.sharedCodegen,
    slickCodegenOutputDir := file("shared/src/main/scala")
  )


lazy val flyway = (project in file("flyway"))
  .configure(commonSettings)
  .settings(flywaySettings:_*)
  .settings(
    flywayUrl := databaseUrl,
    flywayUser := databaseUser,
    flywayPassword := databasePassword,
    flywayLocations := Seq("filesystem:server/conf/db/migration/default"),
    flywaySchemas := Seq("public"),
    updateDb <<= (sourceManaged, dependencyClasspath in Compile, runner in Compile, streams) map { (dir, cp, r, s) =>
      println("UPDATEDB")
      toError(r.run("com.github.olafurpg.slick.Codegen", cp.files, Array[String](), s.log))
      Seq()
    }
  )

lazy val client = (project in file("client"))
  .configure(commonSettings)
  .settings(
  scalaVersion := scalaV,
  persistLauncher := true,
  persistLauncher in Test := false,
//  (jsEnv in Test) := new PhantomJSEnv,
  libraryDependencies ++= Seq(
    "org.scala-js" %%% "scalajs-dom" % "0.8.0"
    , "com.github.japgolly.scalajs-react" %%% "core" % "0.10.0"
    , "com.lihaoyi" %%% "autowire" % "0.2.5"
    , "com.lihaoyi" %%% "utest" % "0.3.1"
  ),
  jsDependencies ++= Seq(
      // React JS itself (Note the filenames, adjust as needed, eg. to remove addons.)
      "org.webjars.npm" % "react"     % "0.14.0" / "react-with-addons.js" commonJSName "React"    minified "react-with-addons.min.js"
    , "org.webjars.npm" % "react-dom" % "0.14.0" / "react-dom.js"         commonJSName "ReactDOM" minified "react-dom.min.js" dependsOn "react-with-addons.js"
  )
).enablePlugins(ScalaJSPlugin, ScalaJSPlay).
  dependsOn(sharedJs)

lazy val server = (project in file("server"))
  .configure(commonSettings)
  .settings(
    scalaJSProjects := clients,
    pipelineStages := Seq(scalaJSProd)
  )
  .settings(
    scalaVersion := scalaV,
    scalacOptions ++= customScalacOptions,
    libraryDependencies ++= Seq(
      jdbc
      , "com.github.olafurpg" %% "postgres-driver" % "0.1.2-SNAPSHOT"
      , "com.lihaoyi" %% "autowire" % "0.2.5"
      , "com.lihaoyi" %% "utest" % "0.3.1"
      , "com.mohiva" %% "play-silhouette" % "3.0.4"
      , "com.mohiva" %% "play-silhouette-testkit" % "3.0.4" % "test"
      , "com.typesafe.play" %% "play-slick" % "1.1.0"
      , "com.typesafe.slick" %% "slick" % slickV
      , "com.vmunier" %% "play-scalajs-scripts" % "0.3.0"
      , "org.postgresql" % "postgresql" % "9.4-1201-jdbc41"
    )
).enablePlugins(PlayScala)
  .aggregate(clients.map(projectToRef): _*)
  .dependsOn(sharedJvm)


lazy val shared = (crossProject.crossType(CrossType.Pure) in file("shared"))
  .settings(
    scalaVersion := scalaV,
    libraryDependencies ++= Seq(
      "com.lihaoyi" %%% "upickle" % "0.3.6"
    )
  )
  .jsConfigure(_ enablePlugins ScalaJSPlay)

lazy val sharedJvm = shared.jvm
lazy val sharedJs = shared.js

// loads the Play project at sbt startup
onLoad in Global := (Command.process("project server", _: State)) compose (onLoad in Global).value

ivyScala := ivyScala.value map { _.copy(overrideScalaVersion = true) }

fork in run := true
