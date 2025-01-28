package petstore;

import static org.hamcrest.Matchers.containsString;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;

@RunWith(SerenityRunner.class)
public class WhenPetstoreAPITests {

	private static final String BASE_URL = "https://petstore.swagger.io/v2/pet/findByStatus?status=available";

	@Test
	public void verifyStatusCodeIs200() {
		SerenityRest.given().baseUri(BASE_URL).when().get().then().statusCode(200);
	}

	@Test
	public void verifyStatusAndCountOfPets() {
		List<Map<String, Object>> pets = SerenityRest.given().queryParam("status", "available").get(BASE_URL).then()
				.statusCode(200).extract().jsonPath().getList("");

		// Verify that the status of each pet is "available"
		for (Map<String, Object> pet : pets) {
			Assert.assertEquals("The pet status should be available", "available", pet.get("status"));
		}

		// Verify that the number of pets is greater than 5
		Assert.assertTrue("Number of pets should be greater than 5", pets.size() > 5);
		System.out.println("Number of pets returned: " + pets.size());
	}

	@Test
	public void verifyResponseHeaders() {
		SerenityRest.given().baseUri(BASE_URL).when().get().then().statusCode(200).and()
				.header("Content-Type", containsString("application/json"))
				.header("Access-Control-Allow-Methods", containsString("GET"));
	}

	@Test
	public void verifyFailureOfTestandStatusCode404() {
		SerenityRest.given().get(BASE_URL + "/invalid").then().statusCode(404);
	}
}
