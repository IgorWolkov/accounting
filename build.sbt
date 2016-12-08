name := "accounting"

version := "1.0"

scalaVersion := "2.11.6"

// TODO clean-up unused dependencies
resolvers ++= Seq(
  "Sbt plugins"                   at "https://dl.bintray.com/sbt/sbt-plugin-releases",
  "Sonatype Releases"             at "http://oss.sonatype.org/content/repositories/releases",
  "Maven Central Server"          at "http://repo1.maven.org/maven2",
  "TypeSafe Repository Releases"  at "http://repo.typesafe.com/typesafe/releases/",
  "TypeSafe Repository Snapshots" at "http://repo.typesafe.com/typesafe/snapshots/",
  "Sonatype"                      at "https://oss.sonatype.org/content/groups/public",
  "Twitter"                       at "http://maven.twttr.com",
  "Edofic snapshots"              at "http://edofic.github.com/repository/snapshots",
  "Scalaz Bintray Repo"           at "http://dl.bintray.com/scalaz/releases",
  "Couchbase"                     at "http://files.couchbase.com/maven2/"
)

resolvers += Resolver.mavenLocal

libraryDependencies ++= Seq(
  "com.typesafe"                  %  "config"                         % "1.2.1",
  "joda-time"                     %  "joda-time"                      % "2.9.2",
  "com.chuusai"                   %% "shapeless"                      % "2.3.2",
  "org.scalatest"                 %% "scalatest"                      % "2.2.4"               % "test"
)

