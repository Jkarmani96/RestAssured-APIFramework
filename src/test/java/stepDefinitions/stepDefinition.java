package stepDefinitions;

import static io.restassured.RestAssured.given;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import POJO.AddPlace;
import POJO.Location;
import Resources.APIResources;
import Resources.TestDataBuild;
import Resources.Utils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import static org.junit.Assert.*;

public class stepDefinition extends Utils {
	RequestSpecification res;
	ResponseSpecification resp;
	Response response;
	public static String getPlaceId;

	TestDataBuild data = new TestDataBuild();

	@Given("Add Place Payload with {string} {string} {string}")
	public void add_place_payload_with(String name, String address, String language) throws IOException {
		// Write code here that turns the phrase above into concrete actions

		resp = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		res = given().spec(requestSpecification()).body(data.AddPlacePayload(name, address, language));
	}

	@When("user calls {string} with {string} http request")
	public void user_calls_with_http_request(String resource, String httpMethod) {
		// Write code here that turns the phrase above into concrete actions

		APIResources resourceAPI = APIResources.valueOf(resource);
		System.out.println(resourceAPI.getResource());

		if (httpMethod.equalsIgnoreCase("POST"))
			response = res.when().post(resourceAPI.getResource());
		else if(httpMethod.equalsIgnoreCase("DELETE"))
			response = res.when().delete(resourceAPI.getResource());
		else if (httpMethod.equalsIgnoreCase("GET"))
			response = res.when().get(resourceAPI.getResource());
	}

	@Then("the API call got success with status code {int}")
	public void the_api_call_got_success_with_status_code(Integer int1) {
		// Write code here that turns the phrase above into concrete actions

		assertEquals(response.getStatusCode(), 200);
//		response.then().assertThat().statusCode(200).extract().response();			
	}

	@Then("{string} in response body is {string}")
	public void in_response_body_is(String keyValue, String ExpectedString) {
		// Write code here that turns the phrase above into concrete actions
		String resp = response.asString();

		JsonPath js = new JsonPath(resp);
		assertEquals(getJsonPath(response,keyValue), ExpectedString);
	}
	
	@Then("verify place_Id created maps to {string} using {string}")
	public void verify_place_id_created_maps_to_using(String expectedName, String resource) throws IOException {
	    // Write code here that turns the phrase above into concrete actions
	    
		getPlaceId = getJsonPath(response, "place_id");		
		res = given().spec(requestSpecification()).queryParam("place_id", getPlaceId);
		user_calls_with_http_request(resource,"GET");
		
		String actualName = getJsonPath(response,"name");
		assertEquals(actualName, expectedName);
		
	}

	@Given("DeletePlace Payload")
	public void delete_place_payload() throws IOException {
	    // Write code here that turns the phrase above into concrete actions
		
		res = given().spec(requestSpecification()).body(data.deletePlace(getPlaceId));
		
	}
}
