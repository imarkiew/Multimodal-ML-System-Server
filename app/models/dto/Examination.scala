package models.dto

import java.text.SimpleDateFormat
import java.util.Date


case class Examination(id: Long,
                       kind: String,
                       title: Option[String],
                       date: String,
                       results: Map[String, Double],
                       path: String, username: String) extends Ordered[Examination] {

  def compare(that: Examination): Int = {
    stringToDateTime(that.date) compareTo stringToDateTime(this.date)
  }

  private def stringToDateTime(string: String, format: String = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"): Date =
    new SimpleDateFormat(format).parse(string)
}