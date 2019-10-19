package controllers

import javax.inject.{Inject, Singleton}
import play.api.mvc.{AbstractController, ControllerComponents}
import scala.concurrent.{ExecutionContext, Future}


@Singleton
class CockpitController @Inject()(controllerComponents: ControllerComponents)(implicit executionContext: ExecutionContext)
  extends AbstractController(controllerComponents){

  def skinLesionsForm = Action.async { implicit request =>
    Future(Ok(views.html.skinLesionsForm()))
  }

  def skinLesions = Action.async { implicit request =>
    println(request.body.asJson)
    Future(Ok)
  }


}
