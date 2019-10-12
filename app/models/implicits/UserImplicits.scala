package models.implicits

import models.dto.UserCredentials
import play.api.libs.functional.syntax._
import play.api.libs.json.{Reads, __}


object UserImplicits {

  implicit val userReads: Reads[UserCredentials] = (
    (__ \ "username").read[String] and
      (__ \ "password").read[String]
    )(UserCredentials.apply _)

}
