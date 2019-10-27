package controllers

import javax.inject.{Inject, Singleton}
import models.dao.ExaminationDao
import models.dto.ExaminationView
import play.api.mvc.{AbstractController, ControllerComponents}
import play.api.http.{Status => HttpStatus}
import scala.concurrent.{ExecutionContext, Future}
import play.api.libs.ws._
import models.utilities.CustomConfig.customConfig
import play.api.libs.json.{JsObject, JsString}


@Singleton
class CockpitController @Inject()(examinationDao: ExaminationDao, loggingAction: LoggingAction, controllerComponents: ControllerComponents, wsClient: WSClient)(implicit executionContext: ExecutionContext)
  extends AbstractController(controllerComponents){

  import models.implicits.ExaminationsImplicits.examinationNames

  def cockpit = loggingAction.async { implicit request =>
    val username = request.session.get("username").get
    examinationDao
      .getAllExaminationsFromUser(username)
      .map(x => Ok(views.html.cockpit(username, x.map(ExaminationView(_)))))
      .recover { case _ => Ok(views.html.exception("Internal exception"))}
  }

  def skinLesionsForm = loggingAction.async { implicit request =>
    Future(Ok(views.html.examination("Skin lesions classifier")))
  }

  def skinLesions = loggingAction.async { implicit request =>
    val jsonRequest = request.body.asJson.get.as[JsObject] + ("username" -> JsString(request.session.get("username").get))
    wsClient
      .url(customConfig.skinLesionsUrl)
      .post(jsonRequest)
      .map {response =>
        if(response.status == HttpStatus.OK){
          Redirect(routes.CockpitController.cockpit())
        } else {
          Ok(views.html.exception("Internal exception"))
        }
      }
  }
}
