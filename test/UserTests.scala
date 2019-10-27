import models.dto.UserCredentials
import models.utilities.UserCredentialsUtilities.getUserCredentials
import play.api.libs.json.Json
import play.api.test._


object UserTests extends PlaySpecification {

  import models.implicits.UserImplicits.userCredentialsReader

  "getUserCredentials" should {

    "return proper case class for input json" in {
      val json = Some(Json.parse("""{"username":"user", "password":"pass"}"""))
      getUserCredentials(json) mustEqual UserCredentials(Some("user"), Some("pass"))
    }

    "return proper case class for input json missing value" in {
      val json = Some(Json.parse("""{"username":"user", "password":null}"""))
      getUserCredentials(json) mustEqual UserCredentials(Some("user"), None)
    }

    "return proper case class for no json" in {
      getUserCredentials(None) mustEqual UserCredentials(None, None)
    }

  }
}
