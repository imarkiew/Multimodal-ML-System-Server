package models.implicits

import models.dto.UserCredentials
import play.api.libs.functional.syntax._
import play.api.libs.json.{Reads, __}


/** Object with implicits for users
  *
  */
object UserImplicits {


  /** Implicit conversion of user credentials from json to case class
    *
    */
  implicit val userCredentialsReader: Reads[UserCredentials] = (
    (__ \ "username").readNullable[String] and
      (__ \ "password").readNullable[String]
    )(UserCredentials.apply _)

}
