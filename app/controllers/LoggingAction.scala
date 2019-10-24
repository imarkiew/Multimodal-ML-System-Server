package controllers

import javax.inject.Inject
import play.api.mvc._
import scala.concurrent.{ExecutionContext, Future}
import play.api.mvc.Results.Ok


class LoggingAction @Inject()(parser: BodyParsers.Default)(implicit executionContext: ExecutionContext) extends ActionBuilderImpl(parser) {

  override def invokeBlock[A](request: Request[A], block: Request[A] => Future[Result]): Future[Result] = {

    val isLogged = request
      .session
      .get("username")
      .isDefined

    if(isLogged){
      block(request)
    } else {
      Future(Ok(views.html.forbidden()))
    }
  }
}