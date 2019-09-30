package com.toolsQA;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

public class SimpleGetTest {

	@Test
	public void GetWeatherDetails() {
		// Specify the base URL to the RESTful web service
		RestAssured.baseURI = "http://restapi.demoqa.com/utilities/weather/city";

		// Get the RequestSpecification of the request that you want to sent
		// to the server. The server is specified by the BaseURI that we have
		// specified in the above step.
		RequestSpecification httpRequest = RestAssured.given();

		// Make a request to the server by specifying the method Type and the method
		// URL.
		// This will return the Response from the server. Store the response in a
		// variable.
		Response response = httpRequest.request(Method.GET, "/Jabalpur");
		int statusCode = response.getStatusCode();

		// Assert that correct status code in return
		Assert.assertEquals(statusCode/* actual value */, 200/* expected */, "Correct Status code returned");

		// Now let us print the body of the message to see what response
		// we have recieved from the server
		String responseBody = response.getBody().asString();
		System.out.println("Response Body is =>  " + responseBody);
		System.out.println("------------");

	}

	@Test
	public void GetWeatherDetailsInvalidCity() {
		RestAssured.baseURI = "http://restapi.demoqa.com/utilities/weather/city";
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.get("/1231231231");
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode /* actual value */, 400 /* expected value */, "Correct status code returned");
	}

	@Test
	public void GetWeatherStatusLine() {
		RestAssured.baseURI = "http://restapi.demoqa.com/utilities/weather/city";
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.get("/Jabalpur");

		// Get the status line from the Response and store it in a variable called
		// statusLine
		String statusLine = response.getStatusLine();
		Assert.assertEquals(statusLine /* actual value */, "HTTP/1.1 200 OK" /* expected value */,
				"Correct status code returned");
	}

	@Test
	public void GetWeatherHeaders() {
		RestAssured.baseURI = "http://restapi.demoqa.com/utilities/weather/city";
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.get("/Jabalpur");
		;

		// Reader header of a give name. In this line we will get
		// Header named Content-Type
		String contentType = response.header("Content-Type");
		System.out.println("Content-Type value: " + contentType);
		System.out.println("------------");
		// Reader header of a give name. In this line we will get
		// Header named Server
		String serverType = response.header("Server");
		System.out.println("Server value: " + serverType);
		System.out.println("------------");
		// Reader header of a give name. In this line we will get
		// Header named Content-Encoding
		String acceptLanguage = response.header("Content-Encoding");
		System.out.println("Content-Encoding: " + acceptLanguage);
		System.out.println("------------");
	}

	@Test
	public void IteratingOverHeaders() {
		RestAssured.baseURI = "http://restapi.demoqa.com/utilities/weather/city";
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.get("/Jabalpur");

		Headers allHeaders = response.headers();

		for (Header header : allHeaders) {
			System.out.println("Key" + header.getName() + "VAlue : " + header.getValue());
		}
		System.out.println("------------");
	}

	@Test
	public void getWeathreHeaders() {
		RestAssured.baseURI = "http://restapi.demoqa.com/utilities/weather/city";
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.get("/Jabalpur");

		String contentType = response.header("Content-Type");
		Assert.assertEquals(contentType /* actual value */, "application/json" /* expected value */);

		// Reader header of a give name. In this line we will get
		// Header named Server
		String serverType = response.header("Server");
		Assert.assertEquals(serverType /* actual value */, "nginx" /* expected value */);

		// Reader header of a give name. In this line we will get
		// Header named Content-Encoding
		String contentEncoding = response.header("Content-Encoding");
		Assert.assertEquals(contentEncoding /* actual value */, "gzip" /* expected value */);
	}

	@Test
	public void VerifyCityInJsonResponse() {
		RestAssured.baseURI = "http://restapi.demoqa.com/utilities/weather/city";
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.get("/Nagpur");

		// First get the JsonPath object instance from the Response interface
		JsonPath jsonPathEvaluator = response.jsonPath();

		// Then simply query the JsonPath object to get a String value of the node
		// specified by JsonPath: City (Note: You should not put $. in the Java code)
		String city = jsonPathEvaluator.get("City");

		// Let us print the city variable to see what we got
		System.out.println("City received from Response " + city);
		System.out.println("-----------------");

		// Validate the response
		Assert.assertEquals(city, "Nagpur", "Correct city name received in the Response");

	}

	@Test
	public void WeatherMessageBody() {
		RestAssured.baseURI = "https://api.mapbox.com/utilities/weather/city";
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.get("/Hyderabad");

		// Retrieve the body of the Response
		ResponseBody body = response.getBody();

		// To check for sub string presence get the Response body as a String.
		// Do a String.contains
		String bodyAsString = body.asString();

		// convert the body into lower case and then do a comparison to ignore casing.
		Assert.assertEquals(bodyAsString.toLowerCase().contains("hyderabad") /* Expected value */,
				true /* Actual Value */, "Response body contains Hyderabad");
	}

	@Test
	public void DisplayAllNodesInWeatherAPI() {
		RestAssured.baseURI = "http://restapi.demoqa.com/utilities/weather/city";
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.get("/Nagpur");

		// First get the JsonPath object instance from the Response interface
		JsonPath jsonPathEvaluator = response.jsonPath();

		// Let us print the city variable to see what we got
		System.out.println("City received from Response " + jsonPathEvaluator.get("City"));

		// Print the temperature node
		System.out.println("Temperature received from Response " + jsonPathEvaluator.get("Temperature"));

		// Print the humidity node
		System.out.println("Humidity received from Response " + jsonPathEvaluator.get("Humidity"));

		// Print weather description
		System.out.println("Weather description received from Response " + jsonPathEvaluator.get("Weather"));

		// Print Wind Speed
		System.out.println("City received from Response " + jsonPathEvaluator.get("WindSpeed"));

		// Print Wind Direction Degree
		System.out.println("City received from Response " + jsonPathEvaluator.get("WindDirectionDegree"));
		System.out.println("-----------------");
	}

	public void RegistrationSuccessful() {
		RestAssured.baseURI = "http://restapi.demoqa.com/customer";
		RequestSpecification request = RestAssured.given();

		JSONObject requestParams = new JSONObject();
		requestParams.put("FirstName", "Virender"); // Cast
		requestParams.put("LastName", "Singh");
		requestParams.put("UserName", "sdimpleuser2dd2011");
		requestParams.put("Password", "password1");
		requestParams.put("Email", "sample2ee26d9@gmail.com");

		request.body(requestParams.toJSONString());
		Response response = request.get("/register");

		int statusCode = response.getStatusCode();
		System.out.println("The status code recieved: " + statusCode);

		System.out.println("Response body: " + response.body().asString());
	}
}
