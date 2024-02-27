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

public class AddAuditForm extends ExtentReport{
	
	public static String formid;
	
	@Test(priority=2)
	public static void addauditform()throws IOException
	{
		String name = new Object(){}.getClass().getEnclosingMethod().getName();
		extent = extentreport.createTest(name);
		Random r = new Random();
		int amount = r.nextInt(10,100);
        String formname = "Form Created by Automation_" + amount;
        System.out.println("Form name is " +formname);
        String AccessToken = BearerToken.generateToken();
		byte[] b = Files.readAllBytes(Paths.get("AddAuditform.json"));
		
		
	      String bdy = new String(b);
		  JSONObject json = new JSONObject(bdy);
		  json.put("formName", formname);
		  String strbdy = json.toString();

	     
	
				
	      
	      Response response = RestAssured.given().contentType(JSON).
	    		  header("Authorization", "Bearer " +AccessToken).body(strbdy)
	    		  .when().post("https://opsapi.workpulse.com/api/AuditForm/form")
	    		  .then()
	    		  .statusCode(200)
	    		  .log().all().extract().response();
	      
	     String formid= response.asString();
	      
	     
	    
	      System.out.println("Form generated with id as: " + formid);  
	      extent.log(Status.INFO, "Form generated with id as: " + formid);
	      
	}
	
	@Test(priority=3)
	public static void addformquestion()throws IOException
	{
		String name = new Object(){}.getClass().getEnclosingMethod().getName();
		extent = extentreport.createTest(name);
		
		String AccessToken = BearerToken.generateToken();
		byte[] b = Files.readAllBytes(Paths.get("AuditFormSaveQuestions.json"));
		
		
	      String bdy = new String(b);
		  JSONObject json = new JSONObject(bdy);
		  json.put("formId", formid);
		  json.put("details.formId", formid);
		  String strbdy = json.toString();

	     
	
				
	      
	      Response response = RestAssured.given().contentType(JSON).
	    		  header("Authorization", "Bearer " +AccessToken).body(strbdy)
	    		  .when().post("https://opsapi.workpulse.com/api/AuditForm/saveQuestions")
	    		  .then()
	    		  .statusCode(200)
	    		  .log().all().extract().response();
	      
	    
	      
	}
	
	
	}


