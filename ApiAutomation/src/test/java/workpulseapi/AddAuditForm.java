package workpulseapi;

import static io.restassured.http.ContentType.JSON;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
	public static String formname;
	
	@Test(priority=2)
	public static void addauditform()throws IOException
	{
		String name = new Object(){}.getClass().getEnclosingMethod().getName();
		extent = extentreport.createTest(name);
		Random r = new Random();
		int amount = r.nextInt(100,1000);
        formname = "Form Created by Automation_" + amount;
        System.out.println("Form name is " +formname);
        String AccessToken = BearerToken.generateToken();
		byte[] b = Files.readAllBytes(Paths.get("Models","AddAuditform.json"));
		
		
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
	      
	     formid= response.asString();
	      
	     
	    
	      System.out.println("Form generated with id as: " + formid);  
	      System.out.println("Form name is: " + formname);
	      extent.log(Status.INFO, "Form generated with name as: " + formname);
	      extent.log(Status.INFO, "Form generated with id as: " + formid);
	      
	}
	
	@Test(priority=3)
	public static void addformquestion()throws IOException
	{
		String name = new Object(){}.getClass().getEnclosingMethod().getName();
		extent = extentreport.createTest(name);
		
		String AccessToken = BearerToken.generateToken();
		byte[] b = Files.readAllBytes(Paths.get("Models","AuditFormSaveQuestions.json"));
		
		
	      String bdy = new String(b);
	     JSONObject jobjectbdy= new JSONObject(bdy);
	   jobjectbdy.put("formId", formid);
		 jobjectbdy.put("details.formId", formid);
	      JSONArray jarray = new JSONArray();
	      jarray.put(jobjectbdy);
	
	      
		 String strbdy = jarray.toString();

	     
	
				
	      
	      Response response = RestAssured.given().contentType(JSON).
	    		  header("Authorization", "Bearer " +AccessToken).body(strbdy)
	    		  .when().post("https://opsapi.workpulse.com/api/AuditForm/saveQuestions")
	    		  .then()
	    		  .statusCode(200)
	    		  .log().all().extract().response();
	      
	      extent.log(Status.INFO, "Question added successfully to Audit with name as " +formname);
	      
	}
	
	@Test(priority=4)
	public static void assignlocation()throws IOException
	{
		String name = new Object(){}.getClass().getEnclosingMethod().getName();
		extent = extentreport.createTest(name);
		
		String AccessToken = BearerToken.generateToken();
		byte[] b = Files.readAllBytes(Paths.get("Models","AssignLocations.json"));
		
		
	      String bdy = new String(b);
	     JSONObject jobjectbdy= new JSONObject(bdy);
	      JSONArray jarray = new JSONArray();
	      jarray.put(jobjectbdy);
	
	      
		 String strbdy = jarray.toString();

	     
	
				
	      
	      Response response = RestAssured.given().contentType(JSON).
	    		  header("Authorization", "Bearer " +AccessToken).body(strbdy).pathParam("formid", formid)
	    		  .when().post("https://opsapi.workpulse.com/api/AuditForm/{formid}/locations")
	    		  .then()
	    		  .statusCode(200)
	    		  .log().all().extract().response();
	      extent.log(Status.INFO, "Location assigned successfully to Audit with name as" +formname);
	    
	      
	}
	
	@Test(priority=5)
	public static void auditformsubmission()throws IOException
	{
		String name = new Object(){}.getClass().getEnclosingMethod().getName();
		extent = extentreport.createTest(name);
		
		String AccessToken = BearerToken.generateToken();
		byte[] b = Files.readAllBytes(Paths.get("Models","AuditFormSubmission.json"));
		
		
	      String bdy = new String(b);
	     JSONObject jobjectbdy= new JSONObject(bdy);
	     jobjectbdy.put("FormId", formid);
	  
	    
	    Date d1 = new Date();
	    
	    SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String currentdate = dateformat.format(d1);
	    Calendar c = Calendar.getInstance();
	    long timeinms= c.getTimeInMillis();
	    Date afteraddingmins = new Date(timeinms- (5*60*1000));
	    String starttime = dateformat.format(afteraddingmins);
	   
	    
	     
	 
	     jobjectbdy.put("AuditStartTime", starttime);
	     jobjectbdy.put("AuditEndTime", currentdate);
	
	    
		 String strbdy = jobjectbdy.toString();

	     
	
				
	      
	      Response response = RestAssured.given().contentType(JSON).
	    		  header("Authorization", "Bearer " +AccessToken).body(strbdy)
	    		  .when().post("https://opsapi.workpulse.com/api/audit/auditForm")
	    		  .then()
	    		  .statusCode(200)
	    		  .log().all().extract().response();
	      extent.log(Status.INFO, "Audit Form " +formname+" Submitted successfully");
	    
	      
	}
	
	@Test(priority=6)
	public static void markinactiveauditform()throws IOException
	{
		String name = new Object(){}.getClass().getEnclosingMethod().getName();
		extent = extentreport.createTest(name);
		
		String AccessToken = BearerToken.generateToken();
		byte[] b = Files.readAllBytes(Paths.get("Models","MarkInactiveAuditForm.json"));
		
		
	      String bdy = new String(b);
	     JSONObject json= new JSONObject(bdy);
	     json.put("categoryDetails.id", formid);
	     json.put("categoryQuestionDetails.id", formid);
	     json.put("categoryQuestionDetails.details.id", formid);
	     json.put("id", formid);
	     json.put("formName", formname);
	     json.put("filtereddetails.formId", formid);
	     json.put("filtereddetails.details.formId", formid);
	
	
	    
		 String strbdy = json.toString();

	     
	
				
	      
	      Response response = RestAssured.given().contentType(JSON).
	    		  header("Authorization", "Bearer " +AccessToken).body(strbdy)
	    		  .when().post("https://opsapi.workpulse.com/api/AuditForm/form")
	    		  .then()
	    		  .statusCode(200)
	    		  .log().all().extract().response();
	      extent.log(Status.INFO, "Audit Form " +formname+ " Inactivated successfully");
	    
	      
	}
	
	}

