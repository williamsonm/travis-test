package verizon.build

import sbt._, Keys._

object CrossLibraryPlugin extends AutoPlugin {

  object autoImport {
    val scalazVersion = settingKey[String]("scalaz version")
  }

  import autoImport._

  override def trigger = allRequirements

  override def requires = RigPlugin

  override lazy val projectSettings = Seq(
    // 7.1.11, 7.2.8
    scalazVersion := {
      sys.env.get("SCALAZ_VERSION").getOrElse("7.1.11")
    },
    version := {
      val suffix = if(scalazVersion.value.startsWith("7.1")) "" else "a"
      val versionValue = version.value
      if(versionValue.endsWith("-SNAPSHOT"))
        versionValue.replaceAll("-SNAPSHOT", s"$suffix-SNAPSHOT")
      else s"$versionValue$suffix"
    }
  )
}
