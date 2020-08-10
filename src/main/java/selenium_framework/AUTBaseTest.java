package selenium_framework;


import java.awt.AWTException;
import java.awt.Robot;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
import java.util.Map;
import java.util.Properties;
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
import org.apache.log4j.Logger;
import org.eclipse.jetty.util.log.Log;
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
import org.openqa.selenium.remote.RemoteWebDriver;
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

import selenium_framework.CaptureScreen;
import selenium_framework.CaptureScreen.ScreenshotOf;
import selenium_framework.LogAs;
import selenium_framework.Reports;

import com.gargoylesoftware.htmlunit.BrowserVersion;
//import com.gargoylesoftware.htmlunit.javascript.host.Document;



public class AUTBaseTest extends CommonUtils

{
	static Logger logger = Logger.getLogger(AUTBaseTest.class.getName());
	public static WebDriver driver;
	public static String browserName;
	 public static String PROPERTYfile ="resource.properties";
	 public static String id="";
	public static String accessKey="";
	
	/**
	* @Method_Name : ATUConfig 
	* Method  to setup ATU Reporter property details 
	* like Author Information, Index Page Description
	* @author Arindam
	* @return void
	*/
	public void ReportsConfig()
	{
	      Reports.setAuthorInfo(Directory.authorName, Utils.getCurrentTime(),"1.0");
	} 
	
