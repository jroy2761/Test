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

public class WebAppTest extends AUTBaseTest{
	static Logger logger = Logger.getLogger(HrmAddemployeeTest.class.getName());	

	//Utilities objUtilities = new Utilities();

	@DataProvider(name = "WebApp")
	public Object[][] testLoginScenario() throws Exception {
		Object[][] data = getTableArray(Directory.dataSheetPath, "WebApp", "WebApp");
		return (data);
	}
	@Test(dataProvider = "WebApp")
	public void orangehrm(String browser, String url) {

		try {

			ReportsConfig();

			setupBrowserAbsDriverPath(browser);

			driver.manage().window().maximize();
			//driver.manage().deleteAllCookies();

			driver.get(url);
			Thread.sleep(6000);

		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {

			driver.quit();

		}


	}
}
