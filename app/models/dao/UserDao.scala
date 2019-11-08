package models.dao

import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import javax.inject.Inject
import models.dto.User
import scala.concurrent.{ExecutionContext, Future}


/** DAO for working with users
  *
  * @param dbConfigProvider
  * @param executionContext
  */
class UserDao @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit executionContext: ExecutionContext) extends HasDatabaseConfigProvider[JdbcProfile]{

  import profile.api._


  /** TableQuery which represents operations on database
    *
    */
  private val users = TableQuery[UsersTable]


  /** Get user by specific username
    *
    * @param username
    * @return Future[Option[User]]
    */
  def getUser(username: String): Future[Option[User]] = db.run(users.filter(_.username === username).result.headOption)


  /** Get all users
    *
    * @return Future[Seq[User]]
    */
  def getAll: Future[Seq[User]] = db.run(users.result)


  /** Class which represents entity of users in database
    *
    * @param tag - tag for table for internal usage
    */
  private class UsersTable(tag: Tag) extends Table[User](tag, "users"){
    def name = column[String]("name")
    def surname = column[String]("surname")
    def specialization = column[Option[String]]("specialization")
    def email = column[Option[String]]("email")
    def phoneNumber = column[Option[String]]("phoneNumber")
    def department = column[Option[String]]("department")
    def username = column[String]("username", O.PrimaryKey)
    def passwordHash = column[String]("passwordHash")
    def * = (name, surname, specialization, email, phoneNumber, department, username, passwordHash) <> (User.tupled, User.unapply)
  }

}
