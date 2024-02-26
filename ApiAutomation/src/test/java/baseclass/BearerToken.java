package baseclass;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import io.restassured.response.Response;
import io.restassured.RestAssured;
import io.restassured.RestAssured.*;
import io.restassured.path.json.JsonPath;
import static io.restassured.http.ContentType.JSON;


public class BearerToken  {
	
	


	
	
	public static String generateToken()
	{
		HashMap map = new HashMap();
		String accessToken;
		map.put("grant_type", "password");
		map.put("client_id", "app.WorkpulseAudit");
		map.put("client_secret", "rwVj4Kt6vEFMVWQ2");
		map.put("scope", "workpulseApi");
		map.put("acr_values", "tenant:4444");
		map.put("username", "hemant");
		map.put("password", "Sep@1989");
		
		Response response = 

				
				
			RestAssured.given().
				contentType("application/x-www-form-urlencoded").
				formParams(map).
				when().
				post("https://opslogin.workpulse.com/core/connect/token").
				then().
				log().all().
				extract().response();
		JsonPath jsonpath = response.jsonPath();
				accessToken = jsonpath.getString("access_token");
		System.out.println("Access Token: " + accessToken);  
		
		return accessToken;
	
				
	}
	
	
	
	
}