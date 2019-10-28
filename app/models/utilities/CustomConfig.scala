package models.utilities

import net.ceedubs.ficus.Ficus._
import com.typesafe.config.ConfigFactory
import net.ceedubs.ficus.readers.ArbitraryTypeReader._


case class CustomConfig(examinations: Seq[String], skinLesionsUrl: String, breastCancerUrl: String)

object CustomConfig {

  val customConfig = ConfigFactory
    .load
    .as[CustomConfig]("customConf")

}
