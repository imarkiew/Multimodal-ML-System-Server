package models.implicits

import models.utilities.CustomConfig.customConfig
import play.api.libs.json._
import scala.collection.immutable.ListMap


object ExaminationsImplicits {

  implicit val examinationNames: Seq[String] = customConfig.examinations

  implicit def examinationResultsStringAsJsonToMap(jsonString: String): Map[String, Double] =
    ListMap(Json
      .parse(jsonString)
      .validate[Map[String, Double]]
      .get
      .toSeq
      .sortBy(_._2)(Ordering[Double].reverse):_*)

  implicit def examinationResultsMapToStringAsJson(results: Map[String, Double]): String = Json.toJson(results).toString

}
