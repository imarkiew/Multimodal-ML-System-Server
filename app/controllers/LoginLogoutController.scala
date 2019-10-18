package controllers

import models.dao.UserDao
import javax.inject.{Inject, Singleton}
import models.dto.User
import models.utilities.UserCredentialsUtilities.{getUserCredentialsFromRequestBody, performAction}
import play.api.mvc.{AbstractController, ControllerComponents}
import scala.concurrent.{ExecutionContext, Future}
import org.mindrot.jbcrypt.BCrypt.checkpw


@Singleton
class LoginLogoutController @Inject()(userDao: UserDao, controllerComponents: ControllerComponents)(implicit executionContext: ExecutionContext)
  extends AbstractController(controllerComponents) {

  import models.implicits.UserImplicits._
  import models.implicits.ExaminationsImplicits._

  def index = Action.async { implicit request =>

    performAction { _ =>
      Future(Ok(views.html.hidden(routes.LoginLogoutController.cockpit().toString)))
    }(Future(Ok(views.html.hidden(routes.LoginLogoutController.login().toString))))
  }

  def login = Action.async { implicit request =>
    Future(Ok(views.html.login(None)))
  }

  def validate = Action.async { implicit request =>

    performAction { _ =>
      Future(Ok(views.html.cockpit(request.session.get("username").get)))
    }({
      val requestVals = getUserCredentialsFromRequestBody(request)
      userDao
        .getUser(requestVals.username.get)
        .map {
          case Some(user: User) if checkpw(requestVals.password.get, user.passwordHash) => Ok(views.html.cockpit(user.username))
            .withSession("username" -> user.username, "Csrf-Token" -> play.filters.csrf.CSRF.getToken.get.value)
          case _ => Ok(views.html.login(Some("Invalid username or password")))
        }
        .recover { case _ => Ok(views.html.exception("Internal exception"))}
    })
  }

  def cockpit = Action.async { implicit request =>
    performAction { _ =>
      Future(Ok(views.html.cockpit(request.session.get("username").get)))
    }(Future(Ok(views.html.login(None))))
  }

  def logout = Action.async { implicit request =>
    Future(Redirect(routes.LoginLogoutController.index()).withNewSession)
  }
}
