import models.dto.Examination
import play.api.test._


/** Object with test for examinations
  *
  */
object ExaminationTests extends PlaySpecification {

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

  "Examination ordering" should {

    "return Seq[Examination] in descending order for input Seq[Examination]" in {
      val examinations = Seq(
        Examination(1, "", None, "2018-10-27T10:17:47.702Z", Map[String, Double](), "", ""),
        Examination(1, "", None, "2018-11-27T10:17:47.702Z", Map[String, Double](), "", ""),
        Examination(1, "", None, "2018-10-27T10:19:47.702Z", Map[String, Double](), "", ""),
        Examination(1, "", None, "2019-10-27T10:17:47.702Z", Map[String, Double](), "", ""),
        Examination(1, "", None, "2018-05-27T10:17:47.702Z", Map[String, Double](), "", ""),
        Examination(1, "", None, "2018-10-29T10:17:47.702Z", Map[String, Double](), "", ""),
        Examination(1, "", None, "2018-10-27T10:17:47.705Z", Map[String, Double](), "", "")
      )

      val sortedExaminations = Seq(
        Examination(1, "", None, "2019-10-27T10:17:47.702Z", Map[String, Double](), "", ""),
        Examination(1, "", None, "2018-11-27T10:17:47.702Z", Map[String, Double](), "", ""),
        Examination(1, "", None, "2018-10-29T10:17:47.702Z", Map[String, Double](), "", ""),
        Examination(1, "", None, "2018-10-27T10:19:47.702Z", Map[String, Double](), "", ""),
        Examination(1, "", None, "2018-10-27T10:17:47.705Z", Map[String, Double](), "", ""),
        Examination(1, "", None, "2018-10-27T10:17:47.702Z", Map[String, Double](), "", ""),
        Examination(1, "", None, "2018-05-27T10:17:47.702Z", Map[String, Double](), "", "")
      )

      examinations.sorted mustEqual sortedExaminations
    }
  }

}
