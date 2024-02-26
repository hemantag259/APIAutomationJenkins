package baseclass;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReport  {

	public static ExtentReports extentreport;
	public static ExtentTest extent;
	public static ExtentSparkReporter reporter;
	
	@BeforeSuite
	public static void reportGenerate()
	{
		String path = System.getProperty("user.dir")+"\\Reports\\index.html"; 
		extentreport = new ExtentReports();
		reporter = new ExtentSparkReporter(path); 
		extentreport.attachReporter(reporter);
        reporter.config().setDocumentTitle("AutomationTesting.in Demo Report");
        reporter.config().setReportName("My Own Report");
        reporter.config().setTheme(Theme.DARK);
	}
	
	@AfterMethod
    public void getResult(ITestResult result)
    {
        if(result.getStatus() == ITestResult.FAILURE)
        {
            extent.log(Status.FAIL, MarkupHelper.createLabel(result.getName()+" Test case FAILED due to below issues:", ExtentColor.RED));
            extent.fail(result.getThrowable());
        }
        else if(result.getStatus() == ITestResult.SUCCESS)
        {
            extent.log(Status.PASS, MarkupHelper.createLabel(result.getName()+" Test Case PASSED", ExtentColor.GREEN));
        }
        else
        {
            extent.log(Status.SKIP, MarkupHelper.createLabel(result.getName()+" Test Case SKIPPED", ExtentColor.ORANGE));
            extent.skip(result.getThrowable());
        }
    }
	@AfterSuite
	public static void reportflush()
	{
		extentreport.flush();
	}
}
