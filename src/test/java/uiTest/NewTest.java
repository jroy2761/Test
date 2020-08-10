package uiTest;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import selenium_framework.AUTBaseTest;

public class NewTest extends AUTBaseTest {
	public  String pgtitle="";
	WebDriver driver;

  @Test
  public void f() {
	  try {
		  
	  
	  //String browser="Chrome";
	 
	 
	  ReportsConfig();
	  setupBrowserAbsDriverPath("Chrome");
	  driver.manage().window().maximize();
	  //driver.manage().deleteAllCookies();
	  
	  driver.get("https://www.google.com/");
	  Thread.sleep(2000);
	  pgtitle=driver.getTitle();
	  
  }
	  catch(Exception e)
	  {
		  e.printStackTrace();
		  
	  }
  }
  
  @Test(dependsOnMethods="f")
  public void g()
  {
	  System.out.println("The value of f is::"+pgtitle);
  }
}
