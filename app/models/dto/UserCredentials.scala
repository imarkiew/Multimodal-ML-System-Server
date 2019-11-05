package models.dto


/** User login credentials DTO
  *
  * @param username
  * @param password
  */
case class UserCredentials(username: Option[String], password: Option[String])
