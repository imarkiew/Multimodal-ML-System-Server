package filters

import javax.inject.Inject
import akka.stream.Materializer
import controllers.routes
import play.api.mvc._
import scala.concurrent.ExecutionContext
import scala.concurrent.Future
import play.api.mvc.Results.Ok


class AjaxRequestsFilter @Inject()(implicit val mat: Materializer, executionContext: ExecutionContext) extends Filter {

  def apply(nextFilter: RequestHeader => Future[Result])(requestHeader: RequestHeader): Future[Result] = {

    val isAjaxRequest = requestHeader.headers.get("X-Requested-With").contains("XMLHttpRequest")
    val isIndexOrAssetsOrLogoutRequest = requestHeader.uri == routes.LoginLogoutController.index().toString ||
      requestHeader.uri.startsWith("/versionedAssets") || requestHeader.uri == "/logout"

    if(isAjaxRequest || isIndexOrAssetsOrLogoutRequest){
      nextFilter(requestHeader)
    } else {
      Future(Ok(views.html.forbidden()))
    }
  }
}
