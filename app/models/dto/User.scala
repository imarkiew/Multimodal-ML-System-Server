package models.dto

case class User(name: String, surname: String, specialization: Option[String], email: Option[String], phoneNumber: Option[String],
                department: Option[String], username: String, passwordHash: String)