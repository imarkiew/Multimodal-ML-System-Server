package models.dto

import org.joda.time.format.DateTimeFormat


/** Examination info DTO for application view with default date time trimming
  *
  * @param id
  * @param kind
  * @param title
  * @param date
  * @param results
  */
case class ExaminationView(id: Long, kind: String, title: Option[String], date: String, results: Map[String, Double])


/** Companion object of ExaminationView
  *
  */
object ExaminationView {


  /** Construct instance ExaminationView from Examination
    *
    * @param examination
    * @return ExaminationView
    */
  def apply(examination: Examination): ExaminationView = ExaminationView(examination.id, examination.kind, examination.title, trimDateTime(examination.date), examination.results)


  /** Trim datetime
    *
    * @param dateTime
    * @param format
    * @param newFormat
    * @return String
    */
  private def trimDateTime(dateTime: String, format: String = "yyyy-mm-dd'T'HH:mm:ss.SSS'Z'", newFormat: String = "yyyy-mm-dd HH:mm:ss"): String = {
    val simpleDateTime =  DateTimeFormat.forPattern(format).parseDateTime(dateTime)
    DateTimeFormat.forPattern(newFormat).print(simpleDateTime)
  }
}