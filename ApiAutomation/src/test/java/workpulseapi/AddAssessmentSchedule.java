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

public class AddAssessmentSchedule extends ExtentReport{
	
	public static String id;
	
	@Test(priority=2)
	public static void addassessmentschedule()throws IOException
	{
		String name = new Object(){}.getClass().getEnclosingMethod().getName();
		extent = extentreport.createTest(name);
		Random r = new Random();
		int amount = r.nextInt(100,1000);
        String Schedulename = "Assessment Schedule created by Automation_" + amount;
        String AccessToken = BearerToken.generateToken();
		byte[] b = Files.readAllBytes(Paths.get("Models","AddAssessmentSchedule.json"));
		
		
	      String bdy = new String(b);
		  JSONObject json = new JSONObject(bdy);
		  json.put("name", Schedulename);
		  String strbdy = json.toString();

	     
	
				
	      
	      Response response = RestAssured.given().contentType(JSON).
	    		  header("Authorization", "Bearer " +AccessToken).body(strbdy)
	    		  .when().post("https://opsapi.workpulse.com/api/People/schedule/preview")
	    		  .then()
	    		  .statusCode(200)
	    		  .log().all().extract().response();
	      
	      
	}

}
