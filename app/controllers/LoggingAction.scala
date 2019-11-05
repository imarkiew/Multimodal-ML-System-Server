package controllers

import javax.inject.Inject
import play.api.mvc._
import scala.concurrent.{ExecutionContext, Future}
import play.api.mvc.Results.Ok


/** Controller class for redirecting not logged users
  *
  * @param parser
  * @param executionContext
  */
class LoggingAction @Inject()(parser: BodyParsers.Default)(implicit executionContext: ExecutionContext) extends ActionBuilderImpl(parser) {


  /** Redirect user if is not logged in
    *
    * @param request
    * @param block
    * @tparam A
    * @return
    */
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