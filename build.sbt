name := """play-jpa"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.13.0"

libraryDependencies += guice
libraryDependencies += javaJpa
libraryDependencies += "org.postgresql" % "postgresql" % "42.2.12"
libraryDependencies += "org.hibernate" % "hibernate-core" % "5.4.14.Final"

libraryDependencies += javaWs % "test"

libraryDependencies += "org.awaitility" % "awaitility" % "3.1.5" % "test"
libraryDependencies += "org.assertj" % "assertj-core" % "3.11.1" % "test"
libraryDependencies += "org.mockito" % "mockito-core" % "2.23.4" % "test"

Test / testOptions += Tests.Argument(TestFrameworks.JUnit, "-a", "-v")

ThisBuild / scalacOptions ++= List("-encoding", "utf8", "-deprecation", "-feature", "-unchecked")
ThisBuild / javacOptions ++= List("-Xlint:unchecked", "-Xlint:deprecation", "-Werror")

PlayKeys.externalizeResourcesExcludes += baseDirectory.value / "conf" / "META-INF" / "persistence.xml"
