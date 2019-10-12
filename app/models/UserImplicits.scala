package models

import models.dto.UserCredentials
import play.api.libs.json.{Reads, __}
import play.api.libs.functional.syntax._


object UserImplicits {

  implicit val userReads: Reads[UserCredentials] = (
    (__ \ "username").read[String] and
      (__ \ "password").read[String]
    )(UserCredentials.apply _)

}
