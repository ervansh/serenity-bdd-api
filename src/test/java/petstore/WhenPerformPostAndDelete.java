package petstore;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;

@RunWith(SerenityRunner.class)
public class WhenPerformPostAndDelete {
	
	private static final String BASE_URL = "https://petstore.swagger.io/v2";
	
	@Test
	public void createAndVerifyNewPet() throws IOException {
		
		String	postdatafile = FileUtils.readFileToString(new File(Constants.postDataFile), "UTF-8");
		SerenityRest.given()
		.baseUri(BASE_URL)
		.auth()
		.none()
		.contentType("application/json").body(postdatafile)
				.post("/pet")
				.then()
				.statusCode(200);
	}

	@Test
	public void deletePetAndVerify() {
		// First, create the pet
		String newPet = "{" + "\"id\": 12346," + "\"category\": { \"id\": 1, \"name\": \"Dogs\" },"
				+ "\"name\": \"Buddy\"," + "\"photoUrls\": [\"url1\", \"url2\"],"
				+ "\"tags\": [{ \"id\": 1, \"name\": \"tag1\" }]," + "\"status\": \"available\"" + "}";
		SerenityRest.given().baseUri("https://petstore.swagger.io/v2").contentType("application/json").body(newPet)
				.post("/pet");

		// Now delete the pet
		SerenityRest.given().baseUri("https://petstore.swagger.io/v2").delete("/pet/12346").then().statusCode(200);

		// Check that the pet no longer exists, should return 404
		SerenityRest.given().baseUri(BASE_URL).get("/pet/12346").then().statusCode(404);
	}
}
