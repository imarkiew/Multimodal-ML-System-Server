import play.api.test._


object ExaminationImplicitConversionTest extends PlaySpecification {

  import models.implicits.ExaminationsImplicits.{examinationResultsStringAsJsonToMap, examinationResultsMapToStringAsJson}

  val json = "{\"vasc\":0.0251571853,\"df\":0.0549332723}"
  val jsonMap = Map(("vasc", 0.0251571853), ("df", 0.0549332723))

  "Examination implicit " should {

    "return proper Map[String, String] for input json String" in {
      val jsonTransformed: Map[String, Double] = json
      jsonTransformed mustEqual jsonMap
    }

    "return proper json String for input Map[String, String]" in {
      val mapTransformed: String = jsonMap
      json mustEqual mapTransformed
    }

  }
}
