package models.implicits

import models.utilities.CustomConfig.customConfig
import play.api.libs.json._


object ExaminationsImplicits {

  implicit val examinationsNames: Seq[String] = customConfig.examinations

  implicit def examinationResultsStringAsJsonToMap(jsonString: String): Map[String, Double] =
    Json.parse(jsonString).validate[Map[String, Double]].get

  implicit def examinationResultsMapToStringAsJson(results: Map[String, Double]): String = Json.toJson(results).toString

}
