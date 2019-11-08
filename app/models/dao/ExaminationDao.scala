package models.dao

import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import javax.inject.Inject
import models.dto.Examination
import scala.concurrent.{ExecutionContext, Future}


/** DAO for working with examinations
  *
  * @param dbConfigProvider
  * @param executionContext
  */
class ExaminationDao @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit executionContext: ExecutionContext) extends HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._
  import models.implicits.ExaminationsImplicits.{examinationResultsStringAsJsonToMap, examinationResultsMapToStringAsJson}


  /** TableQuery which represents operations on database
    *
    */
  private val examinations = TableQuery[ExaminationsTable]


  /** Get specific examination by id
    *
    * @param id
    * @return Future[Option[Examination]]
    */
  def getExamination(id: Long): Future[Option[Examination]] = db.run(examinations.filter(_.id === id).result.headOption)


  /** Get all examinations for specific user
    *
    * @param username
    * @return Future[Seq[Examination]]
    */
  def getAllExaminationsFromUser(username: String): Future[Seq[Examination]] = db.run(examinations.filter(_.username === username).result)


  /** Get all examinations
    *
    * @return Future[Seq[Examination]]
    */
  def getAll: Future[Seq[Examination]] = db.run(examinations.result)


  /** Class which represents entity of examinations in database
    *
    * @param tag - tag for table for internal usage
    */
  private class ExaminationsTable(tag: Tag) extends Table[Examination](tag, "examinations") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def kind = column[String]("kind")
    def title = column[Option[String]]("title")
    def date = column[String]("date")
    def results = column[String]("results")
    def path = column[String]("path")
    def username = column[String]("username")
    def * = (id, kind, title, date, results, path, username) <> (
      { case (id: Long, kind: String, title: Option[String], date: String, results: String, path: String, username: String) => Examination(id, kind, title, date, results, path, username)},
      { examination: Examination =>
        Some((examination.id, examination.kind, examination.title, examination.date, examination.results, examination.path, examination.username))}
    )
  }
}
