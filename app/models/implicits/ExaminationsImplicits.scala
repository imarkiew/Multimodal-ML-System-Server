package models.implicits

import models.utilities.CustomConfig.customConfig


object ExaminationsImplicits {

  implicit val examinations: Seq[String] = customConfig.examinations

}
