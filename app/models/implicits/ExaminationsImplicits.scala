package models.implicits

import models.utilities.CustomConfig.customConfig
import play.api.libs.json._
import scala.collection.immutable.ListMap


/** Object with implicits for Examinations
  *
  */
object ExaminationsImplicits {


  /** Names of examinations
    *
    */
  implicit val examinationNames: Seq[String] = customConfig.examinations


  /** Convert json string with results to Map
    *
    * @param jsonString
    * @return Map[String, Double]
    */
  implicit def examinationResultsStringAsJsonToMap(jsonString: String): Map[String, Double] =
    ListMap(Json
      .parse(jsonString)
      .validate[Map[String, Double]]
      .get
      .toSeq
      .sortBy(_._2)(Ordering[Double].reverse):_*)


  /** Convert Map with examinations results to json string
    *
    * @param results
    * @return String
    */
  implicit def examinationResultsMapToStringAsJson(results: Map[String, Double]): String = Json.toJson(results).toString

}
