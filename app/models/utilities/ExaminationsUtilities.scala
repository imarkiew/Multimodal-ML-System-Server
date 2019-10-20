package models.utilities

import models.dto.ExaminationClientInfo
import play.api.libs.json.{Json, Reads}
import play.api.mvc.{AnyContent, Request}


object ExaminationsUtilities {

  def getExaminationFromClientRequestBody(request: Request[AnyContent])(implicit examinationsReader: Reads[ExaminationClientInfo]): ExaminationClientInfo = {

    val body = request.body.asJson

    body.map(_ => Json.fromJson[ExaminationClientInfo](body.get).get).get
  }
}
