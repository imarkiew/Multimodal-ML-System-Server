package models.utilities

import net.ceedubs.ficus.Ficus._
import com.typesafe.config.ConfigFactory
import net.ceedubs.ficus.readers.ArbitraryTypeReader._


/** Case class with custom config
  *
  * @param examinations
  * @param skinLesionsUrl
  * @param breastCancerUrl
  */
case class CustomConfig(examinations: Seq[String], skinLesionsUrl: String, breastCancerUrl: String)


/** Companion object of custom config
  *
  */
object CustomConfig {


  /** Loaded custom config
    *
    */
  val customConfig = ConfigFactory
    .load
    .as[CustomConfig]("customConf")


  /** Chose appropriate external ML service url
    *
    * @param typeOfMLService
    * @return Option[String]
    */
  def chooseMLUrl(typeOfMLService: String): Option[String] =
    getCaseClassParams(customConfig)
      .get(typeOfMLService)
      .map(_.toString)


  /** Convert case class to Map
    *
    * @param caseClass
    * @return Map[String, Any]
    */
  private def getCaseClassParams(caseClass: Product): Map[String, Any] =
    caseClass
      .getClass
      .getDeclaredFields
      .map( _.getName)
      .zip(caseClass.productIterator.to)
      .toMap

}