	/**
	* @Method_Name : setupBrowser 
	* Method to instantiate and launch web browser based on
	* �Browser Type� (Firefox, IE, Chrome) provided as test input.
	* This method works on the actual path of driver provided 
	* in resource.properties file.
	* @param browser :IE,Firefox,Chrome,PhantomJS
	* @return Webdriver 
	* @author Payel
	*/
	public  WebDriver setupBrowser(String browser)	
	{
		 try
		 {
			 browserName = browser;
			 if(browser.equalsIgnoreCase("firefox"))
			 {
				 FirefoxProfile  firefoxProfile = new FirefoxProfile();
				 driver=new FirefoxDriver(firefoxProfile);
				 Reports.setWebDriver(driver);

					 Reports.add("Pass","Firefox should initialize","Firefox initialized", 
						 LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));

			 }
			 else if(browser.equalsIgnoreCase("IE"))
			 {
				 File file = new File(Directory.ieDriverPath);
				 System.setProperty("webdriver.ie.driver", file.getPath());
				 driver = new InternetExplorerDriver();
				 Reports.setWebDriver(driver);

					 Reports.add("Pass","Internet Explorer should initialize","Internet Explorer initialized", 
						 LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));

			 }
			 else if(browser.equalsIgnoreCase("Chrome"))
			 {
				 File fileChrome=new File(Directory.chromeDriverPath);
				 System.setProperty("webdriver.chrome.driver", fileChrome.getPath());
				 driver=new ChromeDriver();
				 Reports.setWebDriver(driver);

					 Reports.add("Pass","Chrome should initialize","Chrome initialized", 
						 LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			 }
			 else if(browser.equalsIgnoreCase("phantomjs"))
			 {
				 File filePhantom=new File(Directory.phantomJSPath);
				 System.setProperty("phantomjs.binary.path", filePhantom.getPath());
				 driver=new PhantomJSDriver();
				 Reports.setWebDriver(driver);

					 Reports.add("Pass","PhantomJS browser should initialize","PhantomJS initialized", 
						 LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			 }
			 else if(browser.equalsIgnoreCase("htmlunit"))
			 {
				 driver=new HtmlUnitDriver();
				 ((HtmlUnitDriver) driver).setJavascriptEnabled(true);
				 Reports.setWebDriver(driver);

					 Reports.add("Pass","HtmlUnit browser should initialize","HtmlUnit initialized", 
						 LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			 }
			 else if(browser.equalsIgnoreCase("headlesschrome"))
			 {
				 File fileChrome=new File(Directory.chromeDriverPath);
				 System.setProperty("webdriver.chrome.driver", fileChrome.getPath());
				 ChromeOptions options = new ChromeOptions();
				 options.addArguments("headless");
				 driver=new ChromeDriver(options);
				 Reports.setWebDriver(driver);

					 Reports.add("Pass","Headless Chrome browser should initialize","Headless Chrome initialized", 
						 LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			 }
		 }
		 catch(Exception e)
		 {	 
			 Reports.add("Fail",browser+"Browser should initialize",e.getMessage(), 
					 LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		 }
		return driver;
	}	
	
	/**
	* @Method_Name : setupBrowserAbsDriverPath 
	* Method to instantiate and launch web browser based on
	* �Browser Type� (Firefox, IE, Chrome) provided as test input.
	* This method works on the relative path of driver provided 
	* in resource.properties file.
	* @param browser :IE,Chrome,PhantomJS
	* @return Webdriver 
	* @author Arindam
	*/
	public  WebDriver setupBrowserAbsDriverPath(String browser)	
	{
		 try
		 {
			 browserName = browser;
			 String currDir = Directory.CURRENTDir;
			 if(browser.equalsIgnoreCase("Firefox"))
			 {
				 String driverPath = currDir + Directory.geckoDriverPath;
				 File file = new File(driverPath);
				 System.setProperty("webdriver.gecko.driver", file.getPath());
				 driver = new FirefoxDriver();
				 Reports.setWebDriver(driver);

					 Reports.add("Pass","Firefox should initialize","Firefox initialized", 
						 LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			 }
			 else if(browser.equalsIgnoreCase("IE"))
			 {
				 String driverPath = currDir + Directory.ieDriverPath;
				 File file = new File(driverPath);
				 System.setProperty("webdriver.ie.driver", file.getPath());
				 driver = new InternetExplorerDriver();
				 Reports.setWebDriver(driver);

					 Reports.add("Pass","Internet Explorer should initialize","Internet Explorer initialized", 
						 LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));

			 }
			 else if(browser.equalsIgnoreCase("Chrome"))
			 {
				 String driverPath = currDir + Directory.chromeDriverPath;
				 File fileChrome=new File(driverPath);
				 System.setProperty("webdriver.chrome.driver", fileChrome.getPath());
				 ChromeOptions o = new ChromeOptions();
				 /*o.addArguments("disable-extensions");
				 o.addArguments("--start-maximized");*/
				 Map<String, Object> prefs = new HashMap<String, Object>();
				 Map<String, Object> langs = new HashMap<String, Object>();
				 langs.put("fr", "en");
				 prefs.put("translate", "{'enabled' : true}");
				 prefs.put("translate_whitelists", langs);
				 o.setExperimentalOption("prefs", prefs); 
				 driver = new ChromeDriver(o);
				// driver=new ChromeDriver();
				 Reports.setWebDriver(driver);

					 Reports.add("Pass","Chrome should initialize","Chrome initialized", 
						 LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			 }
			 else if(browser.equalsIgnoreCase("phantomjs"))
			 {
				 String driverPath = currDir + Directory.phantomJSPath;
				 File filePhantom=new File(driverPath);
				 System.setProperty("phantomjs.binary.path", filePhantom.getPath());
				 driver=new PhantomJSDriver();
				 Reports.setWebDriver(driver);

					 Reports.add("Pass","PhantomJS browser should initialize","PhantomJS initialized", 
						 LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			 }
			 else if(browser.equalsIgnoreCase("htmlunit"))
			 {
				 driver=new HtmlUnitDriver();
				 ((HtmlUnitDriver) driver).setJavascriptEnabled(true);
				 Reports.setWebDriver(driver);

					 Reports.add("Pass","HtmlUnit browser should initialize","HtmlUnit initialized", 
						 LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			 }
			 else if(browser.equalsIgnoreCase("HeadlessChrome"))
			 {
				 String driverPath = currDir + Directory.chromeDriverPath;
				 File fileChrome=new File(driverPath);
				 System.setProperty("webdriver.chrome.driver", fileChrome.getPath());
				 ChromeOptions options = new ChromeOptions();
				 options.addArguments("headless");
				 driver=new ChromeDriver(options);
				 Reports.setWebDriver(driver);

					 Reports.add("Pass","Headless Chrome browser should initialize","Headless Chrome initialized", 
	 					LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));

			 }
		 }
		 catch(Exception e)
		 {	 
			 Reports.add("Fail",browser+"Browser should initialize",e.getMessage(), 
					 LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		 }
		return driver;
	}
	
	/**
	* @Method_Name :setupFirefoxProfile
	* Method to instantiate and launch Firefox browser with profile 
	* settings provided as test input Method sets up browser profile 
	* with properties like �proxy information�, disable file download 
	* manager prompt , enable any file type download, default 
	* �file download location� as provided  by tester before browser launch
	* @param proxyDetails :proxy of browser
	* @param downloadLocation :download location of file
	* @return WebDriver
	* @author Payel
	*/
	public WebDriver setupFirefoxProfile(String proxyDetails ,String downloadLocation)
	{
			 try
			 {
				 	 FirefoxProfile  firefoxProfile = new FirefoxProfile();
					 firefoxProfile.setAssumeUntrustedCertificateIssuer(false);
					 firefoxProfile.setEnableNativeEvents(false);
					 firefoxProfile.setPreference("network.proxy.type", 1);
					 firefoxProfile.setPreference("network.proxy.http", proxyDetails);
					 firefoxProfile.setPreference("network.proxy.http_port", 8080);
					 firefoxProfile.setPreference("network.proxy.ssl", proxyDetails); 
					 firefoxProfile.setPreference("network.proxy.ssl_port", 8080); 
					 firefoxProfile.setPreference("network.proxy.ftp", proxyDetails); 
					 firefoxProfile.setPreference("network.proxy.ftp_port", 8080); 
					 firefoxProfile.setPreference("network.proxy.socks", proxyDetails); 
					 firefoxProfile.setPreference("network.proxy.socks_port", 8080); 
					 File filePath = new File(downloadLocation);
					 String path=filePath.getCanonicalPath();
					 firefoxProfile.setPreference("browser.download.folderList",2);
					 firefoxProfile.setPreference("browser.download.manager.showWhenStarting",false);
					 firefoxProfile.setPreference("browser.download.dir",path);
					 firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk","application/octet-stream");

					 driver=new FirefoxDriver(firefoxProfile);
					 Reports.setWebDriver(driver);

						 Reports.add("Pass","Proxy "+proxyDetails+"and Download location"+ downloadLocation+" should set up for Firefox",
							 "Proxy "+proxyDetails+"and Download location"+ downloadLocation+" has been set up for Firefox",
							 LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));	
				 }
				 catch(Exception e)
				{
						e.printStackTrace();
						Reports.add("Fail","Proxy "+proxyDetails+"and Download location"+ downloadLocation+" should set up for Firefox",
								 e.getMessage(),LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				}
			 		return driver;
		}
	
	/**
	* @Method_Name :setupFirefoxProfile
	* Method to instantiate and launch Firefox browser with profile 
	* settings provided as test input Method sets up browser profile 
	* with properties like �proxy information�, disable file download 
	* manager prompt , enable any file type download, default 
	* �file download location� as provided  by tester before browser launch
	* @param proxyDetails :proxy of browser
	* @param downloadLocation :download location of file
	* @return WebDriver
	* @author Payel
	*/
	public WebDriver setupFirefoxGeckoProfile(String proxyDetails ,String downloadLocation)
	{
			String currDir = Directory.CURRENTDir;	 
			try
			 	{
				 	String driverPath = currDir + Directory.geckoDriverPath;
				 	File file = new File(driverPath);
				 	System.setProperty("webdriver.gecko.driver", file.getPath());
				 	 FirefoxProfile  firefoxProfile = new FirefoxProfile();
					 firefoxProfile.setAssumeUntrustedCertificateIssuer(false);
					 firefoxProfile.setEnableNativeEvents(false);
					 firefoxProfile.setPreference("network.proxy.type", 1);
					 firefoxProfile.setPreference("network.proxy.http", proxyDetails);
					 firefoxProfile.setPreference("network.proxy.http_port", 8080);
					 firefoxProfile.setPreference("network.proxy.ssl", proxyDetails); 
					 firefoxProfile.setPreference("network.proxy.ssl_port", 8080); 
					 firefoxProfile.setPreference("network.proxy.ftp", proxyDetails); 
					 firefoxProfile.setPreference("network.proxy.ftp_port", 8080); 
					 firefoxProfile.setPreference("network.proxy.socks", proxyDetails); 
					 firefoxProfile.setPreference("network.proxy.socks_port", 8080); 
					 File filePath = new File(downloadLocation);
					 String path=filePath.getCanonicalPath();
					 firefoxProfile.setPreference("browser.download.folderList",2);
					 firefoxProfile.setPreference("browser.download.manager.showWhenStarting",false);
					 firefoxProfile.setPreference("browser.download.dir",path);	
					 firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk","application/octet-stream");

					 driver=new FirefoxDriver(firefoxProfile);
					 Reports.setWebDriver(driver);

						 Reports.add("Pass","Proxy "+proxyDetails+"and Download location"+ downloadLocation+" should set up for Firefox",
							 "Proxy "+proxyDetails+"and Download location"+ downloadLocation+" has been set up for Firefox",
							 LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));	
				 }
				 catch(Exception e)
				{
						e.printStackTrace();
						Reports.add("Fail","Proxy "+proxyDetails+"and Download location"+ downloadLocation+" should set up for Firefox",
								 e.getMessage(),LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				}
			 		return driver;
		}

	/**
	* @Method_Name :setupIEProfile
	* Method to instantiate and launch IE browser with profile settings provided as test input
	* Method sets up browser profile with properties like default 
	* �file download location� as provided  by tester before browser launch
	* @param downloadLocation :download location of file
	* @throws AWTException,InterruptedException
	* @return WebDriver
	* @author Arindam
	*/
	public WebDriver setupIEProfile(String downloadLocation) throws AWTException,InterruptedException
	{
		String currDir = Directory.CURRENTDir;
		try
		{
			String IESeleniumDriverPath = currDir + Directory.ieDriverPath;
			File file = new File(IESeleniumDriverPath);
			System.setProperty("webdriver.ie.driver", file.getPath());
			InternetExplorerDriverService service = InternetExplorerDriverService.createDefaultService();
			DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			capabilities.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL, "about:blank");
			capabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
			capabilities.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, true);
			capabilities.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, false);
			
			driver = new InternetExplorerDriver(service, capabilities);
			Reports.setWebDriver(driver);
			File filePath = new File(downloadLocation);
			String path=filePath.getCanonicalPath();
			copyTextToClipboard(path); 

			Robot robo = new Robot();					
			openIEDownloadTrayViaRobot(robo);
			pressEnterViaRobot(robo,1);
			pressTabViaRobot(robo,5);	
			pressEnterViaRobot(robo,1);
			pasteViaRobot(robo);
			pressEnterViaRobot(robo,1);
			pressTabViaRobot(robo,1);		
			pressEnterViaRobot(robo,1);
			pressTabViaRobot(robo,2);
			pressEnterViaRobot(robo,1);
			pressTabViaRobot(robo,2);
			pressEnterViaRobot(robo,1);
				
			Thread.sleep(10000);
	
				Reports.add("Pass","Download location "+downloadLocation+" and profile should set up for IE",
						"Download location and profile has been set up for IE",LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		catch(Exception e)
		{
			  Reports.add("Fail","Download location"+downloadLocation+" and profile should set up for IE",e.getMessage(), 
				 LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		return driver;
	}	
	
	/**
	* @Method_Name :setupChromeUnix
	* Method to instantiate and launch Chrome browser in Unix
	* @return WebDriver
	* @author Arindam
	*/
	public WebDriver setupChromeUnix()
	{
		String currDir = Directory.CURRENTDir;
		try
		{
			File fileChrome=new File(currDir + Directory.chromeDriverPath);
			System.setProperty("webdriver.chrome.driver", fileChrome.getPath());
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--headless");
			driver = new ChromeDriver(options);
			Reports.setWebDriver(driver);
			
				 Reports.add("Pass","Headless Chrome browser should initialize in Unix","Headless Chrome initialized in Unix", 
	 					LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Reports.add("Fail","Headless Chrome browser should initialize in Unix", e.getMessage(),
				LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		return driver;
	}
	
	/**
	* @Method_Name :setupChromeProfile
	* Method to instantiate and launch IE browser with profile 
	* settings provided as test input Method sets up browser profile 
	* with properties like default �file download location� as provided  
	* by tester before browser launch
	* @param downloadLocation :download location of file
	* @return WebDriver
	* @author Arindam
	*/
	public WebDriver setupChromeProfile(String downloadLocation)
	{
		String currDir = Directory.CURRENTDir;
		try
		{
			File fileChrome=new File(currDir + Directory.chromeDriverPath);
			System.setProperty("webdriver.chrome.driver", fileChrome.getPath());

			File filePath = new File(downloadLocation);
			String path=filePath.getCanonicalPath();
			HashMap<String, Object> chromePreference = new HashMap<String, Object>();
			chromePreference.put("download.prompt_for_download", false);
			chromePreference.put("download.default_directory", path);
		
			ChromeOptions options = new ChromeOptions();
			options.setExperimentalOption("prefs", chromePreference);
			
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			capabilities.setCapability(ChromeOptions.CAPABILITY, options);
					 
			driver = new ChromeDriver(capabilities);
			Reports.setWebDriver(driver);
			
				Reports.add("Pass","Download location"+downloadLocation+" should set up for Chrome",
					"Download location"+downloadLocation+" has been set up for Chrome",
					LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Reports.add("Fail","Download location"+downloadLocation+" should set up for Chrome",
					 e.getMessage(),LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		return driver;
	}
	
	/**
	* @Method_Name :downloadFileWithRobotFF
	* Method to download file using Window Handle 
	* Methods that enables download of file from Firefox 
	* browser using Window Handle controls Method completes 
	* file download using Robot Class window handle control 
	* methods when prompted for File Download manager in 
	* Firefox Browser Per-conditions set - FileDownloadLocation 
	* is set during browser launch
	* @author Arindam
	* @return void
	* @throws AWTException 
	*/
	public void downloadFileWithRobotFF() throws AWTException
	{
			try
			{
				Robot robo = new Robot();
				Thread.sleep(10000);
				pressTabViaRobot(robo, 2);
				pressEnterViaRobot(robo, 1);
				pressEnterViaRobot(robo, 1);
					Reports.add("Pass","File should download in location ","File downloaded in file location", 
							LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}
			catch(Exception e)
			{	
				Reports.add("Fail","File should download",e.getMessage(),
					LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}		
		}
	
	/**
	* @Method_Name :downloadFileWithRobotChrome
	* Method to download file using Window Handle Methods 
	* that enables download of file from Chrome browser 
	* using Window Handle controls Method completes file 
	* download using Robot Class window handle control 
	* methods when prompted for File Download manager in 
	* Chrome Browser Per-conditions set - FileDownloadLocation 
	* is set during browser launch
	* @author Arindam
	* @return void
	* @throws AWTException 
	*/
		public void downloadFileWithRobotChrome()throws AWTException
		{
			try
			{
				Robot robo = new Robot();
				pressEnterViaRobot(robo, 2);
					Reports.add("Pass","File should download ","File downloaded", 
							LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}
			catch(Exception e)
			{
				Reports.add("Fail","Robot event should work fine in chrome",e.getMessage(), 
						LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}
		}
		
		/**
		* @Method_Name :downloadFileWithRobotIE
		* Method to download file using Window Handle 
		* Methods that enables download of file from 
		* Internet Explorer browser using Window Handle controls
		* Method completes file download using Robot Class window 
		* handle control methods when prompted for File Download 
		* manager in Internet Explorer Browser
		* @param fileLocation :Location where you want to save the file
		* @author Arindam
		* @return void
		* @throws InterruptedException,AWTException 
		*/
		public void downloadFileWithRobotIE(String fileLocation) throws InterruptedException,AWTException
		{
			try
			{
				Robot robo = new Robot();
				switchIEDownloadManagerViaRobot(robo);
				pressTabViaRobot(robo,1);
				upKeyPressViaRobot(robo,1);
				downKeyPressViaRobot(robo,1);
				pressEnterViaRobot(robo,1);
				
				Thread.sleep(10000);
				File filePath = new File(fileLocation);
				String path=filePath.getCanonicalPath();
				copyTextToClipboard(fileLocation);
				pasteViaRobot(robo);
				pressEnterViaRobot(robo,1);
				
				Thread.sleep(10000);
					Reports.add("Pass","File should get saved using download manager in "+fileLocation,
						"File saved using download manager in "+fileLocation ,LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}
			catch(Exception e)
			{
				Reports.add("Fail","File should get saved using download manager in "+fileLocation,
						e.getMessage() ,LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}
		}
	
	
	/**
	* @Method_Name :accessHomePage
	* Method to navigate to Application Home page 
	* with Application �URL� provided as test input
	* Validates Home Page has rendered successfully
	* @param oraURL :URL of the application
	* @param locator :Locator of the web element
	* @param by :locator used to find the web element
	* @return Void
	* @author Payel
	*/
	public  void accessHomePage(String oraURL,String locator , String by)	
	{
		 try
		 {
			 driver.get(oraURL);
			 driver.manage().timeouts().pageLoadTimeout(90, TimeUnit.SECONDS);
			 driver.manage().window().maximize();
			 String title = driver.getTitle();
			 Thread.sleep(1000);
				 if(isElementDisplayed(locator,by))
				 {
					 Reports.add("Pass","Browser should navigate to URL "+oraURL,"Navigated to URL "+oraURL,
						 LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
				 }
		 }
		 catch(Exception e) 
		 {
				Reports.add("Fail","Browser should navigate to URL "+oraURL,e.getMessage(), 
					LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		 }
	}
	
	/**
	* @Method_Name :homePageHandleAlert
	* Method to navigate to Application Home page 
	* with Application �URL� provided as test input
	* Validates Home Page has rendered successfully
	* @param oraURL :URL of the application
	* @param locator :Locator of the web element
	* @param by :locator used to find the web element
	* @param choice : choice to click pop up button
	* @param msg : string in popup
	* @return Void
	* @author Arindam
	*/
	public  void homePageHandleAlert(String oraURL,String locator , String by)	
	{
		 try
		 {
			 Alert alertWindow = null;
			 driver.get(oraURL);
			 driver.manage().timeouts().pageLoadTimeout(90, TimeUnit.SECONDS);
			 driver.manage().window().maximize();
			 String title = driver.getTitle();
			 Thread.sleep(5000);
			 
			 if (ExpectedConditions.alertIsPresent() != null)
			 {
				  alertWindow = driver.switchTo().alert();
				  alertWindow.accept();
			 }
			 Thread.sleep(5000);
				 if(isElementDisplayed(locator,by))
				 {
					 Reports.add("Pass","Browser should navigate to URL "+oraURL+" and click popup","Navigated to URL "+oraURL+ "and clicked popup",
						 LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
				 }
		 }
		 catch(Exception e) 
		 {
				Reports.add("Fail","Browser should navigate to URL "+oraURL+" and click popup",e.getMessage(), 
					LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		 }
	}
	/**
	* @Method_Name :getElement
	* Wrapper for WebDriver method findElement 
	* Method to find and return Web Element as 
	* per matching element Locator and Locator 
	* Type provided as method argument
	* @param locator :Locator of the web element
	* @param by :locator used to find the web element
	* @return WebElement
	* @author Payel
	*/
	public WebElement getElement(String locator, String by)
	{
		WebElement webelement = null;
		try
		{
			switch (by) 
			{
				case "id": webelement = driver.findElement(By.id(locator));
					//Reports.add("Pass","Driver should identify locator "+locator+" using "+by,"Webelement "+locator+" identified using "+by, 
							//LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				break;
				case "name": webelement =  driver.findElement(By.name(locator));
					//Reports.add("Pass","Driver should identify locator "+locator+" using "+by,"Webelement "+locator+" identified using "+by, 
							//LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				break;
				case "xpath": webelement= driver.findElement(By.xpath(locator));
					//Reports.add("Pass","Driver should identify locator "+locator+" using "+by,"Webelement "+locator+" identified using "+by, 
							//LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));

				break;
				case "linkText": webelement= driver.findElement(By.linkText(locator));     
					//Reports.add("Pass","Driver should identify locator "+locator+" using "+by,"Webelement "+locator+" identified using "+by, 
							//LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				break;
				case "cssSelector": webelement= driver.findElement(By.cssSelector(locator));     
					//Reports.add("Pass","Driver should identify locator "+locator+" using "+by,"Webelement "+locator+" identified using "+by, 
							//LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				break;
				case "className": webelement= driver.findElement(By.className(locator));     
					//Reports.add("Pass","Driver should identify locator "+locator+" using "+by,"Webelement "+locator+" identified using "+by, 
							//LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));	
				break;
				case "partialLinkText": webelement= driver.findElement(By.partialLinkText(locator));     
					//Reports.add("Pass","Driver should identify locator "+locator+" using "+by,"Webelement "+locator+" identified using "+by, 
							//LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				break;
				case "tagname": webelement= driver.findElement(By.tagName(locator));     
					//Reports.add("Pass","Driver should identify locator "+locator+" using "+by,"Webelement "+locator+" identified using "+by, 
							//LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				break;
				default: Reports.add("Fail","Driver should identify locator "+locator+" using "+by,
					"Unable to identify webelement "+locator+" using "+by,LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				break;
			}
		}
		catch(Exception e)
		{
			Reports.add("Fail","Driver should identify locator "+locator+" using "+by,e.getMessage(),
					LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		return webelement;
	 }
	
	
	/**
	* @Method_Name :getElements
	* Wrapper for WebDriver method findElements 
	* Method to find and return list of Web Elements as 
	* per matching element Locator and Locator 
	* Type provided as method argument
	* @param locator :Locator of the web element
	* @param by :locator used to find the web element
	* @return WebElement
	* @author Arindam
	*/
	public static List<WebElement> getElements(String locator, String by)
	{
		List<WebElement> webelement = null;
		try
		{
			switch (by) 
			{
				case "id": webelement = driver.findElements(By.id(locator));
					//Reports.add("Pass","Driver should identify locator "+locator+" using "+by,"Webelement "+locator+" identified using "+by, 
							//LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				break;
				case "name": webelement =  driver.findElements(By.name(locator));
					//Reports.add("Pass","Driver should identify locator "+locator+" using "+by,"Webelement "+locator+" identified using "+by, 
							//LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				break;
				case "xpath": webelement= driver.findElements(By.xpath(locator));
					//Reports.add("Pass","Driver should identify locator "+locator+" using "+by,"Webelement "+locator+" identified using "+by, 
							//LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));

				break;
				case "linkText": webelement= driver.findElements(By.linkText(locator));     
					//Reports.add("Pass","Driver should identify locator "+locator+" using "+by,"Webelement "+locator+" identified using "+by, 
							//LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				break;
				case "cssSelector": webelement= driver.findElements(By.cssSelector(locator));     
					//Reports.add("Pass","Driver should identify locator "+locator+" using "+by,"Webelement "+locator+" identified using "+by, 
							//LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				break;
				case "className": webelement= driver.findElements(By.className(locator));     
					//Reports.add("Pass","Driver should identify locator "+locator+" using "+by,"Webelement "+locator+" identified using "+by, 
							//LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));	
				break;
				case "partialLinkText": webelement= driver.findElements(By.partialLinkText(locator));     
					//Reports.add("Pass","Driver should identify locator "+locator+" using "+by,"Webelement "+locator+" identified using "+by, 
							//LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				break;
				case "tagname": webelement= driver.findElements(By.tagName(locator));     
					//Reports.add("Pass","Driver should identify locator "+locator+" using "+by,"Webelement "+locator+" identified using "+by, 
							//LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				break;
				default: Reports.add("Fail","Driver should identify locator "+locator+" using "+by,
					"Unable to identify webelements "+locator+" using "+by,LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				break;
			}
		}
		catch(Exception e)
		{
			Reports.add("Fail","Driver should identify locator "+locator+" using "+by,e.getMessage(),
					LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		return webelement;
	 }
	
	/**
	* @Method_Name :getElementPropertyValue
	* Common Method that accepts element Locator, 
	* Locator Type provided as test input and fetches 
	* element property �Value� if the matching element 
	* is displayed Used to fetch and return entered text 
	* input in Free Textbox elements
	* @param locator :Locator of the web element
	* @param by :locator used to find the web element
	* @return String : attribute value of the web element if it is present else null
	* @author Arindam
	*/
	  public String getElementPropertyValue (String locator, String by)
	  {
		  String attrValue = null;
		  try
		  {
				  if (isElementDisplayed(locator,by))
				  {
					  WebElement wbElement = getElement(locator,by);
					  attrValue = wbElement.getAttribute("value");
					  /*Reports.add("Pass","Webdriver should get property value of webelement "+locator+" using"+by ,
						  "Property value of webelement "+locator+" using"+by+" is "+attrValue, 
						  	LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));*/
				  }
		  }
		  catch(Exception e)
		  {
			  Reports.add("Fail","Webdriver should get property value of webelement "+locator+" using"+by ,e.getMessage(), 
					  LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		  }
		  return attrValue;
	  }
	  
	  /**
	  * @Method_Name :getDisplayedText
	  * Common Method that accepts element Locator, 
	  * Locator Type provided as test input and fetches 
	  * element attached text if the matching element is 
	  * displayed Used to fetch and return attached text 
	  * for Web Link, Web Button  and Web Message elements
	  * @param locator :Locator of the web element
	  * @param by :locator used to find the web element
	  * @return String: Text of the web element
	  * @author Arindam
	  */
	  public String getDisplayedText (String locator, String by)
	  {
		  String attrValue = null;
		  try
		  {
			  if (isElementPresent(locator,by))
			  {
				  WebElement wbElement = getElement(locator,by);
				  attrValue = wbElement.getText();
					  /*Reports.add("Pass","Display text of webelement "+locator+" using "+by+" should get captured",
						  "Displayed text of webelement "+locator+" using "+by+" is "+attrValue, 
					  LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));*/
			  }
		  }
		  catch(Exception e)
		  {
			  Reports.add("Fail","Display text of webelement "+locator+" using "+by+" should get captured",e.getMessage(), 
					  LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		  }
		  return attrValue;
	  }
	  
	  /**
	  * @Method_Name :getSelectedListBoxText
	  * Common Method that accepts element Locator, 
	  * Locator Type provided as test input and fetches 
	  * element current selected value if the matching 
	  * element is displayed Used to fetch and return present 
	  * selected value of the Dropdown List element
	  * @param locator :Locator of the web element
	  * @param by :locator used to find the web element
	  * @return String : Selected value of the List Box if it is present else null
	  * @author Arindam
	  */
	  public String getSelectedListBoxText (String locator, String by)
	  {
		  String attrValue = null;
		  try
		  {
			  if (isElementPresent(locator,by))
			  {
				  Select select = (Select) getElement(locator,by);
				  attrValue = select.getFirstSelectedOption().getText();
					  /*Reports.add("Pass","Fetch selected value of listbox "+locator+" using "+by,
						  "Selected Value of listbox "+locator+" using "+by+" is "+attrValue,
						  LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));*/
			  }
		  }
		  catch(Exception e)
		  {
			  Reports.add("Fail","Fetch selected value of listbox "+locator+" using "+by,e.getMessage(), 
					  LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		  }
		  return attrValue;
	  }
	  
	      /**
		  * @Method_Name :getSelectedListBoxTextArray
		  * Common Method that accepts element Locator, 
		  * Locator Type provided as test input and fetches 
		  * all values in a combo box if the matching element 
		  * is displayed Used to fetch and return all present 
		  * value of the Combo box drop down element
		  * @param locator :Locator of the web element
		  * @param by :locator used to find the web element
		  * @return List<WebElement> : Value of selected of the List Box if it is present else null
		  * @author Arindam
		  */
		  public List<WebElement> getSelectedListBoxTextArray (String locator, String by)
		  {
			  List<WebElement> attrValue = null;
			  try
			  {
				  if (isElementPresent(locator,by))
				  {
					  Select select = (Select) getElement(locator,by);
					  attrValue = select.getOptions();
					  /*Reports.add("Pass","Content of listbox "+locator+" using "+by+" should store is a list",
							  "Content of listbox "+locator+" using "+by+" are stored in "+attrValue, 
						  LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));*/ 
				  }
			  }
			  catch(Exception e)
			  {
				  Reports.add("Fail","Content of listbox "+locator+" using "+by+" should store is a list",e.getMessage(), 
						  LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			  }
			  return attrValue;
		  }
	  
	  /**
	  * @Method_Name :getElementText
	  * Wrapper method to find element value of 
	  * any type of web element Method to find and 
	  * return element text as per matching element 
	  * Locator , Locator Type and Element Type 
	  * (Web Textbox, Link, Dropdown list etc.) 
	  * provided as method argument
	  * @param locator :Locator of the web element
	  * @param elementType : Type of web element
	  * @param by :locator used to find the web element
	  * @return String : Text value of the web element 
	  * @author Arindam
	  */
	  public String getElementText(String locator, String by,String elementType)
	  {
		  String attrValue = null;
		  try
		  {
			  switch(elementType)
			  {
		  			case "textbox" : attrValue = getElementPropertyValue (locator,by);
		  			//Reports.add("Pass","Text of "+elementType+" "+locator+" should store in a variable using "+by,
		  					//"Text of "+elementType+" "+locator+" is "+attrValue, 
		  					//LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		  			break;
		  			case "radiobutton" : attrValue = getElementPropertyValue(locator,by);
		  			//Reports.add("Pass","Text of "+elementType+" "+locator+" should store in a variable using "+by,
		  					//"Text of "+elementType+" "+locator+" is "+attrValue,  
		  					//LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		  			break;
		  			case "link" :	attrValue = getDisplayedText(locator,by);
		  			//Reports.add("Pass","Text of "+elementType+" "+locator+" should store in a variable using "+by,
		  					//"Text of "+elementType+" "+locator+" is "+attrValue,  
		  					//LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		  			break;
		  			case "checkbox" : attrValue = getElementPropertyValue(locator,by);
		  			//Reports.add("Pass","Text of "+elementType+" "+locator+" should store in a variable using "+by,
		  					//"Text of "+elementType+" "+locator+" is "+attrValue,  
		  					//LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		  			break;
		  			case "button" : attrValue = getDisplayedText(locator,by);
		  			//Reports.add("Pass","Text of "+elementType+" "+locator+" should store in a variable using "+by,
		  					//"Text of "+elementType+" "+locator+" is "+attrValue,  
		  					//LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		  			break;
		  			case "listbox" : attrValue = getSelectedListBoxText(locator,by);
		  			//Reports.add("Pass","Text of "+elementType+" "+locator+" should store in a variable using "+by,
		  					//"Text of "+elementType+" "+locator+" is "+attrValue,  
		  					//LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		  			break;
		  			default: Reports.add("Fail","Text of "+elementType+" "+locator+" should store in a variable using "+by,
		  					"Unable to capture text of "+elementType+" "+locator+" using "+by 
		  					,LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		  			break;
			  }
		  }
		  catch(Exception e)
		  {
			  Reports.add("Fail","Text of "+elementType+" "+locator+" should store in a variable using "+by,
	  					e.getMessage(),LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		  }
		  return attrValue;
		  
	  }
	  
	  
	  /**
	  * @Method_Name :waitForPage
	  * Method to dynamically wait for page 
	  * to load and keep checking presence of 
	  * expected page element.Method uses Web 
	  * Driver method FluentWait on matching 
	  * element as per Locator and Locator Type 
	  * provided for minimum and maximum wait 
	  * duration provided as method argument
	  * @param locator :Locator of the web element
	  * @param maxWait :Maximum time user wants to wait for the web element
	  * @param minWait :Minimum time user wants to wait for the web element
	  * @param by : Variable to fine the locator
	  * @return Void
	  * @throws TimeoutException
	  * @author Payel
	  * @Modified_By Arindam
	  */
	  public void waitForPage(final String locator, final String by,final int maxWait,final int minWait) throws TimeoutException
	  {
			 try
			 {
				 Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
					  .withTimeout(maxWait,TimeUnit.MILLISECONDS)
					  .pollingEvery(minWait, TimeUnit.MILLISECONDS)
					  .ignoring(NoSuchElementException.class);
			  
				 wait.until(new ExpectedCondition<WebElement>()
					  {
			   				public WebElement apply (WebDriver driver)
			   				{
			   					WebElement element =getElement(locator,by);
			   					Reports.add("Pass","Webelement "+locator+" should get loaded within "+maxWait+" sec",
			   							"Webelement "+locator+" is loaded in "+minWait+" msec", 
			   							LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			   					return element;
			   				}
					  }
					  );
			 }
			 catch(Exception e)
			 {
				 e.printStackTrace();
				 Reports.add("Fail","Webelement "+locator+" should get loaded within "+maxWait+" msec",e.getMessage(), 
						 LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			 }
	  }
	  
	  
	  /**
	  * @Method_Name :waitForDynamicElement
	  * Method to dynamically wait for page to load and keep checking presence of 
	  * expected page element until defined time out is reached.Method uses custom 
	  * polling to check presence of matching element as per Locator and Locator Type 
	  * provided and wait until maximum duration provided as method argument is reached
	  * @param locator :Locator use to find the web element
	  * @param by :Use to identify Locator type
	  * @param maxWaitVal :Maximum time a web element took to load
	  * @param expectedVal :Expected time a web element took to load
	  * @return Void
	  * @throws StaleElementReferenceException,NullPointerException,InterruptedException
	  * @author Arindam
	  */
	  public void waitForDynamicElement(String locator, String by,String elementType,int maxWaitVal,String expectedVal) 
			  throws StaleElementReferenceException,NullPointerException,InterruptedException
	  {
		  int i = 0;
		  try 
		  {
			  for( i =1000 ;i<maxWaitVal ; i=i+2000)
				{
					Thread.sleep(i);
					getElementText (locator, by,elementType).startsWith(expectedVal);
					Thread.sleep(2000);
					break;
				}
			  Reports.add("Pass","Webelement "+locator+" with expected value "+expectedVal+" should get loaded within "+maxWaitVal+" msec",
							"Webelement "+locator+" with expected value "+expectedVal+" is loaded in"+i+" sec", 
							LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		  }
		  catch(Exception e)
		  {
			  Reports.add("Fail","Webelement "+locator+" with expected value "+expectedVal+" should get loaded within "+maxWaitVal+" msec",
					  e.getMessage(),LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		  }	  
	  }
		  
	  /**
	  * @Method_Name :assertTrue
	  * Method to validate assert value - Generic function to check assert for a certain condition 
	  * @param condition :condition user want to check
	  * @return void
	  * @throws AssertionError
	  * @author Payel
	  */
	  public void assertTrue (boolean condition) throws AssertionError
	  {
		  try
		  {
			  if(condition == true)
			  {
				  Reports.add("Pass","Condition should be a valid one","Condition is true", 
					  LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			  }
			  else
			  {
				  Reports.add("Fail","Condition should be a valid one","Condition is false", 
						  LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			  }
		  }
		  catch(Exception e)
		  {
			  e.printStackTrace();
			  Reports.add("Fail"," should be a valid one",e.getMessage(), 
					  LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		  }
	  }
	
	  
	  /**
	  * @Method_Name :assertFalse
	  * Method to validate assert value - Generic function to check assert for a certain condition 
	  * @param condition :condition user want to check
	  * @return void
	  * @throws AssertionError
	  * @author Payel
	  */
	  public void assertFalse (boolean condition) throws AssertionError
	  {
		  try
		  {
			  if(!condition)
			  {
			      Reports.add("Pass","Condition should be a false one","Condition is false", 
					   LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			  }
			  else
			  {
				  Reports.add("Fail","Condition should be a false one","Condition is true", 
						  LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			  }
		  }
		  catch(Exception e)
		  {
			  e.printStackTrace();
			  Reports.add("Fail","Condition should be a false one",e.getMessage(), 
					  LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		  }
	  }
	
	
	  /**
	  * @Method_Name :assertEquals
	  * Method to validate assert equals - Generic function to check actual message is equal to expected message 
	  * @param strExp :Expected message application should display
	  * @param strAct :Actual message application is displaying during execution
	  * @return void
	  * @throws AssertionError
	  * @author Payel
	  */	
	  public void assertEquals (String strExp, String strAct) throws AssertionError
	  {
		  try
		  {
			 if(strExp.equalsIgnoreCase(strAct))
			 {
				 Reports.add("Pass","Strings "+strExp+","+strAct+" should matched","Strings "+strExp+","+strAct+" are matching", 
					  LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			 }
			 else
			 {
				 Reports.add("Fail","Strings "+strExp+","+strAct+" should matched","Strings are not matching",
						  LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			 }
		  }
		  catch(Exception e)
		  {
			  e.printStackTrace();
			  Reports.add("Fail","Strings "+strExp+","+strAct+" should matched",e.getMessage(),
					  LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		  }
	  }
	  
	
	  /**
	  * @Method_Name :verifytitle
	  * Method to validate title of a page- Generic function to check actual page title is equal to expected title 
	  * @param strExp :Expected message application should display
	  * @param strAct :Actual message application is displaying during execution
	  * @return void
	  * @throws AssertionError
	  * @author Payel
	  */
	  public void verifyTitle (String strExp) throws AssertionError
	  {
		  try
		  {
			  String strAct = driver.getTitle();
			  if(strExp.equalsIgnoreCase(strAct))
				 {
					 Reports.add("Pass",strExp+","+strAct,"Strings should matched","Strings are matching", 
						  LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				 }
				 else
				 {
					 Reports.add("Fail",strExp+","+strAct,"Strings should matched","Strings are not matching",
							  LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				 }
		  }
		  catch(Exception e)
		  {
			  e.printStackTrace();
			  Reports.add("Fail","Strings should matched",e.getMessage(),LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		  }
	  }
	  
	
	  /**
	  * @Method_Name :verifySuccessMessageBoolean
	  * Method  to validate expected and actual message are matching
	  *	Method accepts Expected Message and Actual Message as parameters 
	  * and compares them to return Boolean True or False
	  * @param strExp :Expected message application should display
	  * @param strAct :Actual message application is displaying during execution
	  * @return Boolean true if expected message and actual message matches
	  * @throws AssertionError
	  * @author Arindam
	  */
	  public boolean verifySuccessMessageBoolean (String strExp, String strAct) throws AssertionError
	  {
		  boolean condition =false;
		  try
		  {
			  if(strExp.equalsIgnoreCase(strAct))
				 {
				  	 condition = true;
					 Reports.add("Pass","Strings "+strExp+","+strAct+" should matched","Strings "+strExp+","+strAct+"are matching", 
						  LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				 }
			  else
			  {
				  condition = false;
				  Reports.add("Fail","Strings "+strExp+","+strAct+" should matched","Strings are not matching",
						  LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			  }
		  }
		  catch(Exception e)
		  {
			  condition = false;
			  Reports.add("Fail","Strings "+strExp+","+strAct+" should matched",e.getMessage(),
					  LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		  }
		  return condition;
	  }

	
	  /**
	  * @Method_Name :verifyAlertMessageDisplayed
	  * Method  to validate expected and actual message in an alert
	  * window or popup box are matching.Method accepts Expected Message 
	  * and validates it with message in currently active alert window
	  * @param strExp :Expected message application should display  
	  * @return void
	  * @author Arindam
	  */
	  public void verifyAlertMessageDisplayed(String strExp )
	  {
		  Alert alertWindow = null;
		  String strAct = null;
		  try
		  {
			  if (ExpectedConditions.alertIsPresent() != null)
			  {
				  alertWindow = driver.switchTo().alert();
				  strAct = alertWindow.getText();
			  
				  if(verifySuccessMessageBoolean(strExp, strAct))
				  {
					  alertWindow.accept();
					  Reports.add("Pass","Strings should matched","Strings are matching", 
						  LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				  }
				  else
				  {
					  alertWindow.dismiss();
					  Reports.add("Pass","Strings should matched","Strings are not matching", 
							  LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				  }
			  }
		  }
		  catch(Exception e)
		  {
			  Reports.add("Fail","Strings "+strExp+","+strAct+" should matched",e.getMessage(),
					  LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		  }
	  }
	  
	  /**
		  * @Method_Name :enterTextPopUp
		  * Method  to enter text in an alert window or popup box 
		  * .Method identify pop up and enter text in textbox present in pop up
		  * @param strExp :Expected message application should display  
		  * @return void
		  * @author Arindam
		  */
		  public void enterTextPopUp(String locator,String by,String value )
		  {
			  Alert alertWindow = null;
			  String strAct = null;
			  try
			  {
				  if (ExpectedConditions.alertIsPresent() != null)
				  {
					  alertWindow = driver.switchTo().alert();
					  
					  if(isElementDisplayed(locator, by))
					  {
						  setTextboxValue(locator, by, value);						  
						  Reports.add("Pass","String should enter in popUp","String entered in popup", 
							  LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
					  }
				  }
			  }
			  catch(Exception e)
			  {
				  Reports.add("Fail","String should enter in popUp",e.getMessage(),
						  LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			  }
		  }
	
	  /**
	  * @Method_Name :verifyMessageDisplayed
	  * Wrapper Method  to validate expected and actual message are 
	  * matching for an web element message.Method accepts Locator and 
	  * Locator Type of actual message element, Element Type (Web Textbox, 
	  * Link, Dropdown list etc.) , Expected Message, Screen Capture File 
	  * Name, File Save Location as test input parameters.Method fetches actual 
	  * application message verifies if matching with expected message and return 
	  * Boolean True or False
	  * @param strExp :Expected message application should display
	  * @param locator :Locator use to find the web element
	  * @param locatorType: type of web element
	  * @param by :Use to identify Locator type
	  * @return void
	  * @author Arindam
	  */
	  public void verifyMessageDisplayed(String locator,String by,String strExp,String locatorType) 
	  {
		  	 String strAct = null;
			 try
			 {
				 strAct = getElementText(locator,by,locatorType);
				 if(verifySuccessMessageBoolean(strExp, strAct)==true)
				 {
					 //System.out.println("message verified");
					 Reports.add("Pass","Stings "+strExp+","+strAct+" should matched","Strings "+strExp+","+strAct+" are matching",
							 LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				 }
			 }
			 catch(Exception e)
			 {
				 Reports.add("Fail","Stings "+strExp+","+strAct+" should matched",e.getMessage(),
						 LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			 }
	  }
	 
	 	
	  /**
	  * @Method_Name :verifyBrokenLinkBeforeClick
	  * Method to validate whether web link  is broken or not 
	  * as per the Status Code of HTTP Requests on that link 
	  * and return Boolean status.Method accepts Locator and 
	  * Locator Type of the link
	  * @param locator :Locator use to find the web element
	  * @param driver :WebDriver
	  * @param by :Use to identify Locator type
	  * @return Boolean - true or false
	  * @throws IOException,MalformedURLException
	  * @author Arindam
	  */
	  public boolean verifyBrokenLinkBeforeClick (String locator,String by) throws IOException,MalformedURLException
	  {
		  int statusCode = 0 ;
		  boolean status = false;
		  String url = null;
		  try
		  {
			  if (isElementPresent(locator, by))
			  {
				  WebElement wbElement = getElement(locator, by);
				  url = wbElement.getAttribute("href");
				  URL webSite = new URL(url);
				  HttpURLConnection httpConn =  (HttpURLConnection)webSite.openConnection();
				  httpConn.setRequestMethod("GET");
				  httpConn.connect();
				  statusCode = httpConn.getResponseCode();
				  if(statusCode == 404 ||statusCode == 500 || statusCode == 502 || statusCode == 503 ) 
				  {
					  status = false;
					  Reports.add("Fail","Link "+url+" under webelement "+locator+" should work fine",
							  "Link "+url+" under webelement "+locator+" is broken ", 
							  LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				  }
				  else
				  {
					  status = true;
					  //Reports.add("Pass","Link "+url+" under webelement "+locator+" should work fine",
							  //"Link "+url+" under webelement "+locator+" is not broken ", 
							  //LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				  }
			  }
			  else
			  {
				  status = false;
				  //Reports.add("Pass","Link "+url+" under webelement "+locator+" should work fine",
						  //"Link "+url+" under webelement "+locator+" is not present ",  
						  //LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			  }
		  }
		  catch (Exception e) 
		  {
				  e.printStackTrace();
				  Reports.add("Fail","Link "+url+" under webelement "+locator+" should work fine",e.getMessage(), 
						  LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		  }
		  return status;
	  }
	
	
	/**
	 * @Method_Name: isElementPresent 
	 * Method to verify if expected Web Element is 
	 * displayed and enabled for input action.Method checks
	 * presence and enabled status of matching element  as per 
	 * Locator and Locator Type provided as method argument and 
	 * return either True or False
	 * @param locator :Element locator
	 * @param by : Use to identify locator
	 * @return Boolean true: if element is present, false: if element is not present
	 * @author Shailesh
	 */
	  public boolean isElementPresent(String locator,String by) 
	  {
		  boolean condition = false;
		  try 
		  {
				  WebElement WbElement=  getElement(locator,by);				  
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
					  LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		  }
		  return condition;
	  }
	  
	  
	     /**
		 * @Method_Name: isElementDisplayed 
		 * Method to verify if expected Web Element 
		 * is displayed.Method checks presence of matching 
		 * element  as per Locator and Locator Type provided 
		 * as method argument and return either True or False
		 * @param locator :Element locator
		 * @param by : Use to identify locator
		 * @return Boolean true: if element is present, false: if element is not present
		 * @author Arindam
		 */
		  public boolean isElementDisplayed(String locator,String by) 
		  {
			  boolean condition = false;
			  try 
			  {
					  WebElement WbElement=  getElement(locator,by);
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
						  LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			  }
			  return condition;
		  }
		  
		  
	  /**
	  * @Method_Name :setTextboxValue
	  * Wrapper method to set value in Web Textbox
	  * Method checks presence of Textbox element per 
	  * matching Locator, Locator Type provided as test 
	  * input and sets the textbox value with method argument 
	  * value �Value�
	  * @param locator :Element locator
	  * @param driver :WebDriver
	  * @param by : Use to identify locator
	  * @param value : Text user wants to enter
	  * @return Void
	  * @author Arindam
	  */
	  public void setTextboxValue(String locator,String by,String value)
	  {
		  try
		  {
			  if (isElementPresent(locator,by))
			  {
				  WebElement wbElement = getElement(locator,by);
				  wbElement.click();
				  Thread.sleep(2000);
				  wbElement.clear();
				  wbElement.sendKeys(value);
				  logger.info("The value"+value+"sent to the textbox:"+locator);
				  Reports.add("Pass","Text "+value+" should enter in the textbox "+locator,"Text "+value+" has been entered in text box "+locator, 
		  			LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			  }
		  }
		  catch(Exception e)
		  {
			  Reports.add("Fail","Text "+value+" should enter in the textbox "+locator,e.getMessage(), 
					  LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		  }
	  }
	  
	  
	  /**
	  * @Method_Name :clickRadioButton
	  * Wrapper method to click / select desired Web Radio Button
	  * Method checks presence of radio button element and it�s attached 
	  * text as per matching Locator, Locator Type, Attached Text provided 
	  * as test input.Method clicks / selects the Radio Button if not already selected
	  * @param locator :Element locator
	  * @param by : Use to identify locator
	  * @param attachedText : Text attached with the radio button that user want to select
	  * @return Void
	  * @author Arindam
	  */
	  public void clickRadioButton(String locator,String by,String attachedText)
	  {
		  String attrVal = null;
		  try
		  {
			  if (isElementPresent(locator,by))
			  {
				  WebElement wbElement = getElement(locator,by);
				  attrVal = getElementPropertyValue(locator,by);
		  	      if  (attrVal.equals(attachedText))
		  	      {
					  if(verifyRadioButtonIsSelected(locator,by,attachedText) == false)
					  {
						    wbElement.click();
		  			  		Reports.add("Pass","Radiobutton "+locator+"with value "+attachedText+" should get selected",
		  					"Radiobutton "+locator+" is selected",
		  					LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
					  }
				  }
			  }
		  }
		  catch(Exception e)
		  {
			  Reports.add("Fail","Radiobutton "+locator+"with value "+attachedText+"  should get selected",
				  e.getMessage(),LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		  }
	  }
	  
	  
	  /**
	  * @Method_Name :clickWebLink
	  * Wrapper method to click on Web Link. Method checks 
	  * presence of web link element as per matching Locator, 
	  * Locator Type provided as test input then validates 
	  * whether the link is not broken  and clicks  on Web 
	  * Link if it is not broken
	  * @param locator :Element locator
	  * @param by : Use to identify locator
	  * @return Void
	  * @throws MalformedURLException, IOException
	  * @author Arindam
	  */
	  public void clickWebLink(String locator,String by) throws MalformedURLException, IOException
	  {
		  try
		  {
			  if (isElementPresent(locator,by))
			  {
				  WebElement wbElement = getElement(locator,by);
				  wbElement.click();
				  Reports.add("Pass","WebLink "+locator+" should get clicked using "+by,"WebLink "+locator+" is clicked using "+by, 
				  LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			  }
		  }
		  catch(Exception e)
		  {
			  Reports.add("Fail","WebLink "+locator+" should get clicked using"+by,e.getMessage(), 
					LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		  }
	  }
	  
	  
	  /**
	  * @Method_Name :clickButton
	  * Wrapper method to click on Web Button
	  * Method checks presence of Web button element 
	  * per matching Locator, Locator Type provided as 
	  * test input and clicks on it if found
	  * @param locator :Element locator
	  * @param by : Use to identify locator
	  * @return Void
	  * @author Arindam
	  */
	  public void clickButton(String locator,String by) 
	  {
		  try
		  {
			  if (isElementPresent(locator,by))
			  {
				  WebElement wbElement = getElement(locator,by);
				  wbElement.click();
				  Reports.add("Pass","Button "+locator+" should get clicked using "+by,"Button "+locator+" is clicked using "+by, 
					  LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			  }
		  }
		  catch(Exception e)
		  {
			  Reports.add("Fail","Button "+locator+" should get clicked using "+by,e.getMessage(), 
					  LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		  }
	  }
	  
	  
	  /**
	  * @Method_Name :selectCheckBox
	  * Wrapper method to check/ set desired Web Checkbox
	  * Method checks presence of check box element and it�s 
	  * attached text as per matching Locator, Locator Type, 
	  * Attached Text provided as test input.Method checks/ 
	  * selects the Checkbox if not already selected
	  * @param locator :Element locator
	  * @param by : Use to identify locator
	  * @param attachedText : Attribute of the web element
	  * @return Void
	  * @author Arindam
	  */
	  public void selectCheckBox(String locator,String by,String attachedText)
	  {
		  String attrVal = null; 
		  	try
		  	{
		 	  if (isElementPresent(locator,by))
			  {
				  WebElement wbElement = getElement(locator,by);
				  attrVal = getElementPropertyValue(locator,by);		  	  
		  		  if(attrVal.equals(attachedText))
		  		  {
		  			  if(verifyCheckBoxIsChecked(locator,by,attrVal) == false)
		  			  {
		  				  wbElement.click();
		  				  Reports.add("Pass","Check box "+locator+" with value "+attachedText+"should get checked using "+by,
		  						  "Check box "+locator+" with value "+attachedText+" has been checked using "+by, 
		  						  LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		  			  }
		  			  else
		  			  {
		  				  Reports.add("Info","Check box "+locator+" with value "+attachedText+"should get checked using "+by,
		  						  "Check box "+locator+" with value "+attachedText+" is already checked", 
		  						  LogAs.INFO, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		  			  }
		  		  }
		  	  }
		  	}
		  	catch(Exception e)
		  	{
			  	  Reports.add("Fail","Check box "+locator+" with value "+attachedText+"should get checked using "+by,
			  			  e.getMessage(),LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		  	}
		  }
	  
	  
	      /**
		  * @Method_Name :selectCheckBox
		  * @Description :Wrapper for selecting check box - Pre-condition:  IsElementPresent
		  *	Common Method that accepts Locator, Type to find element and selecting the correct checkbox 
		  * @param locator :Element locator
		  * @param by : Use to identify locator
		  * @param attachedText : Attribute of the web element
		  * @return Void
		  * @author Arindam
		  */
		 /* public void selectCheckBoxGroup(String locator,String by,String[] attachedText)
		  {
			  String[] attrVal = null; 
			  List<WebElement> wbElement = null;
			  try
			  {
				   if (isElementPresent(locator,by))
				   {
					   	wbElement = (List<WebElement>) getElement(locator,by);
						for(int i = 0;i < wbElement.size() ; i++)
						{
						   attrVal[i] = getElementPropertyValue(locator,by);		 	  
						   if(attrVal[i].equals(attachedText[i]))
						   {
							  if(verifyCheckBoxIsChecked(locator,by,attrVal[i]) == false)
							  {
								  ((WebElement) wbElement).click();
								  Reports.add("Pass",locator+","+by+","+attachedText,"Check boxes should get checked",
										  "Check boxes has been checked",LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
							  }
							  else
							  {
									  //System.out.println("Check box is already checked");
								  Reports.add("Info",locator+","+by+","+attachedText,"Check boxes should get checked",
										  "Check boxes is already checked",LogAs.INFO, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
							  }
						  }
						  else
						  {
								  //System.out.println("Check box with the expected attribute not found");
							  Reports.add("Fail",locator+","+by+","+attachedText,"Check boxes should get checked","Check boxes is not selected", 
				  				  LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
						  }
				       }
						
					 }
					 else
					 {
						  //System.out.println("Check box not found");
					   Reports.add("Fail",locator+","+by+","+attachedText,"Check boxes should get checked","Check boxes is not present", 
				  	 	  LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
					 }
				  
			  }
			  catch(Exception e)
			  {
				  Reports.add("Fail",locator+","+by+","+attachedText,"Check boxes should get checked",e.getMessage(), 
			  			  LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			  }
				  
		  }*/
	  
	  
		  
		  
	  /**
	  * @Method_Name :verifyRadioButtonIsSelected
	  * Method  checks if Radio Button is selected.Method checks 
	  * presence of radio button element and it�s attached text as 
	  * per matching Locator, Locator Type, Attached Text provided 
	  * as test input and verifies whether it is in selected state 
	  * and returns Ture or False accordingly
	  * @param locator :Element locator
	  * @param by : Use to identify locator
	  * @param attachedText : Attribute of the web element
	  * @return Boolean :true if radio button is selected , false if radio button is not selected 
	  * @author Arindam
	  */
	  public boolean verifyRadioButtonIsSelected(String locator,String by,String attachedText)
	  {
		  String elementText = null;
		  boolean condition = false ;
		  try
		  {
			  if (isElementPresent(locator,by))
			  {
				  WebElement wbElement = getElement(locator,by);
				  elementText = getElementPropertyValue(locator,by);
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
	  				  LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		  }  
		  return condition;
	  }
	  
	  
	  /**
	  * @Method_Name :verifyCheckBoxIsChecked
	  * Method  checks if Checkbox is already checked
	  * Method checks presence of Checkbox element and it�s 
	  * attached text as per matching Locator, Locator Type, 
	  * Attached Text provided as test input and verifies whether 
	  * it is in checked state and returns Ture or False accordingly
	  * @param locator :Element locator
	  * @param by : Use to identify locator
	  * @param attachedText : Attribute of the web element
	  * @return Boolean :true if check box is selected , false if check box is not selected 
	  * @author Arindam
	  */
	  public boolean verifyCheckBoxIsChecked(String locator,String by,String attachedText)
	  {
		  String elementText = null;
		  boolean condition = false;
		  try
		  {
			  if (isElementPresent(locator,by))
			  {
				  WebElement wbElement = getElement(locator,by);
				  elementText = getElementPropertyValue(locator,by);
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
					  e.getMessage(),LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		  }
		  	  return condition;
	}
   
	   /**
	   * @Method_Name : placeCursorOnElement
       * Wrapper method for WebDriver method moveToElement 
       * to set focus on a specific web element.Method checks 
       * presence of element per matching Locator, Locator Type 
       * provided as test input and places cursor to set focus on the element
       * @param locator :Element locator
	   * @param by : Use to identify locator
	   * @throws NoSuchElementException
	   * @return void
       * @author shailesh
       */
      public  void placeCursorOnElement(String locator,String by) throws NoSuchElementException
      {
      
    	  WebElement WbElement=null;
          try
          {
        	  if (isElementDisplayed(locator, by) )
        	  {
        		  WbElement= getElement(locator,by);
        		  new Actions(driver).moveToElement(WbElement).perform();
        	
        		  Reports.add("Pass","Cursor should place on webelement "+locator,"Cursor has been placed on webelement "+locator, 
          					  LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
        	  }
          }
      	  catch (Exception e)
          {
          	  Reports.add("Fail","Cursor should place on element "+locator,e.getMessage(), 
          		  LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
      	  }
      }
       
  	  	
  	/**
	 * @Method_Name: selectDropDownValueByType
	 * Wrapper method to select and set specified value 
	 * for Dropdown list selection.Method checks presence of 
	 * dropdown list element as per matching Locator, Locator 
	 * Type provided as test input and selects dropdown list 
	 * value using either Index, Value or Visible Text as per 
	 * Value and Select Type (index, visibletext or value etc.) provided as test input
	 * @param locator :Element locator
	 * @param by : Use to identify locator
	 * @param Value: Value of Index or Visible Text or Value which user wants to select
	 * @param selectType : Index or Visible Text or Value using which user wants to select
	 * @return Void
	 * @author shailesh
	 */
	public  void selectDropDownValueByType(String locator,String by,String value,String selectType) 
	{
		WebElement WbElement=null;
		try
		{
			if(selectType.equalsIgnoreCase("index"))
			{
				int IndexNum = Integer.valueOf(value);
				if(isElementPresent(locator, by))
				{		
					WbElement=  getElement(locator,by);
					new Select(WbElement).selectByIndex(IndexNum);
					Reports.add("Pass","List box  "+locator+" value "+value+" should get selected using "+selectType,
							"List box "+locator+" value "+value+"is selected using "+selectType,
							LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				}
			}
			else if(selectType.equalsIgnoreCase("visibletext"))
			{
				if(isElementPresent(locator, by))
				{	
					WbElement=  getElement(locator,by);
					new Select(WbElement).selectByVisibleText(value);
					Reports.add("Pass","List box "+locator+" value "+value+" should get selected using "+selectType,
						"List box "+locator+" value "+value+" is selected using "+selectType,
						LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				}
			}
			else if(selectType.equalsIgnoreCase("value"))
			{
				if(isElementPresent(locator, by))
				{	
					WbElement=  getElement(locator,by);
					new Select(WbElement).selectByValue(value);
					Reports.add("Pass","List box "+locator+" value "+value+" should get selected using "+selectType,
						"List box "+locator+" value "+value+" is selected using "+selectType,
						LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				}
			}
		}
		catch(Exception e)
		{
			Reports.add("Fail","List box "+locator+" value "+value+" should get selected using "+selectType,
					e.getMessage(),LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
	}
	
	
	/**
	 * Method Name: selectDropDownOptionByVisibleText
	 * 
	 * Description: This function selects the drop down by visible text
	 * 
	 * @param driver
	 *            : WebDriver instance
	 * @param by
	 *            :Element locator
	 * @param dropDownValue
	 *            : value of the drop down from data XML
	 *            @author shailesh
	 */
	/*public  void selectDropDownOptionByVisibleText(String locator,String LocatorType, String dropDownValue)
	{
		WebElement WbElement=null;
		
		if(isElementPresent(locator, LocatorType))
		{	
		     WbElement=  getElement(locator,LocatorType);
			new Select(WbElement).selectByVisibleText(dropDownValue);
			Reports.add("Pass","List box "+locator+" value should get selected using text "+dropDownValue,
					"List box "+locator+" value is selected using value "+dropDownValue, 
					LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		else
		{
			Reports.add("Fail","List box "+locator+" value should get selected using text "+dropDownValue,
					"Unable to select "+dropDownValue+" from listbox "+locator, 
					LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
	}*/
	

	
	/**
	 * Method Name: selectDropDownOptionByValue
	 * 
	 * Description: This function selects the drop down by value
	 * 
	 * @param driver
	 *            : WebDriver instance
	 * @param by
	 *            :Element locator
	 * @param dropDownValue
	 *            : value of the drop down from data XML
	 * @author shailesh
	 */
	/*public  void selectDropDownOptionByValue(String locator,String LocatorType, String dropDownValue)
	{
		WebElement WbElement=null;
		
		if(isElementPresent(locator, LocatorType))
		{	
		    WbElement=  getElement(locator,LocatorType);
			new Select(WbElement).selectByValue(dropDownValue);
			Reports.add("Pass","List box "+locator+" should get selected using value"+dropDownValue,
					"List box "+locator+" is selected using value "+dropDownValue, 
					LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		else
		{
			Reports.add("Pass","List box "+locator+" should get selected using value"+dropDownValue,
					"Unable to select "+dropDownValue+" from listbox "+locator, 
					LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
	}*/
	
	
    /**
    * @Method_Name: killBrowser
    * Method  to close WebDriver instance
    * @return void
    * @throws InterruptedException
	* @author Arindam
	*/
	public  void killBrowser() throws InterruptedException
	{
		try 
		{
			driver.close();
			//driver.quit();
			Thread.sleep(8000);
			Reports.add("Pass","Webdriver should get closed","Webdriver is closed", 
					LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			Reports.add("Fail","Webdriver should get closed",e.getMessage(), 
					LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
	}
	
	
	 /**
	 * @Method_Name: DragAndDrop
	 * Method  to drag and drop an element from Source to Destination 
	 * frame using Webdriver Action class
	 * @param srclocator : Source Element locator
	 * @param srcLocatorType : Use to identify source locator
	 * @param dstnlocator :Destination Element locator
	 * @param dstnLocatorType : Use to identify destination locator
	 * @return void
	 * @author shailesh
	 */
	public  void DragAndDrop(String srcLocator,String srcLocatorType, String DestnLocator,String DestnLocatorType)
	{
		WebElement WbElementSrc=null;
		WebElement WbElementDestn=null;
		try
		{
			if(isElementPresent(srcLocator, srcLocatorType) &&  isElementPresent(DestnLocator, DestnLocatorType) )
			{	
				Actions act = new Actions(driver);
				WbElementSrc =  getElement(srcLocator,srcLocatorType);
				WbElementDestn= getElement(DestnLocator,DestnLocatorType);
		    
				act.dragAndDrop(WbElementSrc, WbElementDestn).perform();
				Reports.add("Pass",	"File should get dragged from "+srcLocator+" to "+DestnLocatorType,
						"File has been dragged from "+srcLocator+" to "+DestnLocatorType,
						LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));			
			}
		}
		catch(Exception e)
		{
			Reports.add("Fail","File should get dragged from "+srcLocator+" to "+DestnLocatorType,e.getMessage(),
					LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
	}
	
	
	/**
	 * Method Name: GetToolTipText
	 * Method  fetches and returns �Title� property of 
	 * web element.Method checks presence of element per 
	 * matching Locator, Locator Type provided as test input 
	 * and returns the element attribute �Title�
	 * @param locator :Element locator
	 * @param by : Use to identify locator
	 * @return : String
	 * @author shailesh
	 */
	public  String GetToolTipText( String locator,String by)
	{
		WebElement WbElement=null;
		String titleText=null;
		try
		{
			if(isElementPresent(locator, by))
			{	
				WbElement=  getElement(locator,by);
				titleText= WbElement.getAttribute("title");
				Reports.add("Pass","Tool tip of "+locator+"should get captured using "+by,"Tool tip of "+locator+" is "+titleText, 
					LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}
		}
		catch(Exception e)
		{
			Reports.add("Fail","Tool tip of "+locator+"should get captured using "+by,e.getMessage(), 
					LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		return titleText;
	}
	
	
	/**
	* @Method_Name :verifyXMLNodeValue
	* Method  validates if XML Node value is correct
	* Method takes XML File location, XML Node name, 
	* Expected value of the node and validates if the 
	* present value of XML Node matches with expected 
	* value and returns True or False accordingly
	* @param xmlNodeName : XML node name of whose value you want to validate 
	* @param fileLocation : Location where user wants to save the downloading file
	* @param expValue : Expected value of the XML node
	* @return Boolean
	* @author Arindam
	*/
	/*public boolean verifyXMLNodeValue (String fileLocation , String xmlNodeName, String expValue)
	{
		boolean condition = false;
		try
		{
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = (Document) db.parse(fileLocation);
            doc.getDocumentElement().normalize();

            NodeList nodelist = (NodeList) doc.getElementsByTagName(xmlNodeName);
            
            for(int i=0;i<nodelist.getLength();i++)
            {
                Node node=nodelist.item(i);
                String valueOfTag=node.getAttributes().getNamedItem(xmlNodeName).getNodeValue();
                
                if(valueOfTag.equalsIgnoreCase(xmlNodeName))
                {
                    if(node.hasChildNodes())
                    {
                        NodeList childNList=node.getChildNodes();
                        for(int j = 0; j < childNList.getLength();j++)
                        {
                            Node childNode = childNList.item(j);
                            if(childNode.getNodeType() == 1) 
                            {
                                String nvalue = childNode.getAttributes().getNamedItem(xmlNodeName).getNodeValue();
                                assertTrue(nvalue.equalsIgnoreCase(expValue));
                                condition = true;
                                Reports.add("Pass","Node value "+xmlNodeName+" should get matched with "+expValue,
                                		"Node value of "+xmlNodeName+" is "+nvalue , 
                                		LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
                            }
                        }
                    }
                }
            }
		}
		catch(Exception e)
		{
			Reports.add("Fail","Node value "+xmlNodeName+" should get matched with "+expValue,
					e.getMessage() ,LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		
	    return condition;
	}
	*/
	
   /**
 	* @Method_Name: pressEnter
    * Method  to accomplish �Enter� key press in keyboard using Action class 
    * @return void        
 	* @author shailesh
 	*/
 	public void PressEnterViaAction() 
 	{
 		try
 		{
 			Actions action = new Actions(driver);
 			action.sendKeys(Keys.ENTER).build().perform();
 			//Reports.add("Pass","Enter key should get pressed using Action","Enter key pressed using Action" , 
 					//LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
 		}
 		catch(Exception e)
 		{
 			e.printStackTrace();
 			Reports.add("Fail","Enter key should get pressed using Action",e.getMessage() , 
 					LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
 		}
 	}
 	

 	/**
 	 * @Method_Name: PressEscViaAction
 	 * Method  to accomplish �Esc� key press in keyboard using Action class
     * @return void
 	 * @author shailesh
 	 */
 	public void PressESCViaAction() 
 	{
 		try
 		{
 			Actions action = new Actions(driver);
 			action.sendKeys(Keys.ESCAPE).build().perform();
 			//Reports.add("Pass","Escape key should get press using Action","Escape key pressed using Action" , 
 					//LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
 		}
 		catch(Exception e)
 		{
 			e.printStackTrace();
 			Reports.add("Fail","Escape key should get press using Action",e.getMessage() , 
 					LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));	
 		}
 	}
 	
 	
   /**
 	 * @Method_Name: PressTABViaAction
 	 * Method  to accomplish �Tab� key press in keyboard using Action class
     * @return void
 	 * @author shailesh
 	 */
 	public void PressTABViaAction() 
 	{
 		try
 		{
 			Actions action = new Actions(driver);
 			action.sendKeys(Keys.TAB).build().perform();
 			//Reports.add("Pass","Tab key should get press using Action","Tab key pressed using Action" ,
 					//LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
 		}
 		catch(Exception e)
 		{
 			e.printStackTrace();
 			Reports.add("Fail","Tab key should get press using Action",e.getMessage() , 
 					LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));	
 		}
 	}
 	
 	
   /**
 	 * @Method_Name: PressPAGEUPViaAction
 	 * Method  to accomplish �Page Up� key press in keyboard using Action class
     * @return void      
 	 * @author shailesh
 	 */
 	public void PressPAGEUPViaAction() 
 	{
 		try
 		{
 			Actions action = new Actions(driver);
 			action.sendKeys(Keys.PAGE_UP).build().perform();
 			//Reports.add("Pass","PageUp key should get press using Action","PageUP key pressed using Action" , 
 					//LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
 		}
 		catch(Exception e)
 		{
 			e.printStackTrace();
 			Reports.add("Fail","PageUp key should get press using Action",e.getMessage() , 
 					LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));	
 		}
 	}
	
 	
   /**
 	 * @Method_Name: PressPAGEDOWNViaAction
 	 * Method  to accomplish �Page Down� key press in keyboard using Action class
     * @return void          
 	 * @author shailesh
 	 */
 	public void PressPAGEDOWNViaAction() 
 	{
 		try
 		{
 			Actions action = new Actions(driver);
 			action.sendKeys(Keys.PAGE_DOWN).build().perform();
 			//Reports.add("Pass","PageDown key should get press using Action","PageDown key pressed using Action" , 
 					//LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
 		}
 		catch(Exception e)
 		{
 			e.printStackTrace();
 			Reports.add("Fail","PageDown key should get press using Action",e.getMessage() , 
 					LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
 		}
 	}
 	
 	
     /**
 	 * @Method_Name: PressControlKeyViaAction
 	 * Method to accomplish �Control� key press in keyboard using Action class
     * @return void        
 	 * @author shailesh
 	 */
 	public void PressControlKeyViaAction()
 	{
 		try
 		{
 			Actions action = new Actions(driver);
 			action.sendKeys(Keys.CONTROL).build().perform();
 			//Reports.add("Pass","Control key should get press using Action","Control key pressed using Action" , 
 					//LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
 		}
 		catch(Exception e)
 		{
 			e.printStackTrace();
 			Reports.add("Fail","Control key should get press using Action",e.getMessage() , 
 					LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
 		}
 	}
 	
 	
     /**
 	 * @Method_Name: PressSHIFTKeyViaAction
 	 * Method  to accomplish �Shift� key press in keyboard using Action class
     * @return void          
 	 * @author shailesh
 	 */
 	public void PressSHIFTKeyViaAction()
 	{
 		try
 		{
 			Actions action = new Actions(driver);
 			action.sendKeys(Keys.SHIFT).build().perform();
 			//Reports.add("Pass","Shift key should get press using Action","Shift key pressed using Action" , 
 					//LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
 		}
 		catch(Exception e)
 		{
 			e.printStackTrace();
 			Reports.add("Fail","Shift key should get press using Action",e.getMessage() , 
 					LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
 		}
 	}
 	
     /**
 	 * @Method_Name: PressBackspaceKeyViaAction
 	 * Method  to accomplish �Backspace� key press in keyboard using Action class
     * @return void           
 	 * @author shailesh
 	 */
 	public void PressBackspaceKeyViaAction() 
 	{
 		try
 		{
 			Actions action = new Actions(driver);
 			action.sendKeys(Keys.BACK_SPACE).build().perform();
 			//Reports.add("Pass","BackSpace key should get press using Action","BackSpace key pressed using Action" , 
 					//LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
 		}
 		catch(Exception e)
 		{
 			e.printStackTrace();
 			Reports.add("Fail","BackSpace key should get press using Action",e.getMessage() , 
 					LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
 		}
 	}
 	
 	
 	/**
	* @Method_Name :uploadFileWithInputTag
	* Method  to accomplish file upload when element has input 
	* tag defined as �File�.Method finds file input web element using 
	* Locator and Locator Type provided as test input and uploads the file 
	* available under specified file location as specified in method argument
	* @param locator :Element locator
	* @param by : Use to identify locator
	* @param fileLocation : Location where user want to save the file
	* @return void
	* @throws InterruptedException
	* @author Payel
	*/
	public void uploadFileWithInputTag(String locator, String by,String fileLocation) throws InterruptedException
	{
		try
		{
			File fileIm=new File(fileLocation);
			String filepath = fileIm.getCanonicalPath() ;
			getElement(locator,by).sendKeys(filepath);
			Reports.add("Pass","File "+locator+" should get uploaded from "+fileLocation,"File "+locator+" uploaded from "+fileLocation , 
				LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Thread.sleep(10000);
		}
        catch (Exception e) 
        {
			Reports.add("Fail","File "+locator+" should get uploaded from "+fileLocation,e.getMessage(), 
					LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
	}
	
	/**
	* @Method_Name :captureSaveScreenshot
	* Method  to capture screen shot of current application under test and store 
	* it as an image file.Method takes �File Save Location� and �File Name To Save� 
	* as argument, captures active screen shot of AUT and save with file name provided 
	* and store in specified file save location
	* @param FileNameToBeSaved : Name of the file by which user wants to save it
	* @param FileSaveLocation : Location where user want to save the file
	* @param driver : WebDriver
	* @return Void
	* @throws IOException
	* @author Payel
	*/
	public void captureSaveScreenshot(String FileSaveLocation ,String FileNameToBeSaved) throws IOException
	{
		try 
		{
	    	File srcFile=((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		
			DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy_hhmmss");
			Date date = new Date();
			String dateTime = dateFormat.format(date);
			File fileIm=new File(FileSaveLocation+"\\"+ FileNameToBeSaved+dateTime+".png");
			FileUtils.copyFile(srcFile, fileIm);
			Reports.add("Pass","Screenshot should get captured and saved in"+FileSaveLocation,
					"Screenshot captured and saved in"+FileSaveLocation , 
					LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			Reports.add("Fail","Screenshot should get captured and saved in"+FileSaveLocation,e.getMessage(), 
					LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
	}
	
	
	/**
	* @Method_Name :waitForAjaxObject
	* Dynamic Wait for Ajax Element- Dynamically wait for ajax element 
	* to set value   (Auto Suggest Dropdown) and proceed to next element 
	* when the property value is successfully set (Uses findLocatorPropertyValue method)
	* @param locator :Locator of the web element
	* @param by :locator used to find the web element
	* @param MaxWaitVal : Maximum time to wait Ajax object to load
	* @param ExpectedVal : Expected String value 
	* @throws NullPointerException,InterruptedException
	* @return Void
	* @author Payel
	*/

	public void waitForAjaxObject(String locator, String by,int maxWaitVal,String expectedVal) 
			throws NullPointerException,InterruptedException
	{
		String actValue = null;
		try 
		{
			for(int i =1000 ;i<maxWaitVal ; i=i+2000)
			{
				Thread.sleep(i);						
				actValue = getDisplayedText(locator, by);
				Thread.sleep(2000);
				if(expectedVal.contains(actValue))		
				{
					Thread.sleep(2000);
					Reports.add("Pass","Script should wait for"+maxWaitVal+" msec for Ajax object "+locator,
							"Expected value "+expectedVal+" entered in Ajax object "+locator,
							LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
					break;
					
				}
				else
				{
					WebElement txtbox = getElement(locator, by);
					txtbox.clear();
					setTextboxValue(locator,by,expectedVal);
					try
					{
						Robot robo = new Robot();
						downKeyPressViaRobot(robo,1);
						pressEnterViaRobot(robo, 1);
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					Thread.sleep(2000);
					if(i >= maxWaitVal)
					{
						Reports.add("Fail","Script should wait for"+maxWaitVal+" msec for Ajax object "+locator,
							"Unable to enter expected value "+expectedVal+" in Ajax object "+locator,
							LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
					}
				}
			}
		}
		catch(Exception e)
		{
			Reports.add("Fail","Script should wait for"+maxWaitVal+" msec for Ajax object "+locator,e.getMessage() , 
						LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
						
		}
	  }

	

	 /**
	  * @Method_Name :dynamicWaitForElement
	  * Method to dynamically wait for page to load and keep checking 
	  * presence of expected page element until defined time out is reached.
	  * Method uses custom polling to check presence of matching element as 
	  * per Locator and Locator Type provided and wait until maximum duration 
	  * provided as method argument is reached
	  * @param locator :Locator use to find the web element
	  * @param by :Use to identify Locator type
	  * @param maxWaitVal :Maximum time a web element took to load
	  * @return Void
	  * @throws StaleElementReferenceException,NullPointerException,InterruptedException
	  * @author Arindam
	  */
	  public void dynamicWaitForElement(String locator, String by,int maxWaitVal) 
			  throws StaleElementReferenceException,NullPointerException,InterruptedException
	  {
		  int i = 0;
		  boolean flag = false;
		  try 
		  {
			  	for(i =1000 ;i<maxWaitVal ; i=i+2000)
				{
					Thread.sleep(i);
					if(isElementDisplayed(locator, by)||isElementPresent(locator, by))
					{
						Reports.add("Pass","Webelement "+locator+" should get loaded in "+maxWaitVal+" msec",
								"Webelement "+locator+" is loaded after "+i+" msec",LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
						flag = true;
						break;
					}
				}
			  	if(flag != true)
			  	{
			  		Reports.add("Fail","Webelement "+locator+" should get loaded in "+maxWaitVal+" msec",
						 "Webelement "+locator+" is not loaded after "+i+" msec",
						 LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			  	}	
		  }
		  catch(Exception e)
		  {
				 Reports.add("Fail","Webelement "+locator+" should get loaded in "+maxWaitVal+" msec",
						 e.getMessage(),LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		  }	  
	  }
	  
	/**
	 * @Method_Name :popUpClick
	 * Method  to accept or dismiss on an expected 
	 * popup window and return the control to main window.
	 * Method  takes user choice (accept or dismiss) and popup 
	 * title as input, checks if active alert window is expected 
	 * alert window and clicks on either �Ok� or �Cancel� as per user choice input
	 * @param Choice : whether accept or dismiss
	 * @param attachedText :user input to verify pop up message
	 * @return Void
	 * @author Arindam
	 */
	  public void popUpClick(String choice,String attachedText)
	  {
		  try
		  {     
			  	WebDriverWait wait = new WebDriverWait(driver, 2);
			  	wait.until(ExpectedConditions.alertIsPresent());
				Alert a = driver.switchTo().alert();
				if(a.getText().contains(attachedText))
				{
					if (choice.equalsIgnoreCase("accept"))
					{
						a.accept();
						Reports.add("Pass","Alert should get accepted",
								"Alert is accepted",LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
					}
					if(choice.equalsIgnoreCase("dismiss"))
					{
						a.dismiss();
						Reports.add("Pass","Alert should get dismissed",
								"Alert is dismissed",LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
					}
				}
		  }
		  catch(Exception e)
		  {
			  Reports.add("Fail","Alert should get accepted/dismissed",
						e.getMessage(),LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		  }
	  }
	  
	  
	     /**
		 * @Method_Name :popUpClickViaWindowHandle
		 * Method  to handle closing multiple popup windows opened 
		 * and returning the control the parent window.Method takes 
		 * Locator and Locator Type as input, clicking on which opens 
		 * multiple windows that needs to be closed
		 * @param locator :Locator use to find the web element
		 * @param by :Use to identify Locator type
		 * @return Void
		 * @author Arindam
		 */
		  public void popUpClickViaWindowHandle(String locator,String by)
		  {
			  try
			  {     
				  	String prntWindow = driver.getWindowHandle();
				  	clickButton(locator, by);
				  	Set subWindows = driver.getWindowHandles();
				  	Iterator itr = subWindows.iterator();
				  	while(itr.hasNext())
				  	{
				  		String popUpHandle = itr.next().toString();
				  		if(!popUpHandle.contains(prntWindow))
				  		{
				  			driver.switchTo().window(popUpHandle).close();
				  		}
				  	}
				  	driver.switchTo().window(prntWindow);
				  	Reports.add("Pass","Popup windows should get accepted/dismissed and return to main window",
							"Popups are closed and control returns to main wondow",LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			  }
			  catch(Exception e)
			  {
				  Reports.add("Fail","Popup windows should get accepted/dismissed and return to main window",
							e.getMessage(),LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			  }
		  }
		  
		      /**
			  * @Method_Name :clickRadioButton
			  * Wrapper method to click / select desired Web Radio Button.
			  * Method checks presence of radio button element as per matching 
			  * Locator, Locator Type provided as test input.Method clicks / selects 
			  * the Radio Button if not already selected
			  * @param locator :Element locator
			  * @param by : Use to identify locator
			  * @return Void
			  * @author Arindam
			  */
			  public void clickRadioButton(String locator,String by)
			  {
				  String attrVal = null;
				  try
				  {
					  if (isElementPresent(locator,by))
					  {
						  WebElement wbElement = getElement(locator,by);
						  attrVal = getElementPropertyValue(locator,by);
						  if(verifyRadioButtonIsSelected(locator,by) == false)
							  {
								    wbElement.click();
				  			  		Reports.add("Pass","Radiobutton "+locator+" should get selected",
				  					"Radiobutton "+locator+" is selected",
				  					LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
							  }
							  else
							  {
								  Reports.add("Info","Radiobutton "+locator+"  should get selected",
				  					"Radiobutton "+locator+" is already selected",
				  					LogAs.INFO, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
							  }
						  }
					  }
				  catch(Exception e)
				  {
					  Reports.add("Fail","Radiobutton "+locator+"  should get selected",
						  e.getMessage(),LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				  }
			  }
			  
			      /**
				  * @Method_Name :verifyRadioButtonIsSelected
				  * Method  checks if Radio Button is selected.Method checks 
				  * presence of radio button element as per
				  * matching Locator, Locator Type provided as test input and 
				  * verifies whether it is in selected state and returns Ture or False accordingly
				  * @param locator :Element locator
				  * @param by : Use to identify locator
				  * @return Boolean :true if radio button is selected , false if radio button is not selected 
				  * @author Arindam
				  */
				  public boolean verifyRadioButtonIsSelected(String locator,String by)
				  {
					  String elementText = null;
					  boolean condition = false ;
					  try
					  {
						  if (isElementPresent(locator,by))
						  {
							  WebElement wbElement = getElement(locator,by);
							  elementText = getElementPropertyValue(locator,by);
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
				  				  LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
					  }  
					  return condition;
				  }
				  
				      /**
					  * @Method_Name :selectCheckBox
					  * Wrapper method to check/ set desired Web Checkbox.Method checks 
					  * presence of check box element as per matching 
					  * Locator, Locator Type provided as test input.Method checks/ 
					  * selects the Checkbox if not already selected
					  * @param locator :Element locator
					  * @param by : Use to identify locator
					  * @return Void
					  * @author Arindam
					  */
					  public void selectCheckBox(String locator,String by)
					  {
						  String attrVal = null; 
						  	try
						  	{
						 	  if (isElementPresent(locator,by))
							  {
								  WebElement wbElement = getElement(locator,by);
								  attrVal = getElementPropertyValue(locator,by);		  	  
						  			  if(verifyCheckBoxIsChecked(locator,by) == false)
						  			  {
						  				  wbElement.click();
						  				  Reports.add("Pass","Check box "+locator+" should get checked using "+by,
						  						  "Check box "+locator+"  has been checked using "+by, 
						  						  LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
						  			  }
						  			  else
						  			  {
						  				  Reports.add("Info","Check box "+locator+" should get checked using "+by,
						  						  "Check box "+locator+" is already checked", 
						  						  LogAs.INFO, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
						  			  }
						  		  }
						  	  }
						  	catch(Exception e)
						  	{
							  	  Reports.add("Fail","Check box "+locator+" should get checked using "+by,
							  			  e.getMessage(),LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
						  	}
						  }
					
					      /**
						  * @Method_Name :verifyCheckBoxIsChecked
						  * Method  checks if Checkbox is already checked
						  * Method checks presence of Checkbox element 
						  * as per matching Locator, Locator Type
						  * as test input and verifies whether it is in checked state 
						  * and returns Ture or False accordingly
						  * @param locator :Element locator
						  * @param by : Use to identify locator
						  * @return Boolean :true if check box is selected , false if check box is not selected 
						  * @author Arindam
						  */
						  public boolean verifyCheckBoxIsChecked(String locator,String by)
						  {
							  String elementText = null;
							  boolean condition = false;
							  try
							  {
								  if (isElementPresent(locator,by))
								  {
									  WebElement wbElement = getElement(locator,by);
									  elementText = getElementPropertyValue(locator,by);
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
										  e.getMessage(),LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
							  }
							  	  return condition;
						}
						
						/**
						* @Method_Name :scrollToElement
						* Method  scroll to the Web Element on which driver
						* is performing some action
						* @param Webelement :web element type
						* @return void 
						* @author Subrat
						*/
						  public static void scrollToElement(WebElement element)
						  {
								try
								{
									((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", element);
								}
								catch(Exception e)
								{
									Reports.add("Fail","Control should move to element"+element,
											  e.getMessage(),LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
								}
						  }
						  
						   /**
							* @Method_Name :moveToElement
							* Method  move to the Web Element on which driver
							* is performing some action
							* @param Webelement :web element type
							* @return void 
							* @author Subrat
							*/
						    public static void moveToElement(WebElement element)
							{
						        try
						        {
								Actions actions = new Actions(driver);
								actions.moveToElement(element);
								actions.perform();
						        }
						        catch(Exception e)
						        {
						        	Reports.add("Fail","Control should move to element"+element,
											  e.getMessage(),LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
						        }
							}
						    
						    /**
							* @Method_Name :fetchWebElementOfWebTable
							* Method to fetch rows of data from a web table and return a list of web element
							* @param Webelement :web element type
							* @return List of web element 
							* @author Subrat
							*/
						    public static List<WebElement> fetchWebElementOfWebTable (String locator,String by)
						    {
								List<WebElement> allRows = getElements(locator,by);
								int size ;
								try
								{
									for (WebElement tr : allRows) 
									{
										List<WebElement> TDs = fetchElementsOfWebTableRow(tr);
										for(WebElement td: TDs)
										{
											td.getText();
										}
									}
									Reports.add("Pass","Driver should fetch data from all rows of web table using "+locator+"and"+by,
											  "Driver fetch data from all rows of web table using "+locator+"and"+by,
											  LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));									
								}
								catch(Exception e)
								{
									Reports.add("Fail","Driver should fetch data from all rows of web table using "+locator+"and"+by,
											  e.getMessage(),LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
								}

								return allRows;
							}
							
						    /**
							* @Method_Name :fetchElementsOfWebTableRow
							* Method to fetch single row of data from a web table and return list of web element
							* @param Webelement :web element type
							* @return List of web element 
							* @author Subrat
							*/
							public static List<WebElement> fetchElementsOfWebTableRow (WebElement rowElement)
							{
								List<WebElement> allRowItems = new ArrayList<WebElement>();
								try
								{
									allRowItems = rowElement.findElements(By.tagName("td"));
								}
								catch(Exception e)
								{
									Reports.add("Fail","Driver should fetch data from a row of web table",
											  e.getMessage(),LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
								}
								return allRowItems;
							}
							
							/**
							* @Method_Name :fetchContentOfWebTable
							* Method to fetch rows of data from a web table and return list of string
							* @param Webelement :web element type
							* @return List of String 
							* @author Subrat
							*/
						    public static List<String> fetchContentOfWebTable (String locator,String by)
						    {
						    	List <String>text = new ArrayList<String>();
								List<WebElement> allRows = getElements(locator,by);
								try
								{
									for (WebElement tr : allRows) 
									{
										List<WebElement> TDs = fetchElementsOfWebTableRow(tr);
										for(WebElement td: TDs)
										{
											text.add(td.getText().toString());
										}
									}
									Reports.add("Pass","Driver should fetch data from all rows of web table using "+locator+"and"+by,
											  "Driver fetch data from all rows of web table using "+locator+"and"+by,
											  LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));									
								}
								catch(Exception e)
								{
									Reports.add("Fail","Driver should fetch data from all rows of web table using "+locator+"and"+by,
											  e.getMessage(),LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
								}

								return text;
							}
							
						    
							/**
							* @Method_Name :datesChronological
							* Method to test whether dates are in chronological order or not
							* @param webElement : list of web elements
							* @return String 
							* @author Subrat
							*/
							public String datesChronological(List<WebElement> webElement)
							{
								String sFinalStatus = "" ;
								try
								{
									int iIterator = 0;
									int iIterator1 = 0;

									int iElementSize = webElement.size();
									SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
									String [] sStatus =  new String[(iElementSize - 1)];//= null;
									for(iIterator = 0; iIterator< (iElementSize - 1); iIterator++)
									{
										String sDate1 = webElement.get(iIterator).getText();
										String sDate2 = webElement.get(iIterator+1).getText();
										Date dDate1 = sdf.parse(sDate1);
										Date dDate2 = sdf.parse(sDate2);
										if(dDate1.compareTo(dDate2)<0)
										{
											sStatus[iIterator] = "true";
										}
										else
										{
											sStatus[iIterator] = "false";
										}
									}
									for(iIterator1 =0; iIterator1<sStatus.length; iIterator1++)
									{
										if(sStatus[iIterator1].equals("true"))
										{
											sFinalStatus = "true";
										}
										else
										{
											sFinalStatus = "false";
										}
									}
								}
								catch (Exception e)
								{
									Reports.add("Fail","Date should be in chronological order",
							    			e.getMessage(),LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
								}
								return sFinalStatus;
							}
							
							/**
							 * @Method_Name :actionBrowserTab
							 * Method  to perform action like clicking web Link,pressing Button,select Radio Button,
							 * select checkbox and fetch web table content and return list of element in new tab
							 * @param locator :Locator use to find the web element
							 * @param by :Use to identify Locator type
							 * @param tabNum : browser tab number 
							 * @param action : action name
							 * @return Void
							 * @author Arindam
							 */
							  public void actionBrowserTab(String locator,String by,int tabNum,String action)
							  {
								  try
								  {     
									  	ArrayList<String> tab = new ArrayList<String>(driver.getWindowHandles());
									  	driver.switchTo().window(tab.get(tabNum));
									  	switch(action)
									  	{
									  		case "link": clickWebLink(locator, by);
									  		    Reports.add("Pass","Action should perform on link "+locator,
													"Action performed on "+locator,LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
									  		break;
									  		case "button": clickButton(locator, by);
									  		    Reports.add("Pass","Action should perform on button "+locator,
													"Action performed on "+locator,LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
									  		break;
									  		case "radio" : clickRadioButton(locator, by);
									  		    Reports.add("Pass","Action should perform on radio "+locator,
													"Action performed on "+locator,LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
									  		break;
									  		case "checkbox" : selectCheckBox(locator, by);
									  		    Reports.add("Pass","Action should perform on checkbox "+locator,
													"Action performed on "+locator,LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
									  		break;	
									  		case "webtable" : fetchContentOfWebTable(locator, by);
									  			Reports.add("Pass","Content should fetched from web table "+locator,
									  				"Content should fetched from "+locator,LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
									  		break;
									  		default:
									  			Reports.add("Fail","Element should perform on element",
														"Unable to perform action on element",LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
									  			break;
									  	}
								  }
								  catch(Exception e)
								  {
									  Reports.add("Fail","Element should perform on Element",
												e.getMessage(),LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
								  }
							  }
							  
							  /**
								 * @Method_Name :popUpBrowserTab
								 * Method  to verify popup in new tab and click appropriate button
								 * @param choice : choice of popup
								 * @param attachedText : attached text of popup
								 * @param tabNum : tab number 
								 * @return Void
								 * @author Arindam
								 */
								  public void popUpBrowserTab(String choice,String attachedText,int tabNum)
								  {
									  try
									  {     
										  	ArrayList<String> tab = new ArrayList<String>(driver.getWindowHandles());
										  	driver.switchTo().window(tab.get(tabNum));
										  	popUpClick(choice, attachedText);
									  }
									  catch(Exception e)
									  {
										  Reports.add("Fail","Action should performed on popup",
													e.getMessage(),LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
									  }
								  }

							  
							  /**
								 * @Method_Name :verifyElementBrowserTab
								 * Method  to verify element in new tab
								 * @param locator :Locator use to find the web element
								 * @param by :Use to identify Locator type
								 * @param tabNum : tab number 
								 * @return boolean
								 * @author Arindam
								 */
								  public boolean verifyElementBrowserTab(String locator,String by,int tabNum)
								  {
									  boolean cond = false;
									  try
									  {     
										  	ArrayList<String> tab = new ArrayList<String>(driver.getWindowHandles());
										  	driver.switchTo().window(tab.get(tabNum));
										  	if (isElementDisplayed(locator, by))
										  	{
										  		cond = true;									 
										  		Reports.add("Pass","Action should perform on Element "+locator,
													"Action performed on "+locator,LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
										  	}
									  }
									  catch(Exception e)
									  {
										  cond = false;
										  Reports.add("Fail","Element should perform on Element",
													e.getMessage(),LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
									  }
									return cond;
								  }
								  
							  /**
								 * @Method_Name :closeBrowserTab
								 * Method  to close new tab
								 * @param locator :Locator use to find the web element
								 * @param by :Use to identify Locator type
								 * @param tabNum : tab number 
								 * @param element : web element name
								 * @return Void
								 * @author Arindam
								 */
								  public void closeBrowserTab(int tabNum,int prtnTab)
								  {
									  try
									  {     
										  	ArrayList<String> tab = new ArrayList<String>(driver.getWindowHandles());
										  	driver.switchTo().window(tab.get(tabNum));
										  	driver.close();
										  	driver.switchTo().window(tab.get(prtnTab));
									  }
									  catch(Exception e)
									  {
										  Reports.add("Fail","Method should clode browser tab",
													e.getMessage(),LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
									  }
								  }
								  
									
									/**
									* @Method_Name : setupBrowserAbsDriverPath 
									* Method to instantiate and launch web browser based on
									* �Browser Type� (Firefox, IE, Chrome) provided as test input.
									* This method works on the relative path of driver provided 
									* in resource.properties file.
									* @param browser :IE,Chrome,PhantomJS
									* @return Webdriver 
									* @author Sabya
									 * @throws IOException 
									*/
									public  WebDriver setupSauceWithBrowser(String browser) throws IOException	
									{
										Properties prop = new Properties();
									     InputStream input = null;
										input = new FileInputStream(PROPERTYfile);
								    	 prop.load(input);
								    	 id=prop.getProperty("proj.sauce.id").trim();
								    	 accessKey=prop.getProperty("proj.saucekey").trim();
								    	 String sauceLabUrl="http://"+id+":"+accessKey+"@ondemand.saucelabs.com:80/wd/hub";
								    	 logger.info("The Sauce values are:"+sauceLabUrl);
										 try
										 {
											 browserName = browser;
											
											 if(browser.equalsIgnoreCase("Chrome"))
											 {
												 DesiredCapabilities caps = DesiredCapabilities.chrome();
												  caps.setCapability("platform", "Windows 7");
												  caps.setCapability("version", "74");
												  caps.setCapability("name", "Testing on Chome 74");
												  driver = new RemoteWebDriver(new URL(sauceLabUrl), caps);
													 Reports.add("Pass","Saucelab with chrome initialized","Chrome Initialized", 
														 LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
											 }
											 else if(browser.equalsIgnoreCase("IE"))
											 {
												 DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
												  caps.setCapability("platform", "Windows 7");
												  caps.setCapability("version", "74");
												  caps.setCapability("name", "Testing on Chome 74");
												  driver = new RemoteWebDriver(new URL(sauceLabUrl), caps);
													 Reports.add("Pass","Saucelab with chrome initialized","Chrome Initialized", 
														 LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));

													/* Reports.add("Pass","Internet Explorer should initialize","Internet Explorer initialized", 
														 LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
*/
											 }
											 else if(browser.equalsIgnoreCase("Firefox"))
											 {
												 DesiredCapabilities caps = DesiredCapabilities.firefox();
												  caps.setCapability("platform", "VISTA");
												  caps.setCapability("version", "74");
												  caps.setCapability("name", "Testing on Chome 74");
												  driver = new RemoteWebDriver(new URL(sauceLabUrl), caps);
													 Reports.add("Pass","Saucelab with chrome initialized","Chrome Initialized", 
														 LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));

											 }
											 /*else if(browser.equalsIgnoreCase("phantomjs"))
											 {
												 String driverPath = currDir + Directory.phantomJSPath;
												 File filePhantom=new File(driverPath);
												 System.setProperty("phantomjs.binary.path", filePhantom.getPath());
												 driver=new PhantomJSDriver();
												 Reports.setWebDriver(driver);

													 Reports.add("Pass","PhantomJS browser should initialize","PhantomJS initialized", 
														 LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
											 }
											 else if(browser.equalsIgnoreCase("htmlunit"))
											 {
												 driver=new HtmlUnitDriver();
												 ((HtmlUnitDriver) driver).setJavascriptEnabled(true);
												 Reports.setWebDriver(driver);

													 Reports.add("Pass","HtmlUnit browser should initialize","HtmlUnit initialized", 
														 LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
											 }
											 else if(browser.equalsIgnoreCase("HeadlessChrome"))
											 {
												 String driverPath = currDir + Directory.chromeDriverPath;
												 File fileChrome=new File(driverPath);
												 System.setProperty("webdriver.chrome.driver", fileChrome.getPath());
												 ChromeOptions options = new ChromeOptions();
												 options.addArguments("headless");
												 driver=new ChromeDriver(options);
												 Reports.setWebDriver(driver);

													 Reports.add("Pass","Headless Chrome browser should initialize","Headless Chrome initialized", 
									 					LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));

											 }
*/										 }
										 catch(Exception e)
										 
										 {	 
											 e.printStackTrace();
											 Reports.add("Fail",browser+"Browser should initialize",e.getMessage(), 
													 LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
										 }
										return driver;
									}	  
								  
							
								  
								  
								  
}