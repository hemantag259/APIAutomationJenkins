package workpulseapi;

import static io.restassured.http.ContentType.JSON;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONObject;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import baseclass.BearerToken;
import baseclass.ExtentReport;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class AddDeskTicket extends ExtentReport {
	
	
	@Test(priority=2)
	public static void addnetworkdeskticket() throws IOException
	{
		
		String name = new Object(){}.getClass().getEnclosingMethod().getName();
		extent = extentreport.createTest(name);
		byte[] b = Files.readAllBytes(Paths.get("AddDeskTicket.json"));
		
		String AccessToken = BearerToken.generateToken();
	    String bdy = new String(b);
		  

	     
	
				
	      
	      Response response = RestAssured.given().contentType(JSON).
	    		  header("Authorization", "Bearer " +AccessToken).body(bdy)
	    		  .when().post("https://opsapi.workpulse.com/api/desk/ticket")
	    		  .then()
	    		  .statusCode(200)
	    		  .log().all().extract().response();
	      JsonPath jsonpath = response.jsonPath();
	       String taskId = jsonpath.getString("taskId");
	      System.out.println("Ticket Id: " + taskId);  
	      extent.log(Status.INFO, "Desk Ticket generated with id as " + taskId);
	}


	}


