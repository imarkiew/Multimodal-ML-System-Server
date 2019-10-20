package models.implicits

import com.typesafe.config.ConfigFactory
import models.dto.ExaminationClientInfo
import play.api.libs.json.{Reads, __}
import play.api.libs.functional.syntax._
import collection.JavaConverters._


object ExaminationsImplicits {

  implicit val examinations: Seq[String] = ConfigFactory.load.getStringList("examinations").asScala
  implicit val examinationsReader: Reads[ExaminationClientInfo] = (
    (__ \ "title").readNullable[String] and
      (__ \ "date").read[String] and
      (__ \ "fileName").read[String] and
      (__ \ "content").read[String]
    )(ExaminationClientInfo.apply _)
}
