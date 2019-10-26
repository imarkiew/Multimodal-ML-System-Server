package models.utilities

import models.dto.UserCredentials
import play.api.libs.json.{JsValue, Json, Reads}
import play.api.mvc.{AnyContent, Request}


object UserCredentialsUtilities {

  def getUserCredentials(body: Option[JsValue])(implicit userCredentialsReader: Reads[UserCredentials]): UserCredentials =
    body
      .map(_ => Json.fromJson[UserCredentials](body.get).get)
      .getOrElse(UserCredentials(None, None))

  def getUserCredentialsFromRequestBody(request: Request[AnyContent])(implicit userCredentialsReader: Reads[UserCredentials]): UserCredentials =
    getUserCredentials(request.body.asJson)

  def performAction[T](loggedAction: String => T)(notLoggedAction: T)(implicit request: Request[AnyContent]): T = {

    implicit val _: Request[AnyContent] = request

    request.session.get("username")
      .map(loggedAction)
      .getOrElse(notLoggedAction)
  }
}
