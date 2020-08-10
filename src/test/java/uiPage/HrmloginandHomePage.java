package uiPage;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import selenium_framework.CaptureScreen;
import selenium_framework.LogAs;
import selenium_framework.Reports;
import selenium_framework.CaptureScreen.ScreenshotOf;

//import com.scenarios.*;
import selenium_framework.Utilities;
import apiTest.JiraIssueCreate;

/**
 * This class Login Page
 */
public class HrmloginandHomePage extends Utilities {

	/**
	 * pageLocators
	 */
	// comment

	public static String locateById = "id";
	public static String locateByXpath = "xpath";
	public static String locateByCss = "css";
	public static String locateByName = "name";

	public static String tbxUserid = "txtUsername";
	public static String tbxPassword = "txtPassword";
	public static String btnLogin = "btnLogin";
	public static String addButton = "btnAdd";
	public static String firstNm = "firstName";
	public static String lastNm = "lastName";
	public static String createlogincheck = "chkLogin";
	public static String employeeid = "employeeId";
	public static String userid = "user_name";
	public static String password = "user_password";
	public static String confimpassword = "re_password";
	public static String savebutton = "btnSave";
	public static String empid = "empsearch_id";
	public static String searchbtn = "searchBtn";
	String empidlink = "//div[@id='tableWrapper']/table/tbody/tr[1]/td[2]/a";
	String personalDetails = "//h1[text()='Personal Details']";
	String OrangeHrmAdminWelcome = "//div[@id='branding']/a/following-sibling::a[text()='Welcome Admin']";
	String OranheHrmPIMTab = "//div[@class='menu']/ul/li[2]/a/b[text()='PIM']";
	String profileName = "//div[@id='profile-pic']/h1";
	String employeeList = "//a[contains(text(),'yee List')]";
	String size = "(//div[@class='detail__attributes-item detail__size-item not-avail-instock'])[1]/input";
	String Add = "//span[text()='This item has been successfully added:']";
	JiraIssueCreate objJiraIssueCreate = new JiraIssueCreate();

	// String
	/**
	 * function to login into Orange Hrm Application
	 * 
	 * @param username
	 * @param password
	 */

	public void LoginHrm(String username, String password) {
		try {
			
			Thread.sleep(1000);
			setTextboxValue(tbxUserid, locateById, username);
			Thread.sleep(1000);
			setTextboxValue(tbxPassword, locateById, password);
			Thread.sleep(1000);
			clickButton(btnLogin, locateById);
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Function to validate application login
	 * 
	 * @param userId   - String username
	 * @param password - String password
	 */
	public void AdminPageNavigate() {

		try {
			int status = verifyElement(OrangeHrmAdminWelcome, locateByXpath, 10, "Verify Admin Page navigation",
					"Successfully navigated to Admin welcome Page", "Navigation to Admin Page failed");
			
			if(status == 1) {
				objJiraIssueCreate.getData();
				objJiraIssueCreate.JiraAPICreateIssue();
				objJiraIssueCreate.JiraAPIAttachScreenshot("Error");
			}

			Thread.sleep(2000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addSearchVerifyEmployeeHrm(String firstname,String lastname,String userid,String password)
	{
		String id="";
		try {
			clickButton(OranheHrmPIMTab, locateByXpath);
			clickButton(addButton, locateById);
			id=getElementPropertyValue(employeeid, locateById);
			System.out.println("The id is::"+id);
			setTextboxValue(firstNm, locateById, firstname);
			setTextboxValue(lastNm, locateById, lastname);
			clickButton(createlogincheck, locateById);
			setTextboxValue(HrmloginandHomePage.userid, locateById, userid);
			setTextboxValue(HrmloginandHomePage.password, locateById, password);
			setTextboxValue(confimpassword, locateById, password);
			clickButton(savebutton, locateById);
			String str=getDisplayedText(profileName, locateByXpath).trim();
			String profilepagename=str.replaceAll("\\s", "");
			String suppliedValue=firstname+lastname;
			if(driver.findElement(By.xpath(profileName)).isDisplayed())
			{
			  if(profilepagename.equals(suppliedValue))
			 {
				Reports.add("Pass","Verify profile created for new employee","Employee profile created", LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			 }
			else {
				Reports.add("Fail","Verify profile created for new employee","Employee profile does not created its already Exists", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				
			}
			  
			
			
			/** Search operation performed with unique employee id to check whether employe data is recorded or not **/
			Thread.sleep(2000);
			clickButton(employeeList, locateByXpath);
			Thread.sleep(2000);
			setTextboxValue(empid, locateById, id);
			Thread.sleep(2000);
			clickButton(searchbtn, locateById);
			
//			verifyElement(empidlink, locateByXpath,10, "Verify new employee data is found with search operation",
//					"Successfully data is populated", "No data is found");
			
			Thread.sleep(3000);
			clickButton(empidlink, locateByXpath);
			Thread.sleep(3000);
//			verifyElement(personalDetails, locateByXpath,10, "Verify clicking on the search id hyperlink Navigate to personal details page",
//					"Successfully Navigate to personal details page", "Doesnot navigate to Personal details page");
	
			}
		
		else
		{
			Reports.add("Fail","Verify profile created for new employee","Employee profile does not created its already Exists", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
	}
		catch(Exception e)
		{
			Reports.add("Fail","Verify profile created for new employee","Employee profile Already Exits you are trying to enter duplicate value", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			//e.printStackTrace();
		}
		
	}
	
	/*public void verifyDuplicateEmployeeRecord(String firstname,String lastname,String userid,String password)
	{
		try {
			
			
			
		}
		catch(Exception e)
	}*/
	/*public void AllWeedingDressPageNavigate() {

		try {
			mouseHover(Bride, locateByXpath);
			Thread.sleep(3000);

			driver.get("https://www.davidsbridal.com/wedding-dresses/all-wedding-dresses?navtest");
			verifyElement(AllWeedingDressPage, locateByXpath, 30, "Verify All Wedding dress page",
					"All Wedding dress page visible", "All Wedding dress page not visible");

			Thread.sleep(4000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	/*public void TotalCount() {

		try {
			String str = driver.findElement(By.xpath(TotalCountAllDress)).getText();

			Reports.add("Pass", "Total number should not be equal to zero ",
					"Total number is not equals to zero , currently its showing " + str, LogAs.PASSED,
					new CaptureScreen(ScreenshotOf.BROWSER_PAGE));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
*/
	/*public void SortByPrice() {

		try {
			Select SortByDropdown = new Select(driver.findElement(By.xpath(SortDropdown)));
			SortByDropdown.selectByIndex(2);

			Reports.add("Pass", "Dropdpown option should change to Price high to low",
					"Dropdpown option changed to Price high to low", LogAs.PASSED,
					new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Thread.sleep(4000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	/*public void FirstDress() {

		try {
			clickButtonUsingJavaScript(FirstDress, locateByXpath);
			verifyElement(AddToShoppingBag, locateByXpath, 30, "Verify Add to shopping Bag page",
					"Add to shopping Bag page visible", "Add to shopping Bag page not visible");
			// clickButtonUsingJavaScript(size, locateByXpath);
			clickButtonUsingJavaScript(AddToShoppingBag, locateByXpath);
			Reports.add("Fail", "Verify successfully added to Cart",
					"Not successfully added to cart as size not selected", LogAs.FAILED,
					new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			// verifyElement(Add, locateByXpath, 30, "Verify successfully added to Cart",
			// "Successfully added to cart", "Not successfully added to cart");
			Thread.sleep(4000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

}
