package controllers

import models.dao.UserDao
import javax.inject.{Inject, Singleton}
import models.dto.User
import models.utilities.UserCredentialsUtilities.{getUserCredentialsFromRequestBody, performAction}
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import scala.concurrent.{ExecutionContext, Future}
import org.mindrot.jbcrypt.BCrypt.checkpw


/** Controller class for login management
  *
  * @param userDao
  * @param controllerComponents
  * @param executionContext
  */
@Singleton
class LoginLogoutController @Inject()(userDao: UserDao, controllerComponents: ControllerComponents)(implicit executionContext: ExecutionContext)
  extends AbstractController(controllerComponents) {


  /** Importing implicit for getting implicit conversion of user credentials from json to case class
    *
    */
  import models.implicits.UserImplicits.userCredentialsReader


  /** Show user login form if they are not logged in or redirect them to cockpit page otherwise
    *
    * @return Action[AnyContent]
    */
  def index: Action[AnyContent] = Action.async { implicit request =>
    performAction { _ =>
      Future(Ok(views.html.hidden(routes.CockpitController.cockpit().toString)))
    }(Future(Ok(views.html.hidden(routes.LoginLogoutController.login().toString))))
  }


  /** Show not logged user login page or forbid access if user is logged in
    *
    * @return Action[AnyContent]
    */
  def login: Action[AnyContent] = Action.async { implicit request =>
    performAction { _ =>
      Future(Ok(views.html.forbidden()))
    }(Future(Ok(views.html.login(None))))
  }


  /** Validate credentials for not logged user or forbid access if user is logged in
    * After validation user is moved to cockpit page or receives information of invalid authentication or gets
    * information of internal problem
    *
    * @return Action[AnyContent]
    */
  def validate: Action[AnyContent] = Action.async { implicit request =>
    performAction { _ =>
      Future(Ok(views.html.forbidden()))
    }({
      val requestVals = getUserCredentialsFromRequestBody(request)
      userDao
        .getUser(requestVals.username.get)
        .map {
          case Some(user: User) if checkpw(requestVals.password.get, user.passwordHash) => Redirect(routes.CockpitController.cockpit())
            .addingToSession("username" -> user.username, "Csrf-Token" -> play.filters.csrf.CSRF.getToken.get.value)
          case _ => Ok(views.html.login(Some("Invalid username or password")))
        }
        .recover { case _ => Ok(views.html.exception("Internal exception"))}
    })
  }


  /** Logout user to login page and remove their old session
    *
    * @return Action[AnyContent]
    */
  def logout: Action[AnyContent] = Action.async { implicit request =>
    Future(Redirect(routes.LoginLogoutController.index()).withNewSession)
  }
}
