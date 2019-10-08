package controllers

import javax.inject.{Inject, Singleton}
import play.api.mvc.{AbstractController, ControllerComponents, RequestHeader}


@Singleton
class LogInController @Inject()(cc: ControllerComponents) extends AbstractController(cc){

  def index = Action { implicit request =>
    Ok(views.html.index(routes.LogInController.login().toString))
  }

  def login = Action { implicit request =>
    Ok(views.html.login())
  }

  def validate = Action { implicit request =>
    val postVals = request.body.asFormUrlEncoded
    println(postVals)
    Ok("Ok")
  }

}
