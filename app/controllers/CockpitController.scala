package controllers

import javax.inject.{Inject, Singleton}
import models.dao.ExaminationDao
import models.dto.ExaminationView
import play.api.mvc.{AbstractController, ControllerComponents}
import play.api.http.{Status => HttpStatus}
import scala.concurrent.{ExecutionContext, Future}
import play.api.libs.ws._
import models.utilities.CustomConfig.chooseMLUrl
import play.api.libs.json.{JsObject, JsString}


@Singleton
class CockpitController @Inject()(examinationDao: ExaminationDao, loggingAction: LoggingAction, controllerComponents: ControllerComponents, wsClient: WSClient)(implicit executionContext: ExecutionContext)
  extends AbstractController(controllerComponents) {

  import models.implicits.ExaminationsImplicits.examinationNames

  def cockpit = loggingAction.async { implicit request =>
    val username = request.session.get("username").get
    examinationDao
      .getAllExaminationsFromUser(username)
      .map(x => Ok(views.html.cockpit(username, x.sorted.map(ExaminationView(_)))))
      .recover { case _ => Ok(views.html.exception("Internal exception")) }
  }

  def skinLesionsForm = loggingAction.async { implicit request =>
    Future(Ok(views.html.examination("Skin lesions classifier")))
  }

  def breastCancerForm = loggingAction.async { implicit request =>
    Future(Ok(views.html.examination("Brest cancer classifier")))
  }

  def mlService = loggingAction.async { implicit request =>
    val body = request.body.asJson.get.as[JsObject]
    val jsonRequest = body + ("username" -> JsString(request.session.get("username").get))
    val mlServicePath = (body \ "typeOfMLService").as[String]

    chooseMLUrl(mlServicePath) match {
      case Some(path: String) =>
        wsClient
        .url (path)
        .post (jsonRequest)
        .map { response =>
          if (response.status == HttpStatus.OK) {
              Redirect(routes.CockpitController.cockpit())
            } else {
              Ok(views.html.exception("Internal exception"))
            }
        }
      case _ => Future(Ok(views.html.exception("Internal exception")))
    }
  }
}