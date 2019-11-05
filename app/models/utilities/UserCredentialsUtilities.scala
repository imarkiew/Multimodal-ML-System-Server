package models.utilities

import models.dto.UserCredentials
import play.api.libs.json.{JsValue, Json, Reads}
import play.api.mvc.{AnyContent, Request}


/** Object with utilities for user credentials
  *
  */
object UserCredentialsUtilities {


  /** Get user credentials from json body and convert them to UserCredentials
    *
    * @param body
    * @param userCredentialsReader
    * @return UserCredentials
    */
  def getUserCredentials(body: Option[JsValue])(implicit userCredentialsReader: Reads[UserCredentials]): UserCredentials =
    body
      .map(_ => Json.fromJson[UserCredentials](body.get).get)
      .getOrElse(UserCredentials(None, None))


  /** Get user credentials from request body and convert them to UserCredentials
    *
    * @param request
    * @param userCredentialsReader
    * @return UserCredentials
    */
  def getUserCredentialsFromRequestBody(request: Request[AnyContent])(implicit userCredentialsReader: Reads[UserCredentials]): UserCredentials =
    getUserCredentials(request.body.asJson)


  /** Check if user is logged in and perform default action
    *
    * @param loggedAction
    * @param notLoggedAction
    * @param request
    * @tparam T
    * @return T
    */
  def performAction[T](loggedAction: String => T)(notLoggedAction: T)(implicit request: Request[AnyContent]): T = {

    implicit val _: Request[AnyContent] = request

    request.session.get("username")
      .map(loggedAction)
      .getOrElse(notLoggedAction)
  }
}
