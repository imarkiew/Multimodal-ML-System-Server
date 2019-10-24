package models.utilities

import models.dto.ExaminationView

object Tmp {

  val exams = Seq(
    ExaminationView(11, "skin-lesions", Some("Patient 1"), "2019-10-11 12:28:37", Map("akiec" -> "57", "ner" -> "20", "true" -> "23")),
    ExaminationView(5, "breast-cancer", None, "2019-10-11 12:30:37", Map("mal" -> "60", "bem" -> "40"))
  )

}
