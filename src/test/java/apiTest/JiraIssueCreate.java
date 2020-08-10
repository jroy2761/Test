package apiTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import static io.restassured.RestAssured.given;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import selenium_framework.AUTBaseTest;
import selenium_framework.ReusableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class JiraIssueCreate extends AUTBaseTest{
	Properties prop=new Properties();
	String key;
    String Jirahost;
	@BeforeTest
	public void getData() throws IOException
	{

		FileInputStream fis=new FileInputStream(".\\resource.properties");
		prop.load(fis);
		Jirahost = (String) prop.get("JIRAHOST");

	}
	@Test(priority=0)
	public void JiraAPICreateIssue()
	{
		//Creating Issue/Defect
		RestAssured.baseURI= Jirahost;
		Response res=given().header("Content-Type", "application/json").
				header("Cookie","JSESSIONID="+ReusableMethods.getSessionKEY()).
				body(PayLoad.jiraIssueCreate()).when().
				post(Resources.JiraCreateIssuePostData()).then().statusCode(201).extract().response();

		JsonPath js= ReusableMethods.rawToJson(res);
		key=js.get("key");

	}
	@Test(priority=1)
	public void JiraAPIAttachScreenshot(String ScreenshotName)
	{
		//Capture screenshot and save
		// Raise a bug in Jira
		File src= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try {
			// now copy the  screenshot to desired location using copyFile //method
			FileUtils.copyFile(src, new File("Reports/JiraScreenshot/"+ ScreenshotName +".png"));
		}
		catch (IOException e)
		{
			System.out.println(e.getMessage());
		}

		RestAssured.baseURI= Jirahost;
		Response res=given().header("Content-Type", "multipart/form-data").
				header("X-Atlassian-Token","no-check").
				header("Authorization","Basic cHdjOlB3Y25ldEAxMjM0NQ=="). // Created from postman providing Auth Jira ID and password
				multiPart(new File("Reports/JiraScreenshot/"+ ScreenshotName +".png")).
				when().
				post("/rest/api/2/issue/"+key+"/attachments").then().statusCode(200).extract().response();
		JsonPath js= ReusableMethods.rawToJson(res);
		System.out.println(js.toString());
	}
}

