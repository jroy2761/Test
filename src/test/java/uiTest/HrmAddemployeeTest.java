package uiTest;

import org.apache.log4j.Logger;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import uiPage.HrmloginandHomePage;

/*import com.pagefactory.HrmloginandHomePage;
import com.utilities.Utilities;

import testng.AUTBaseTest;
import testng.Directory;*/

import selenium_framework.AUTBaseTest;
import selenium_framework.Directory;

public class HrmAddemployeeTest extends AUTBaseTest{
	static Logger logger = Logger.getLogger(HrmAddemployeeTest.class.getName());

	
	
	//Utilities objUtilities = new Utilities();

	@DataProvider(name = "HRM")
	public Object[][] testLoginScenario() throws Exception {
		Object[][] data = getTableArray(Directory.dataSheetPath, "HRM", "HRM");
		return (data);
	}
  @Test(dataProvider = "HRM")
  public void orangehrm(String browser, String url,String mode,String username,String password,String firstname,String lastname,String userid,String empassword) {
	  
	  try {
		  
		  ReportsConfig();
		  if (mode.equals("sauce"))
		  {
			  setupSauceWithBrowser(browser);
		  }
		  else {
			  setupBrowserAbsDriverPath(browser);
			  
		  }
		  
		  driver.manage().window().maximize();
		  //driver.manage().deleteAllCookies();
		  
		  driver.get(url);
		  HrmloginandHomePage hrm=new HrmloginandHomePage();
		  hrm.LoginHrm(username, password);
		  hrm.AdminPageNavigate();
//  	      hrm.addSearchVerifyEmployeeHrm(firstname, lastname, userid,empassword);
		
	} catch (Exception e) {
		System.out.println(e.toString());
	} finally {
		
		driver.quit();
		
	}

	  
  }
}
