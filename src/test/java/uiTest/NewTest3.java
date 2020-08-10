package uiTest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;

public class NewTest3 {
	WebDriver driver;
	private String tl="";
  @Test
  public void f() {
	  try {
	  
	  System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"\\Config\\Driver\\chromedriver.exe");
	  ChromeOptions o = new ChromeOptions();
	  o.addArguments("disable-extensions");
	  o.addArguments("--start-maximized");
	  o.addArguments("--start-maximized");

	  //WebDriver driver = new ChromeDriver(o);

	  driver=new ChromeDriver(o);
	  //driver.manage().window().maximize();
	  driver.get("https://www.google.com");
	  driver.manage().window().maximize();
	  tl=driver.getTitle();
	  
  }
	  catch(Exception e)
	  {
		  e.printStackTrace();
	  }
  }
  @Test(dependsOnMethods="f")
  public void g() {
	  System.out.println("the value is:"+tl);
  
}
}
