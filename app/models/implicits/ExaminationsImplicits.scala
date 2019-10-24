package models.implicits

import models.utilities.CustomConfig.customConfig
import play.api.libs.json._


object ExaminationsImplicits {

  implicit val examinationsNames: Seq[String] = customConfig.examinations
  implicit def examinationResultsStringAsJsonToMap(jsonString: String): Map[String, String] = {

    implicit def examinationReader: Reads[Map[String, String]] = __.read[Map[String, String]]

    Json.fromJson[Map[String, String]](Json.parse(jsonString)).get
  }

  implicit def examinationResultsMapToStringAsJson(results: Map[String, String]): String = Json.toJson(results).toString

}
