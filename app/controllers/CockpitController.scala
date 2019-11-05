package controllers

import javax.inject.{Inject, Singleton}
import models.dao.ExaminationDao
import models.dto.ExaminationView
import play.api.mvc.{Action, AbstractController, AnyContent, ControllerComponents}
import play.api.http.{Status => HttpStatus}
import scala.concurrent.{ExecutionContext, Future}
import play.api.libs.ws._
import models.utilities.CustomConfig.chooseMLUrl
import play.api.libs.json.{JsObject, JsString}


/** Controller class for managing page with examinations
  *
  * @param examinationDao
  * @param loggingAction
  * @param controllerComponents
  * @param wsClient
  * @param executionContext
  */
@Singleton
class CockpitController @Inject()(examinationDao: ExaminationDao, loggingAction: LoggingAction, controllerComponents: ControllerComponents, wsClient: WSClient)(implicit executionContext: ExecutionContext)
  extends AbstractController(controllerComponents) {


  /**
    * Importing implicit for getting names of all examinations
    */
  import models.implicits.ExaminationsImplicits.examinationNames


  /** Loading content of the page
    *
    * @return Action[AnyContent]
    */
  def cockpit: Action[AnyContent] = loggingAction.async { implicit request =>
    val username = request.session.get("username").get
    examinationDao
      .getAllExaminationsFromUser(username)
      .map(x => Ok(views.html.cockpit(username, x.sorted.map(ExaminationView(_)))))
      .recover { case _ => Ok(views.html.exception("Internal exception")) }
  }


  /** Returns skinLesionsForm to the application view
    *
    * @return Action[AnyContent]
    */
  def skinLesionsForm: Action[AnyContent] = loggingAction.async { implicit request =>
    Future(Ok(views.html.examination("Skin lesions classifier")))
  }


  /** Returns breastCancerForm to the application view
    *
    * @return Action[AnyContent]
    */
  def breastCancerForm: Action[AnyContent] = loggingAction.async { implicit request =>
    Future(Ok(views.html.examination("Brest cancer classifier")))
  }


  /** The method in which the decision is made to send information from the view to the appropriate external service
    * After that user gets updated cockpit page or exception information
    *
    * @return Action[AnyContent]
    */
  def mlService: Action[AnyContent] = loggingAction.async { implicit request =>
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