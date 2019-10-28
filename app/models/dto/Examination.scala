package models.dto

import org.joda.time.format.DateTimeFormat
import org.joda.time.DateTime


case class Examination(id: Long,
                       kind: String,
                       title: Option[String],
                       date: String,
                       results: Map[String, Double],
                       path: String, username: String) extends Ordered[Examination] {

  def compare(that: Examination): Int = {
    stringToDateTime(that.date) compareTo stringToDateTime(this.date)
  }

  private def stringToDateTime(string: String, format: String = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"): DateTime =
    DateTimeFormat.forPattern(format).parseDateTime(string)
}