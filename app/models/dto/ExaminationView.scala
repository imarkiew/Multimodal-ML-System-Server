package models.dto

import org.joda.time.format.DateTimeFormat


case class ExaminationView(id: Long, kind: String, title: Option[String], date: String, results: Map[String, Double])

object ExaminationView {

  def apply(examination: Examination): ExaminationView = ExaminationView(examination.id, examination.kind, examination.title, trimDateTime(examination.date), examination.results)

  private def trimDateTime(dateTime: String, format: String = "yyyy-mm-dd'T'HH:mm:ss.SSS'Z'", newFormat: String = "yyyy-mm-dd HH:mm:ss"): String = {
    val simpleDateTime =  DateTimeFormat.forPattern(format).parseDateTime(dateTime)
    DateTimeFormat.forPattern(newFormat).print(simpleDateTime)
  }
}