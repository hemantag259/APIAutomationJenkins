package workpulseapi;

import static io.restassured.http.ContentType.JSON;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

import org.json.*;

import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import baseclass.BearerToken;
import baseclass.ExtentReport;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class AddBookTask extends ExtentReport{
	
	public static String taskgeneratedname;
	public static String taskid;
	public static String groupid;
	public static String groupdetailsid;
	
	@Test(priority=2)
	public static void addbooktask()throws IOException
	{
		String name = new Object(){}.getClass().getEnclosingMethod().getName();
		extent = extentreport.createTest(name);
		Random r = new Random();
		int amount = r.nextInt(10,1000);
        String taskname = "Task Created by Automation_" + amount;
        String AccessToken = BearerToken.generateToken();
		byte[] b = Files.readAllBytes(Paths.get("Models","AddBookTask.json"));
		
		
	      String bdy = new String(b);
		  JSONObject json = new JSONObject(bdy);
		  json.put("taskName", taskname);
		  String strbdy = json.toString();

	     
	
				
	      
	      Response response = RestAssured.given().contentType(JSON).
	    		  header("Authorization", "Bearer " +AccessToken).body(strbdy)
	    		  .when().post("https://opsapi.workpulse.com/api/booktask")
	    		  .then()
	    		  .statusCode(200)
	    		  .log().all().extract().response();
	      JsonPath jsonpath = response.jsonPath();
	      taskgeneratedname = jsonpath.getString("taskName");
	      taskid = jsonpath.getString("id");
	      groupid = jsonpath.getString("groups.id");
	      groupdetailsid = jsonpath.getString("groups.details.id");
	      
	      System.out.println("Task generated with name as: " + taskgeneratedname);  
	      extent.log(Status.INFO, "Task generated with name as: " + taskgeneratedname);
	      
	}

	@Test(priority=3)
	public static void addlocation()throws IOException
	{
		String name = new Object(){}.getClass().getEnclosingMethod().getName();
		extent = extentreport.createTest(name);
        String AccessToken = BearerToken.generateToken();
		byte[] b = Files.readAllBytes(Paths.get("Models","AddBookTaskLocation.json"));
		
		
	    String bdy = new String(b);
	    
	    JSONArray jarray = new JSONArray(bdy);

	    String strbdy = jarray.toString();
	
				
	      
	      Response response = RestAssured.given().contentType(JSON).
	    		  header("Authorization", "Bearer " +AccessToken).body(strbdy).
	    		  pathParam("taskid", taskid)
	    		  .when().post("https://opsapi.workpulse.com/api/booktask/{taskid}/locations")
	    		  .then()
	    		  .statusCode(200)
	    		  .log().all().extract().response();
	      
	      extent.log(Status.INFO, "Location Assigned successfully to the Task");
	      
	}
	
	@Test(priority=4)
	public static void updatebooktaskwithschedule()throws IOException
	{
		String name = new Object(){}.getClass().getEnclosingMethod().getName();
		extent = extentreport.createTest(name);
	
      
        String AccessToken = BearerToken.generateToken();
		byte[] b = Files.readAllBytes(Paths.get("Models","UpdateBookTaskwithSchedule.json"));
		
		
	      String bdy = new String(b);
		  JSONObject json = new JSONObject(bdy);
		  json.put("taskName", taskgeneratedname);
		  json.put("groups.details.taskId", taskid);
		  json.put("groups.id", groupid);
		  json.put("groups.details.id", groupdetailsid);
		  json.put("id",taskid);
		  String strbdy = json.toString();

	     
	
				
	      
	      Response response = RestAssured.given().contentType(JSON).
	    		  header("Authorization", "Bearer " +AccessToken).body(strbdy)
	    		  .when().post("https://opsapi.workpulse.com/api/booktask")
	    		  .then()
	    		  .statusCode(200)
	    		  .log().all().extract().response();
	      
	    
	}

	
	
}
