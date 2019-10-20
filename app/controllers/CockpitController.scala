package controllers

import javax.inject.{Inject, Singleton}
import models.dto.ExaminationClientInfo
import models.utilities.UserCredentialsUtilities.performAction
import play.api.mvc.{AbstractController, ControllerComponents}
import scala.concurrent.{ExecutionContext, Future}
import models.utilities.Tmp.exams
import play.api.libs.json.Json
import models.utilities.ExaminationsUtilities.getExaminationFromClientRequestBody


@Singleton
class CockpitController @Inject()(controllerComponents: ControllerComponents)(implicit executionContext: ExecutionContext)
  extends AbstractController(controllerComponents){

  import models.implicits.ExaminationsImplicits._

  def cockpit = Action.async { implicit request =>
    performAction { _ =>
      Future(Ok(views.html.cockpit(request.session.get("username").get, exams)))
    }(Future(Ok(views.html.login(None))))
  }

  def skinLesionsForm = Action.async { implicit request =>
    Future(Ok(views.html.skinLesionsForm()))
  }

  def skinLesions = Action.async { implicit request =>

    val skinLesionsClientInfo = getExaminationFromClientRequestBody(request)
    println(skinLesionsClientInfo)
    Future(Ok)
  }


}
