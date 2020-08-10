package uiTest;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;

import selenium_framework.AUTBaseTest;

public class NewTest1 extends AUTBaseTest {
	public static final String USERNAME = "sabya123";

	//enter your access key here
	public static final String ACCESS_KEY = "266749f1-f3fd-42de-8ab9-6ef9388d9bdd";
	public static final String SauceLabURL = "http://"+USERNAME+":"+ACCESS_KEY+"@ondemand.saucelabs.com:80/wd/hub";
	
	WebDriver driver;
  @Test
  public void f() throws MalformedURLException,TimeoutException, InterruptedException{
	  DesiredCapabilities caps = DesiredCapabilities.chrome();
	  caps.setCapability("platform", "Windows 7");
	  caps.setCapability("version", "74");
	  caps.setCapability("name", "Testing on Chome 74");
	  this.driver = new RemoteWebDriver(new URL(SauceLabURL), caps);
	  driver.get("https://www.google.com");
	  System.out.println(driver.getTitle());
	  System.out.println("BrowserName :" + caps.getBrowserName() + " – "
	  + "Version : " + caps.getVersion());
	  System.out
	  .println("————————————————————————————");
	  }
  }

