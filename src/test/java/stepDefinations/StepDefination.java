package stepDefinations;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import pojo.AddPlace;
import pojo.Location;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;


public class StepDefination extends Utils{
	
	RequestSpecification res;
	ResponseSpecification resspec;
	Response response;
	static String place_id;
	TestDataBuild data = new TestDataBuild();
	
	@Given("Add Place Payload with {string} {string} {string}")
	public void add_place_payload_with(String name, String language, String address) throws IOException { 
		
		//RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		res = given().spec(requestSpecification()).
		body(data.AddPlace(address, language, name)).relaxedHTTPSValidation();
		
	}
	@When("user calls {string} with {string} https request")
	public void user_calls_with_https_request(String resource, String httpMethod) {
	    
		
		//Enum class constructor will be called with value of resource which you pass
		APIResources apiRes = APIResources.valueOf(resource);
		System.out.println(apiRes.getResource());
		//Response Spec Builder
		resspec = new ResponseSpecBuilder().expectStatusCode(200).
				expectContentType(ContentType.JSON).build();
		
		if(httpMethod.equalsIgnoreCase("POST"))
		response = res.relaxedHTTPSValidation().when().post(apiRes.getResource());
		
		else if(httpMethod.equalsIgnoreCase("GET"))
			response = res.relaxedHTTPSValidation().when().get(apiRes.getResource());
		
		
	}
	
	@Then("the API call is success with status code is {int}")
	public void the_api_call_is_success_with_status_code_is(Integer int1) {
	    
		assertEquals(200, response.getStatusCode());
	}
	@Then("{string} in response body is {string}")
	public void in_response_body_is(String key, String value) {
	    
		assertEquals(getJsonPathVaue(response, key), value);
	}
	
	@Then("verify that place_Id created maps to {string} using {string}")
	public void verify_that_place_id_created_maps_to_using(String ExpectedName, String resource) throws IOException {
	    
		//need to store place id from post method
		place_id = getJsonPathVaue(response, "place_id");
		
		//need to create requestSpecification for Get Place API
		res = given().spec(requestSpecification()).queryParam("place_id", place_id);
		user_calls_with_https_request(resource, "GET");
		String ActualName = getJsonPathVaue(response, "name");
		assertEquals(ActualName, ExpectedName);
		
	}
	
	@Given("Delete Place payload")
	public void delete_place_payload() throws IOException {
	    
		//create Request Spec
		res = given().spec(requestSpecification()).body(data.deletePlacePayload(place_id));
	}
	
	


}
