package models.dto

case class Examination(id: Long, kind: String, title: Option[String], date: String, results: Map[String, Double], path: String, username: String)