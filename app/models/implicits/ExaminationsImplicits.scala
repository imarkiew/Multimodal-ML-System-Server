package models.implicits

import com.typesafe.config.ConfigFactory
import collection.JavaConverters._


object ExaminationsImplicits {

  implicit val examinations: Seq[String] = ConfigFactory.load.getStringList("examinations").asScala

}
