package controllers

import models.dao.UserDao
import javax.inject.{Inject, Singleton}
import models.dto.{User, UserCredentials}
import play.api.mvc.{AbstractController, ControllerComponents}
import play.api.libs.json._
import scala.concurrent.ExecutionContext
import org.mindrot.jbcrypt.BCrypt.checkpw


@Singleton
class LoginController @Inject()(userDao: UserDao, controllerComponents: ControllerComponents)(implicit executionContext: ExecutionContext) extends AbstractController(controllerComponents){

  import models.implicits.UserImplicits._

  def index = Action { implicit request =>
    Ok(views.html.hidden(routes.LoginController.login().toString))
  }

  def login = Action { implicit request =>
    Ok(views.html.login(None))
  }

  def validate = Action.async { implicit request =>

    val requestVals = Json.fromJson[UserCredentials](request.body.asJson.get).get
    userDao
      .getUser(requestVals.username)
      .map {
        case Some(user: User) if checkpw(requestVals.password, user.passwordHash) => Ok(views.html.cockpit())
        case _ => Ok(views.html.login(Some("Invalid username or password")))
      }
      .recover { case _ => Ok(views.html.exception("Internal exception")) }
  }
}
