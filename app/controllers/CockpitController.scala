package controllers

import javax.inject.{Inject, Singleton}
import models.dao.ExaminationDao
import models.dto.ExaminationView
import models.utilities.UserCredentialsUtilities.performAction
import play.api.mvc.{AbstractController, ControllerComponents}
import play.api.http.{Status => HttpStatus}
import scala.concurrent.{ExecutionContext, Future}
import models.utilities.Tmp.exams
import play.api.libs.ws._
import models.utilities.CustomConfig.customConfig


@Singleton
class CockpitController @Inject()(examinationDao: ExaminationDao, loggingAction: LoggingAction, controllerComponents: ControllerComponents, wsClient: WSClient)(implicit executionContext: ExecutionContext)
  extends AbstractController(controllerComponents){

  import models.implicits.ExaminationsImplicits.examinationsNames

  def cockpit = loggingAction.async { implicit request =>
    performAction { _ =>
      Future(Ok(views.html.cockpit(request.session.get("username").get, exams)))
    }(Future(Ok(views.html.login(None))))
  }

  def skinLesionsForm = loggingAction.async { implicit request =>
    Future(Ok(views.html.examination("Skin lesions classifier")))
  }

  def skinLesions = loggingAction.async { implicit request =>
    val userName = request.session.get("username").get
    wsClient
      .url(customConfig.skinLesionsUrl)
      .post(request.body.asJson.get)
      .flatMap {response =>
        if(response.status == HttpStatus.OK){
          examinationDao
            .getAllExaminationsFromUser(userName)
            .map(examinations => Ok(views.html.cockpit(userName, examinations.map(ExaminationView(_)))))
        } else {
          Future(Ok(views.html.exception("Internal exception")))
        }
      }
  }


}
