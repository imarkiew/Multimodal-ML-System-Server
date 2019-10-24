package models.dto

case class ExaminationView(id: Long, kind: String, title: Option[String], date: String, results: Map[String, String])

object ExaminationView {
  def apply(examination: Examination): ExaminationView = ExaminationView(examination.id, examination.kind, examination.title, examination.date, examination.results)
}
