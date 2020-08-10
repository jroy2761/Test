package selenium_framework;


import java.awt.AWTException;
import java.awt.Robot;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.JavascriptExecutor;

import selenium_framework.Utils;

import javax.ejb.EnterpriseBean;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.FileUtils;
import org.apache.http.client.ClientProtocolException;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.openqa.selenium.winium.DesktopOptions;
import org.openqa.selenium.winium.WiniumDriver;
import org.openqa.selenium.winium.WiniumDriverService;

import selenium_framework.CaptureScreen;
import selenium_framework.CaptureScreen.ScreenshotOf;
import selenium_framework.LogAs;
import selenium_framework.Reports;

import com.gargoylesoftware.htmlunit.BrowserVersion;

public class WinBaseTest extends CommonUtils

{
	public static WiniumDriver  windriver;
	
	public void ReportsConfig()
	{
	      Reports.setAuthorInfo(Directory.authorName, Utils.getCurrentTime(),"1.0");
	}
	
	public WiniumDriver startDriver(String winAppPath)
	{
		String currDir = Directory.CURRENTDir;
		String driverPath = currDir + Directory.winiumPath;
	    try
	    {
	    	DesktopOptions options = new DesktopOptions(); 
	    	options.setApplicationPath(winAppPath);
	    	File drivePath = new File(driverPath);
	    	WiniumDriverService service = new WiniumDriverService.Builder()
	    								.usingDriverExecutable(drivePath)
	    								.usingPort(9999)
	    								.withVerbose(true)
	    								.withSilent(false)
	    								.buildDesktopService();
	    	windriver = new WiniumDriver(service, options);
	    	
	    	 Reports.add("Pass","Winium driver should initialize","Winium driver initialized", 
	 					LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
	    }
	    catch(Exception e)
	    {
	    	Reports.add("Fail","Winium driver should initialize",e.getMessage(), 
 					LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
	    }
	    return windriver;
	}
	
	public WebElement getWindowsElement(String locator, String by)
	{
		WebElement webelement = null;
		try
		{
			switch (by) 
			{
				case "id": webelement = windriver.findElement(By.id(locator));
					//Reports.add("Pass","Driver should identify locator "+locator+" using "+by,"Webelement "+locator+" identified using "+by, 
							//LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				break;
				case "name": webelement =  windriver.findElement(By.name(locator));
					//Reports.add("Pass","Driver should identify locator "+locator+" using "+by,"Webelement "+locator+" identified using "+by, 
							//LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				break;
				case "xpath": webelement= windriver.findElement(By.xpath(locator));
					//Reports.add("Pass","Driver should identify locator "+locator+" using "+by,"Webelement "+locator+" identified using "+by, 
							//LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));

				break;
				case "linkText": webelement= windriver.findElement(By.linkText(locator));     
					//Reports.add("Pass","Driver should identify locator "+locator+" using "+by,"Webelement "+locator+" identified using "+by, 
							//LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				break;
				case "cssSelector": webelement= windriver.findElement(By.cssSelector(locator));     
					//Reports.add("Pass","Driver should identify locator "+locator+" using "+by,"Webelement "+locator+" identified using "+by, 
							//LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				break;
				case "className": webelement= windriver.findElement(By.className(locator));     
					//Reports.add("Pass","Driver should identify locator "+locator+" using "+by,"Webelement "+locator+" identified using "+by, 
							//LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));	
				break;
				case "partialLinkText": webelement= windriver.findElement(By.partialLinkText(locator));     
					//Reports.add("Pass","Driver should identify locator "+locator+" using "+by,"Webelement "+locator+" identified using "+by, 
							//LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				break;
				case "tagname": webelement= windriver.findElement(By.tagName(locator));     
					//Reports.add("Pass","Driver should identify locator "+locator+" using "+by,"Webelement "+locator+" identified using "+by, 
							//LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				break;
				default: Reports.add("Fail","Winium Driver should identify locator "+locator+" using "+by,
					"Unable to identify webelement "+locator+" using "+by,LogAs.FAILED, 
					new CaptureScreen(ScreenshotOf.DESKTOP));
				break;
			}
		}
		catch(Exception e)
		{
			Reports.add("Fail","Winium Driver should identify locator "+locator+" using "+by,e.getMessage(),
					LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
		}
		return webelement;
	 }
	
	public static List<WebElement> getWindowsElements(String locator, String by)
	{
		List<WebElement> webelement = null;
		try
		{
			switch (by) 
			{
				case "id": webelement = windriver.findElements(By.id(locator));
					//Reports.add("Pass","Driver should identify locator "+locator+" using "+by,"Webelement "+locator+" identified using "+by, 
							//LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				break;
				case "name": webelement =  windriver.findElements(By.name(locator));
					//Reports.add("Pass","Driver should identify locator "+locator+" using "+by,"Webelement "+locator+" identified using "+by, 
							//LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				break;
				case "xpath": webelement= windriver.findElements(By.xpath(locator));
					//Reports.add("Pass","Driver should identify locator "+locator+" using "+by,"Webelement "+locator+" identified using "+by, 
							//LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));

				break;
				case "linkText": webelement= windriver.findElements(By.linkText(locator));     
					//Reports.add("Pass","Driver should identify locator "+locator+" using "+by,"Webelement "+locator+" identified using "+by, 
							//LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				break;
				case "cssSelector": webelement= windriver.findElements(By.cssSelector(locator));     
					//Reports.add("Pass","Driver should identify locator "+locator+" using "+by,"Webelement "+locator+" identified using "+by, 
							//LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				break;
				case "className": webelement= windriver.findElements(By.className(locator));     
					//Reports.add("Pass","Driver should identify locator "+locator+" using "+by,"Webelement "+locator+" identified using "+by, 
							//LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));	
				break;
				case "partialLinkText": webelement= windriver.findElements(By.partialLinkText(locator));     
					//Reports.add("Pass","Driver should identify locator "+locator+" using "+by,"Webelement "+locator+" identified using "+by, 
							//LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				break;
				case "tagname": webelement= windriver.findElements(By.tagName(locator));     
					//Reports.add("Pass","Driver should identify locator "+locator+" using "+by,"Webelement "+locator+" identified using "+by, 
							//LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				break;
				default: Reports.add("Fail","Winum Driver should identify locator "+locator+" using "+by,
					"Unable to identify webelements "+locator+" using "+by,LogAs.FAILED, 
					new CaptureScreen(ScreenshotOf.DESKTOP));
				break;
			}
		}
		catch(Exception e)
		{
			Reports.add("Fail","Winium Driver should identify locator "+locator+" using "+by,e.getMessage(),
					LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
		}
		return webelement;
	 }
	 
	public WebElement getNestedWindowsElement(String locator,String by,String nestedlocator, String nestedby)
	{
		WebElement webelement = getWindowsElement(locator, by);
		WebElement nestedWebelement = null;
		try
		{
			switch (nestedby) 
			{
				case "id": nestedWebelement = webelement.findElement(By.id(locator));
					//Reports.add("Pass","Driver should identify locator "+locator+" using "+by,"Webelement "+locator+" identified using "+by, 
							//LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				break;
				case "name": nestedWebelement =  webelement.findElement(By.name(locator));
					//Reports.add("Pass","Driver should identify locator "+locator+" using "+by,"Webelement "+locator+" identified using "+by, 
							//LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				break;
				case "xpath": nestedWebelement= webelement.findElement(By.xpath(locator));
					//Reports.add("Pass","Driver should identify locator "+locator+" using "+by,"Webelement "+locator+" identified using "+by, 
							//LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));

				break;
				case "linkText": nestedWebelement= webelement.findElement(By.linkText(locator));     
					//Reports.add("Pass","Driver should identify locator "+locator+" using "+by,"Webelement "+locator+" identified using "+by, 
							//LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				break;
				case "cssSelector": nestedWebelement= webelement.findElement(By.cssSelector(locator));     
					//Reports.add("Pass","Driver should identify locator "+locator+" using "+by,"Webelement "+locator+" identified using "+by, 
							//LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				break;
				case "className": nestedWebelement= webelement.findElement(By.className(locator));     
					//Reports.add("Pass","Driver should identify locator "+locator+" using "+by,"Webelement "+locator+" identified using "+by, 
							//LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));	
				break;
				case "partialLinkText": nestedWebelement= webelement.findElement(By.partialLinkText(locator));     
					//Reports.add("Pass","Driver should identify locator "+locator+" using "+by,"Webelement "+locator+" identified using "+by, 
							//LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				break;
				case "tagname": nestedWebelement= webelement.findElement(By.tagName(locator));     
					//Reports.add("Pass","Driver should identify locator "+locator+" using "+by,"Webelement "+locator+" identified using "+by, 
							//LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				break;
				default: Reports.add("Fail","Winium Driver should identify nested locator "+nestedlocator+" using "+nestedby,
					"Unable to identify nested webelement "+nestedlocator+" using "+nestedby,LogAs.FAILED, 
					new CaptureScreen(ScreenshotOf.DESKTOP));
				break;
			}
		}
		catch(Exception e)
		{
			Reports.add("Fail","Winium Driver should identify nested locator "+nestedlocator+" using "+nestedby,e.getMessage(),
					LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
		}
		return nestedWebelement;
	 }
	
	
	 public String getWindowsElementPropertyValue (String locator, String by)
	 {
		  String attrValue = null;
		  try
		  {
				  if (isWindowsElementDisplayed(locator,by))
				  {
					  WebElement wbElement = getWindowsElement(locator,by);
					  attrValue = wbElement.getAttribute("value");
					  /*Reports.add("Pass","Webdriver should get property value of webelement "+locator+" using"+by ,
						  "Property value of webelement "+locator+" using"+by+" is "+attrValue, 
						  	LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));*/
				  }
		  }
		  catch(Exception e)
		  {
			  Reports.add("Fail","Winium driver should get property value of webelement "+locator+" using"+by ,e.getMessage(), 
					  LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
		  }
		  return attrValue;
	  }
	 
	  public String getWindowsDisplayedText (String locator, String by)
	  {
		  String attrValue = null;
		  try
		  {
			  if (isWindowsElementPresent(locator,by))
			  {
				  WebElement wbElement = getWindowsElement(locator,by);
				  attrValue = wbElement.getText();
					  /*Reports.add("Pass","Display text of webelement "+locator+" using "+by+" should get captured",
						  "Displayed text of webelement "+locator+" using "+by+" is "+attrValue, 
					  LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));*/
			  }
		  }
		  catch(Exception e)
		  {
			  Reports.add("Fail","Display text of webelement "+locator+" using "+by+" should get captured",e.getMessage(), 
					  LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
		  }
		  return attrValue;
	  }
	  
	  public String getWindowsSelectedListBoxText (String locator, String by)
	  {
		  String attrValue = null;
		  try
		  {
			  if (isWindowsElementPresent(locator,by))
			  {
				  Select select = (Select) getWindowsElement(locator,by);
				  attrValue = select.getFirstSelectedOption().getText();
					  /*Reports.add("Pass","Fetch selected value of listbox "+locator+" using "+by,
						  "Selected Value of listbox "+locator+" using "+by+" is "+attrValue,
						  LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));*/
			  }
		  }
		  catch(Exception e)
		  {
			  Reports.add("Fail","Fetch selected value of listbox "+locator+" using "+by,e.getMessage(), 
					  LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
		  }
		  return attrValue;
	  }
	  
	  public List<WebElement> getWindowsSelectedListBoxTextArray (String locator, String by)
	  {
		  List<WebElement> attrValue = null;
		  try
		  {
			  if (isWindowsElementPresent(locator,by))
			  {
				  Select select = (Select) getWindowsElement(locator,by);
				  attrValue = select.getOptions();
				  /*Reports.add("Pass","Content of listbox "+locator+" using "+by+" should store is a list",
						  "Content of listbox "+locator+" using "+by+" are stored in "+attrValue, 
					  LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));*/ 
			  }
		  }
		  catch(Exception e)
		  {
			  Reports.add("Fail","Content of listbox "+locator+" using "+by+" should store is a list",e.getMessage(), 
					  LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
		  }
		  return attrValue;
	  }
	  
	  public String getWindowsElementText(String locator, String by,String elementType)
	  {
		  String attrValue = null;
		  try
		  {
			  switch(elementType)
			  {
		  			case "textbox" : attrValue = getWindowsElementPropertyValue (locator,by);
		  			//Reports.add("Pass","Text of "+elementType+" "+locator+" should store in a variable using "+by,
		  					//"Text of "+elementType+" "+locator+" is "+attrValue, 
		  					//LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		  			break;
		  			case "radiobutton" : attrValue = getWindowsElementPropertyValue(locator,by);
		  			//Reports.add("Pass","Text of "+elementType+" "+locator+" should store in a variable using "+by,
		  					//"Text of "+elementType+" "+locator+" is "+attrValue,  
		  					//LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		  			break;
		  			case "link" :	attrValue = getWindowsDisplayedText(locator,by);
		  			//Reports.add("Pass","Text of "+elementType+" "+locator+" should store in a variable using "+by,
		  					//"Text of "+elementType+" "+locator+" is "+attrValue,  
		  					//LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		  			break;
		  			case "checkbox" : attrValue = getWindowsElementPropertyValue(locator,by);
		  			//Reports.add("Pass","Text of "+elementType+" "+locator+" should store in a variable using "+by,
		  					//"Text of "+elementType+" "+locator+" is "+attrValue,  
		  					//LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		  			break;
		  			case "button" : attrValue = getWindowsDisplayedText(locator,by);
		  			//Reports.add("Pass","Text of "+elementType+" "+locator+" should store in a variable using "+by,
		  					//"Text of "+elementType+" "+locator+" is "+attrValue,  
		  					//LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		  			break;
		  			case "listbox" : attrValue = getWindowsSelectedListBoxText(locator,by);
		  			//Reports.add("Pass","Text of "+elementType+" "+locator+" should store in a variable using "+by,
		  					//"Text of "+elementType+" "+locator+" is "+attrValue,  
		  					//LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		  			break;
		  			default: Reports.add("Fail","Text of "+elementType+" "+locator+" should store in a variable using "+by,
		  					"Unable to capture text of "+elementType+" "+locator+" using "+by 
		  					,LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
		  			break;
			  }
		  }
		  catch(Exception e)
		  {
			  Reports.add("Fail","Text of "+elementType+" "+locator+" should store in a variable using "+by,
	  					e.getMessage(),LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
		  }
		  return attrValue;
		  
	  }
	  
	  
	  public void waitForDynamicWindowsElement(String locator, String by,String elementType,int maxWaitVal,String expectedVal) 
			  throws StaleElementReferenceException,NullPointerException,InterruptedException
	  {
		  int i = 0;
		  try 
		  {
			  for( i =1000 ;i<maxWaitVal ; i=i+2000)
				{
					Thread.sleep(i);
					getWindowsElementText (locator, by,elementType).startsWith(expectedVal);
					Thread.sleep(2000);
					break;
				}
			  Reports.add("Pass","Webelement "+locator+" with expected value "+expectedVal+" should get loaded within "+maxWaitVal+" msec",
							"Webelement "+locator+" with expected value "+expectedVal+" is loaded in"+i+" sec", 
							LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
		  }
		  catch(Exception e)
		  {
			  Reports.add("Fail","Webelement "+locator+" with expected value "+expectedVal+" should get loaded within "+maxWaitVal+" msec",
					  e.getMessage(),LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
		  }	  
	  }
	  
	  public void assertTrueWindows (boolean condition) throws AssertionError
	  {
		  try
		  {
			  if(condition == true)
			  {
				  Reports.add("Pass","Condition should be a valid one","Condition is true", 
					  LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
			  }
			  else
			  {
				  Reports.add("Fail","Condition should be a valid one","Condition is false", 
						  LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			  }
		  }
		  catch(Exception e)
		  {
			  e.printStackTrace();
			  Reports.add("Fail"," should be a valid one",e.getMessage(), 
					  LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
		  }
	  }
	  
	  public void assertFalseWindows (boolean condition) throws AssertionError
	  {
		  try
		  {
			  if(!condition)
			  {
			      Reports.add("Pass","Condition should be a false one","Condition is false", 
					   LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
			  }
			  else
			  {
				  Reports.add("Fail","Condition should be a false one","Condition is true", 
						  LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			  }
		  }
		  catch(Exception e)
		  {
			  e.printStackTrace();
			  Reports.add("Fail","Condition should be a false one",e.getMessage(), 
					  LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
		  }
	  }
	  
	  public void assertEqualsWindows (String strExp, String strAct) throws AssertionError
	  {
		  try
		  {
			 if(strExp.equalsIgnoreCase(strAct))
			 {
				 Reports.add("Pass","Strings "+strExp+","+strAct+" should matched","Strings "+strExp+","+strAct+" are matching", 
					  LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
			 }
			 else
			 {
				 Reports.add("Fail","Strings "+strExp+","+strAct+" should matched","Strings are not matching",
						  LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			 }
		  }
		  catch(Exception e)
		  {
			  e.printStackTrace();
			  Reports.add("Fail","Strings "+strExp+","+strAct+" should matched",e.getMessage(),
					  LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
		  }
	  }
	  
	  public void verifyWindowsTitle (String strExp) throws AssertionError
	  {
		  try
		  {
			  String strAct = windriver.getTitle();
			  if(strExp.equalsIgnoreCase(strAct))
				 {
					 Reports.add("Pass",strExp+","+strAct,"Strings should matched","Strings are matching", 
						  LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
				 }
				 else
				 {
					 Reports.add("Fail",strExp+","+strAct,"Strings should matched","Strings are not matching",
							  LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
				 }
		  }
		  catch(Exception e)
		  {
			  e.printStackTrace();
			  Reports.add("Fail","Strings should matched",e.getMessage(),LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
		  }
	  }
	  
	  public boolean verifyWindowsSuccessMessageBoolean (String strExp, String strAct) throws AssertionError
	  {
		  boolean condition =false;
		  try
		  {
			  if(strExp.equalsIgnoreCase(strAct))
				 {
				  	 condition = true;
					 Reports.add("Pass","Strings "+strExp+","+strAct+" should matched","Strings "+strExp+","+strAct+"are matching", 
						  LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
				 }
			  else
			  {
				  condition = false;
				  Reports.add("Fail","Strings "+strExp+","+strAct+" should matched","Strings are not matching",
						  LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			  }
		  }
		  catch(Exception e)
		  {
			  condition = false;
			  Reports.add("Fail","Strings "+strExp+","+strAct+" should matched",e.getMessage(),
					  LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
		  }
		  return condition;
	  }
	  
	  public void verifyWindowsAlertMessageDisplayed(String strExp )
	  {
		  Alert alertWindow = null;
		  String strAct = null;
		  try
		  {
			  if (ExpectedConditions.alertIsPresent() != null)
			  {
				  alertWindow = windriver.switchTo().alert();
				  strAct = alertWindow.getText();
			  
				  if(verifyWindowsSuccessMessageBoolean(strExp, strAct))
				  {
					  alertWindow.accept();
					  Reports.add("Pass","Strings should matched","Strings are matching", 
						  LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
				  }
				  else
				  {
					  alertWindow.dismiss();
					  Reports.add("Pass","Strings should matched","Strings are not matching", 
							  LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
				  }
			  }
		  }
		  catch(Exception e)
		  {
			  Reports.add("Fail","Strings "+strExp+","+strAct+" should matched",e.getMessage(),
					  LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
		  }
	  }
	  
	  public void enterTextWindowsPopUp(String locator,String by,String value )
	  {
		  Alert alertWindow = null;
		  String strAct = null;
		  try
		  {
			  if (ExpectedConditions.alertIsPresent() != null)
			  {
				  alertWindow = windriver.switchTo().alert();
				  
				  if(isWindowsElementDisplayed(locator, by))
				  {
					  setWindowsTextboxValue(locator, by, value);						  
					  Reports.add("Pass","String should enter in popUp","String entered in popup", 
						  LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
				  }
			  }
		  }
		  catch(Exception e)
		  {
			  Reports.add("Fail","String should enter in popUp",e.getMessage(),
					  LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
		  }
	  }
	  
	  public void verifyWindowsMessageDisplayed(String locator,String by,String strExp,String locatorType) 
	  {
		  	 String strAct = null;
			 try
			 {
				 strAct = getWindowsElementText(locator,by,locatorType);
				 if(verifyWindowsSuccessMessageBoolean(strExp, strAct)==true)
				 {
					 //System.out.println("message verified");
					 Reports.add("Pass","Stings "+strExp+","+strAct+" should matched","Strings "+strExp+","+strAct+" are matching",
							 LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
				 }
			 }
			 catch(Exception e)
			 {
				 Reports.add("Fail","Stings "+strExp+","+strAct+" should matched",e.getMessage(),
						 LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			 }
	  }
	  
	  public boolean isWindowsElementPresent(String locator,String by) 
	  {
		  boolean condition = false;
		  try 
		  {
				  WebElement WbElement=  getWindowsElement(locator,by);				  
				  if (WbElement.isDisplayed())
				  {
					  if(WbElement.isEnabled());
					  {
						  condition = true;
					  //Reports.add("Pass","Webelement "+locator+" should present","Webelement "+locator+" is present", 
							  //LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
					  }
				  }
		  }
		  catch (Exception e)
		  {
			  
			  Reports.add("Fail","Webelement "+locator+" should present",e.getMessage(), 
					  LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
		  }
		  return condition;
	  }
	  
	  public boolean isWindowsElementDisplayed(String locator,String by) 
	  {
		  boolean condition = false;
		  try 
		  {
				  WebElement WbElement=  getWindowsElement(locator,by);
				  if(WbElement.isDisplayed())
				  {
					  condition = true;
					  //Reports.add("Pass","Webelement "+locator+" should display","Webelement "+locator+" is displaying",
						  //LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				  }
		  }
		  catch (Exception e)
		  {
			  e.printStackTrace();
			  Reports.add("Fail","Webelement "+locator+" should display",e.getMessage(), 
					  LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
		  }
		  return condition;
	  }
	  
	  public void setWindowsTextboxValue(String locator,String by,String value)
	  {
		  try
		  {
			  if (isWindowsElementPresent(locator,by))
			  {
				  WebElement wbElement = getWindowsElement(locator,by);
				  wbElement.sendKeys(value);
				  Reports.add("Pass","Text "+value+" should enter in the textbox "+locator,"Text "+value+" has been entered in text box "+locator, 
		  			LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
			  }
		  }
		  catch(Exception e)
		  {
			  Reports.add("Fail","Text "+value+" should enter in the textbox "+locator,e.getMessage(), 
					  LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
		  }
	  }
	  
	  public void clickWindowsRadioButton(String locator,String by,String attachedText)
	  {
		  String attrVal = null;
		  try
		  {
			  if (isWindowsElementPresent(locator,by))
			  {
				  WebElement wbElement = getWindowsElement(locator,by);
				  attrVal = getWindowsElementPropertyValue(locator,by);
		  	      if  (attrVal.equals(attachedText))
		  	      {
					  if(verifyRadioButtonIsSelected(locator,by,attachedText) == false)
					  {
						    wbElement.click();
		  			  		Reports.add("Pass","Radiobutton "+locator+"with value "+attachedText+" should get selected",
		  					"Radiobutton "+locator+" is selected",
		  					LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
					  }
				  }
			  }
		  }
		  catch(Exception e)
		  {
			  Reports.add("Fail","Radiobutton "+locator+"with value "+attachedText+"  should get selected",
				  e.getMessage(),LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
		  }
	  }
	  
	  public void clickWindowsWebLink(String locator,String by) throws MalformedURLException, IOException
	  {
		  try
		  {
			  if (isWindowsElementPresent(locator,by))
			  {
				  WebElement wbElement = getWindowsElement(locator,by);
				  wbElement.click();
				  Reports.add("Pass","WebLink "+locator+" should get clicked using "+by,"WebLink "+locator+" is clicked using "+by, 
				  LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
			  }
		  }
		  catch(Exception e)
		  {
			  Reports.add("Fail","WebLink "+locator+" should get clicked using"+by,e.getMessage(), 
					LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
		  }
	  }
	  
	  public static void moveToWindowsElement(WebElement element)
	  {
	        try
	        {
			Actions actions = new Actions(windriver);
			actions.moveToElement(element);
			actions.perform();
	        }
	        catch(Exception e)
	        {
	        	Reports.add("Fail","Control should move to element"+element,
						  e.getMessage(),LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
	        }
	  }
	  
	  public static void scrollToWindowsElement(WebElement element)
	  {
			try
			{
				((JavascriptExecutor)windriver).executeScript("arguments[0].scrollIntoView();", element);
			}
			catch(Exception e)
			{
				Reports.add("Fail","Control should move to element"+element,
						  e.getMessage(),LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			}
	  }
	  
	  public boolean verifyWindowsCheckBoxIsChecked(String locator,String by)
	  {
		  String elementText = null;
		  boolean condition = false;
		  try
		  {
			  if (isWindowsElementPresent(locator,by))
			  {
				  WebElement wbElement = getWindowsElement(locator,by);
				  elementText = getWindowsElementPropertyValue(locator,by);
					  if(wbElement.isSelected())
					  {
						  condition = true;
						  //Reports.add("Pass","Check box "+locator+" with value "+attachedText+" should get checked using "+by,
								  //"Check box "+locator+" with value "+attachedText+" has been checked using "+by, 
								  //LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
					  }
				  }			  
		  }
		  catch(Exception e)
		  {
			  Reports.add("Fail","Check box "+locator+" should get checked using "+by,
					  e.getMessage(),LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
		  }
		  return condition;
	}
	  
	  public void selectCheckBox(String locator,String by)
	  {
		  String attrVal = null; 
		  	try
		  	{
		 	  if (isWindowsElementPresent(locator,by))
			  {
				  WebElement wbElement = getWindowsElement(locator,by);
				  attrVal = getWindowsElementPropertyValue(locator,by);		  	  
		  			  if(verifyWindowsCheckBoxIsChecked(locator,by) == false)
		  			  {
		  				  wbElement.click();
		  				  Reports.add("Pass","Check box "+locator+" should get checked using "+by,
		  						  "Check box "+locator+"  has been checked using "+by, 
		  						  LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
		  			  }
		  			  else
		  			  {
		  				  Reports.add("Info","Check box "+locator+" should get checked using "+by,
		  						  "Check box "+locator+" is already checked", 
		  						  LogAs.INFO, new CaptureScreen(ScreenshotOf.DESKTOP));
		  			  }
		  		  }
		  	  }
		  	catch(Exception e)
		  	{
			  	  Reports.add("Fail","Check box "+locator+" should get checked using "+by,
			  			  e.getMessage(),LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
		  	}
		}
	  
	  public boolean verifyWindowsRadioButtonIsSelected(String locator,String by)
	  {
		  String elementText = null;
		  boolean condition = false ;
		  try
		  {
			  if (isWindowsElementPresent(locator,by))
			  {
				  WebElement wbElement = getWindowsElement(locator,by);
				  elementText = getWindowsElementPropertyValue(locator,by);
					  if(wbElement.isSelected())
					  {
						  condition = true;
						  //Reports.add("Pass","Radion button "+locator+" with value "+attachedText+" should get selected using "+by,
								  //"Radion button "+locator+" with value "+attachedText+" is selected using "+by, 
		  					  //LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
					  }
				  }						  
		  }
		  catch(Exception e)
		  {
			  Reports.add("Fail","Radion button "+locator+" should get selected using "+by,e.getMessage(), 
	  				  LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
		  }  
		  return condition;
	  }
	  
	  public void clicWindowskRadioButton(String locator,String by)
	  {
		  String attrVal = null;
		  try
		  {
			  if (isWindowsElementPresent(locator,by))
			  {
				  WebElement wbElement = getWindowsElement(locator,by);
				  attrVal = getWindowsElementPropertyValue(locator,by);
				  if(verifyWindowsRadioButtonIsSelected(locator,by) == false)
					  {
						    wbElement.click();
		  			  		Reports.add("Pass","Radiobutton "+locator+" should get selected",
		  					"Radiobutton "+locator+" is selected",
		  					LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
					  }
					  else
					  {
						  Reports.add("Info","Radiobutton "+locator+"  should get selected",
		  					"Radiobutton "+locator+" is already selected",
		  					LogAs.INFO, new CaptureScreen(ScreenshotOf.DESKTOP));
					  }
				  }
			  }
		  catch(Exception e)
		  {
			  Reports.add("Fail","Radiobutton "+locator+"  should get selected",
				  e.getMessage(),LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
		  }
	  }
	  
	  public void windownPopUpClickViaWindowHandle(String locator,String by)
	  {
		  try
		  {     
			  	String prntWindow = windriver.getWindowHandle();
			  	clickWindowsButton(locator, by);
			  	Set subWindows = windriver.getWindowHandles();
			  	Iterator itr = subWindows.iterator();
			  	while(itr.hasNext())
			  	{
			  		String popUpHandle = itr.next().toString();
			  		if(!popUpHandle.contains(prntWindow))
			  		{
			  			windriver.switchTo().window(popUpHandle).close();
			  		}
			  	}
			  	windriver.switchTo().window(prntWindow);
			  	Reports.add("Pass","Popup windows should get accepted/dismissed and return to main window",
						"Popups are closed and control returns to main wondow",LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
		  }
		  catch(Exception e)
		  {
			  Reports.add("Fail","Popup windows should get accepted/dismissed and return to main window",
						e.getMessage(),LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
		  }
	  }
	  
	  public void windowsPopUpClick(String choice,String attachedText)
	  {
		  try
		  {     
			  	Alert a = windriver.switchTo().alert();
				if(a.getText().contains(attachedText))
				{
					if (choice.equalsIgnoreCase("accept"))
					{
						a.accept();
						Reports.add("Pass","Alert should get accepted",
								"Alert is accepted",LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
					}
					if(choice.equalsIgnoreCase("dismiss"))
					{
						a.dismiss();
						Reports.add("Pass","Alert should get dismissed",
								"Alert is dismissed",LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
					}
				}
		  }
		  catch(Exception e)
		  {
			  Reports.add("Fail","Alert should get accepted/dismissed",
						e.getMessage(),LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
		  }
	  }
	  
	  public void dynamicWaitForWindowsElement(String locator, String by,int maxWaitVal) 
			  throws StaleElementReferenceException,NullPointerException,InterruptedException
	  {
		  int i = 0;
		  boolean flag = false;
		  try 
		  {
			  	for(i =1000 ;i<maxWaitVal ; i=i+2000)
				{
					Thread.sleep(i);
					if(isWindowsElementDisplayed(locator, by)||isWindowsElementPresent(locator, by))
					{
						Reports.add("Pass","Webelement "+locator+" should get loaded in "+maxWaitVal+" msec",
								"Webelement "+locator+" is loaded after "+i+" msec",LogAs.PASSED, 
								new CaptureScreen(ScreenshotOf.DESKTOP));
						flag = true;
						break;
					}
				}
			  	if(flag != true)
			  	{
			  		Reports.add("Fail","Webelement "+locator+" should get loaded in "+maxWaitVal+" msec",
						 "Webelement "+locator+" is not loaded after "+i+" msec",
						 LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			  	}	
		  }
		  catch(Exception e)
		  {
				 Reports.add("Fail","Webelement "+locator+" should get loaded in "+maxWaitVal+" msec",
						 e.getMessage(),LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
		  }	  
	  }
	  
	  public  String getWindowsToolTipText( String locator,String by)
	  {
			WebElement WbElement=null;
			String titleText=null;
			try
			{
				if(isWindowsElementPresent(locator, by))
				{	
					WbElement=  getWindowsElement(locator,by);
					titleText= WbElement.getAttribute("title");
					Reports.add("Pass","Tool tip of "+locator+"should get captured using "+by,"Tool tip of "+locator+" is "+titleText, 
						LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
				}
			}
			catch(Exception e)
			{
				Reports.add("Fail","Tool tip of "+locator+"should get captured using "+by,e.getMessage(), 
						LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			}
			return titleText;
	 }
	  
	  public  void dragAndDropWindows(String srcLocator,String srcLocatorType, String DestnLocator,String DestnLocatorType)
	  {
			WebElement WbElementSrc=null;
			WebElement WbElementDestn=null;
			try
			{
				if(isWindowsElementPresent(srcLocator, srcLocatorType) &&  isWindowsElementPresent(DestnLocator, DestnLocatorType) )
				{	
					Actions act = new Actions(windriver);
					WbElementSrc =  getWindowsElement(srcLocator,srcLocatorType);
					WbElementDestn= getWindowsElement(DestnLocator,DestnLocatorType);
			    
					act.dragAndDrop(WbElementSrc, WbElementDestn).perform();
					Reports.add("Pass",	"File should get dragged from "+srcLocator+" to "+DestnLocatorType,
							"File has been dragged from "+srcLocator+" to "+DestnLocatorType,
							LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));			
				}
			}
			catch(Exception e)
			{
				Reports.add("Fail","File should get dragged from "+srcLocator+" to "+DestnLocatorType,e.getMessage(),
						LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			}
		} 
	  
	    public  void killApp() throws InterruptedException
		{
			try 
			{
				windriver.close();
				//driver.quit();
				Thread.sleep(8000);
				Reports.add("Pass","Winium driver should get closed","Winium driver is closed", 
						LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				Reports.add("Fail","Winium driver should get closed",e.getMessage(), 
						LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			}
		}
	    
	    public  void selectWindowsDropDownValueByType(String locator,String by,String value,String selectType) 
		{
			WebElement WbElement=null;
			try
			{
				if(selectType.equalsIgnoreCase("index"))
				{
					int IndexNum = Integer.valueOf(value);
					if(isWindowsElementPresent(locator, by))
					{		
						WbElement=  getWindowsElement(locator,by);
						new Select(WbElement).selectByIndex(IndexNum);
						Reports.add("Pass","List box  "+locator+" value "+value+" should get selected using "+selectType,
								"List box "+locator+" value "+value+"is selected using "+selectType,
								LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
					}
				}
				else if(selectType.equalsIgnoreCase("visibletext"))
				{
					if(isWindowsElementPresent(locator, by))
					{	
						WbElement=  getWindowsElement(locator,by);
						new Select(WbElement).selectByVisibleText(value);
						Reports.add("Pass","List box "+locator+" value "+value+" should get selected using "+selectType,
							"List box "+locator+" value "+value+" is selected using "+selectType,
							LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
					}
				}
				else if(selectType.equalsIgnoreCase("value"))
				{
					if(isWindowsElementPresent(locator, by))
					{	
						WbElement=  getWindowsElement(locator,by);
						new Select(WbElement).selectByValue(value);
						Reports.add("Pass","List box "+locator+" value "+value+" should get selected using "+selectType,
							"List box "+locator+" value "+value+" is selected using "+selectType,
							LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
					}
				}
			}
			catch(Exception e)
			{
				Reports.add("Fail","List box "+locator+" value "+value+" should get selected using "+selectType,
						e.getMessage(),LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			}
		}
	    
	    public  void placeCursorOnWindowsElement(String locator,String by) throws NoSuchElementException
	      {
	      
	    	  WebElement WbElement=null;
	          try
	          {
	        	  if (isWindowsElementDisplayed(locator, by) )
	        	  {
	        		  WbElement= getWindowsElement(locator,by);
	        		  new Actions(windriver).moveToElement(WbElement).perform();
	        	
	        		  Reports.add("Pass","Cursor should place on webelement "+locator,"Cursor has been placed on webelement "+locator, 
	          					  LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
	        	  }
	          }
	      	  catch (Exception e)
	          {
	          	  Reports.add("Fail","Cursor should place on element "+locator,e.getMessage(), 
	          		  LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
	      	  }
	      }
	    
	    public boolean verifyWindowsCheckBoxIsChecked(String locator,String by,String attachedText)
		  {
			  String elementText = null;
			  boolean condition = false;
			  try
			  {
				  if (isWindowsElementPresent(locator,by))
				  {
					  WebElement wbElement = getWindowsElement(locator,by);
					  elementText = getWindowsElementPropertyValue(locator,by);
					  if(elementText.equals(attachedText))
					  {
						  if(wbElement.isSelected())
						  {
							  condition = true;
							  //Reports.add("Pass","Check box "+locator+" with value "+attachedText+" should get checked using "+by,
									  //"Check box "+locator+" with value "+attachedText+" has been checked using "+by, 
									  //LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
						  }
					  }
				  }
			  }
			  catch(Exception e)
			  {
				  Reports.add("Fail","Check box "+locator+" with value "+attachedText+" should get checked using "+by,
						  e.getMessage(),LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			  }
			  	  return condition;
		}
	    
	    public boolean verifyRadioButtonIsSelected(String locator,String by,String attachedText)
		  {
			  String elementText = null;
			  boolean condition = false ;
			  try
			  {
				  if (isWindowsElementPresent(locator,by))
				  {
					  WebElement wbElement = getWindowsElement(locator,by);
					  elementText = getWindowsElementPropertyValue(locator,by);
					  if(elementText.equals(attachedText))
					  {
						  if(wbElement.isSelected())
						  {
							  condition = true;
							  //Reports.add("Pass","Radion button "+locator+" with value "+attachedText+" should get selected using "+by,
									  //"Radion button "+locator+" with value "+attachedText+" is selected using "+by, 
			  					  //LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
						  }
					  }
				  }
				  
			  }
			  catch(Exception e)
			  {
				  Reports.add("Fail","Radion button "+locator+"with value "+attachedText+" should get selected using "+by,e.getMessage(), 
		  				  LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			  }  
			  return condition;
		  }
	    
	    public void selectCheckBox(String locator,String by,String attachedText)
		  {
			  String attrVal = null; 
			  	try
			  	{
			 	  if (isWindowsElementPresent(locator,by))
				  {
					  WebElement wbElement = getWindowsElement(locator,by);
					  attrVal = getWindowsElementPropertyValue(locator,by);		  	  
			  		  if(attrVal.equals(attachedText))
			  		  {
			  			  if(verifyWindowsCheckBoxIsChecked(locator,by,attrVal) == false)
			  			  {
			  				  wbElement.click();
			  				  Reports.add("Pass","Check box "+locator+" with value "+attachedText+"should get checked using "+by,
			  						  "Check box "+locator+" with value "+attachedText+" has been checked using "+by, 
			  						  LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
			  			  }
			  			  else
			  			  {
			  				  Reports.add("Info","Check box "+locator+" with value "+attachedText+"should get checked using "+by,
			  						  "Check box "+locator+" with value "+attachedText+" is already checked", 
			  						  LogAs.INFO, new CaptureScreen(ScreenshotOf.DESKTOP));
			  			  }
			  		  }
			  	  }
			  	}
			  	catch(Exception e)
			  	{
				  	  Reports.add("Fail","Check box "+locator+" with value "+attachedText+"should get checked using "+by,
				  			  e.getMessage(),LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			  	}
			  }
	    
	    public void clickWindowsButton(String locator,String by) 
		  {
			  try
			  {
				  if (isWindowsElementPresent(locator,by))
				  {
					  WebElement wbElement = getWindowsElement(locator,by);
					  wbElement.click();
					  Reports.add("Pass","Button "+locator+" should get clicked using "+by,"Button "+locator+" is clicked using "+by, 
						  LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
				  }
			  }
			  catch(Exception e)
			  {
				  Reports.add("Fail","Button "+locator+" should get clicked using "+by,e.getMessage(), 
						  LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			  }
		  }
}
