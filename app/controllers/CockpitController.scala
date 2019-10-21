package controllers

import javax.inject.{Inject, Singleton}
import models.utilities.UserCredentialsUtilities.performAction
import play.api.mvc.{AbstractController, ControllerComponents}
import scala.concurrent.{ExecutionContext, Future}
import models.utilities.Tmp.exams
import play.api.libs.ws._
import models.utilities.CustomConfig.customConfig


@Singleton
class CockpitController @Inject()(controllerComponents: ControllerComponents, wsClient: WSClient)(implicit executionContext: ExecutionContext)
  extends AbstractController(controllerComponents){

  import models.implicits.ExaminationsImplicits._

  def cockpit = Action.async { implicit request =>
    performAction { _ =>
      Future(Ok(views.html.cockpit(request.session.get("username").get, exams)))
    }(Future(Ok(views.html.login(None))))
  }

  def skinLesionsForm = Action.async { implicit request =>
    Future(Ok(views.html.examination("Skin lesions classifier")))
  }

  def skinLesions = Action.async { implicit request =>

    val futureResponse = wsClient.url(customConfig.skinLesionsUrl).post(request.body.asJson.get)
    Future(Ok(views.html.cockpit(request.session.get("username").get, exams)))
  }


}
