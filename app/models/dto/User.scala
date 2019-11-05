package models.dto


/** User info DTO
  *
  * @param name
  * @param surname
  * @param specialization
  * @param email
  * @param phoneNumber
  * @param department
  * @param username
  * @param passwordHash
  */
case class User(name: String, surname: String, specialization: Option[String], email: Option[String], phoneNumber: Option[String],
                department: Option[String], username: String, passwordHash: String)