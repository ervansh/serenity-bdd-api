package petstore;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import io.restassured.response.Response;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;

@RunWith(SerenityRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WhenPerformPostAndDelete {

	private static final String BASE_URL = "https://petstore.swagger.io/v2";
	private String postdatafile;
	private static String id;

	@Before
	public void setUp() throws IOException {
		postdatafile = FileUtils.readFileToString(new File(Constants.postDataFile), "UTF-8");
	}

	@Test
	public void createAndVerifyNewPet() {
		Response response = SerenityRest.given().baseUri(BASE_URL).auth().none().contentType("application/json")
				.body(postdatafile).post("/pet").then().statusCode(200).extract().response();
		id = response.jsonPath().get("id").toString();
		System.out.println(id + " id is");
		Assertions.assertNotNull(id, "Id should not be null.");
	}

	@Test
	public void deletePetAndVerify() {
		// Delete the pet
		SerenityRest.given().baseUri(BASE_URL).delete("/pet/" + id).then().statusCode(200);

		// Check that the pet no longer exists, should return 404
		SerenityRest.given().baseUri(BASE_URL).get("/pet/" + id).then().statusCode(404);
	}
}
