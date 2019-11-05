package models.dto

import org.joda.time.format.DateTimeFormat
import org.joda.time.DateTime


/** Examination DTO with default sorting order by date
  *
  * @param id
  * @param kind
  * @param title
  * @param date
  * @param results
  * @param path
  * @param username
  */
case class Examination(id: Long,
                       kind: String,
                       title: Option[String],
                       date: String,
                       results: Map[String, Double],
                       path: String, username: String) extends Ordered[Examination] {


  /** Compare two examinations
    *
    * @param that
    * @return Int
    */
  def compare(that: Examination): Int = {
    stringToDateTime(that.date) compareTo stringToDateTime(this.date)
  }


  /** Convert date time in String format to DateTime format
    *
    * @param string
    * @param format
    * @return DateTime
    */
  private def stringToDateTime(string: String, format: String = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"): DateTime =
    DateTimeFormat.forPattern(format).parseDateTime(string)
}