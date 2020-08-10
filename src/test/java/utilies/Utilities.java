package utilies;


import java.awt.Robot;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import selenium_framework.AUTBaseTest;
import selenium_framework.CaptureScreen;
import selenium_framework.CaptureScreen.ScreenshotOf;
import selenium_framework.LogAs;
import selenium_framework.Reports;

/**
 *This class contains generic utilities
 */

public class Utilities extends AUTBaseTest {

	AUTBaseTest objAUTBaseTest=new AUTBaseTest();


	/**
	 *This function is used to instantiate and launch web browser based on �Browser Type� 
	 *@param browser - String browser type(Firefox, IE, Chrome) provided as test input
	 *@return - returns the driver
	 */

	public WebDriver setupBrowserAbsDriverPath(String browser)
	{
		WebDriver driver=null;
		try
		{
			driver=objAUTBaseTest.setupBrowserAbsDriverPath(browser);
			return driver;
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
			return driver;
		}
	}

	/**
	 *This function sets the value in a textbox.
	 *@param locator - String Page Element locator
	 *@param locatorType - String Page Element locator Type
	 *@param inputValue - String value to be entered
	 */


	public void setTextboxValue(String locator, String locatorType, String inputValue)
	{
		try
		{
			objAUTBaseTest.setTextboxValue(locator,locatorType,inputValue);
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
	}

	/**
	 *This function performs Click Action on an element.
	 *@param locator - String Page Element locator
	 *@param locatorType - String Page Element locator Type
	 */

	public void clickButton(String locator, String locatorType)
	{
		try
		{
			objAUTBaseTest.clickButton(locator,locatorType);
			
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}

	}


	/**
	 *This function performs Click Action using Javascript executor on an element.
	 *@param locator - String Page Element locator
	 *@param locatorType - String Page Element locator Type
	 */

	public void clickButtonUsingJavaScript(String locator, String locatorType)
	{
		try
		{
			WebElement webbtn = objAUTBaseTest.getElement(locator, locatorType);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].click();", webbtn);
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}

	}



	/**
	 *This function checks for the presence of a webelement.
	 *@param locator - String Page Element locator
	 *@param locatorType - String Page Element locator Type
	 */


	public boolean isElementPresent(String locator, String locatorType)
	{

		try
		{
			return objAUTBaseTest.isElementPresent(locator,locatorType);

		}
		catch(Exception e)
		{
			System.out.println(e.toString());
			return false;

		}


	}


	/**
	 *This function returns  webelement.
	 *@param locator - String Page Element locator
	 *@param locatorType - String Page Element locator Type
	 */

	public WebElement getElement(String locator, String locatorType)
	{
		WebElement ele=null;
		try
		{
			return objAUTBaseTest.getElement(locator,locatorType);

		}
		catch(Exception e)
		{
			System.out.println(e.toString());
			return ele;

		}
	}


	/**
	 *This function performs Click Action on an element.
	 *@param by - String Page Element locator Type
	 *@param locator - String Page Element locator
	 */

	public By createLocator(String by, String locator) {        
		By finalLocator = null;                
		if(by.contentEquals("id")) {
			finalLocator = By.id(locator);        
		}
		else if (by.contentEquals("xpath")) {            
			finalLocator = By.xpath(locator);      
		}
		else if(by.contentEquals("cssSelector")) {           
			finalLocator = By.cssSelector(locator);        
		}else if(by.contentEquals("name")){
			finalLocator = By.name(locator);
		}
		return finalLocator;
	}


	/**
	 *This function performs Click Action on a Weblink.
	 *@param locator - String Page Element locator
	 *@param by - String Page Element locator Type
	 */

	public void clickWebLink(String locator, String by){

		try {
			objAUTBaseTest.clickWebLink(locator, by);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}




	/**
	 *This function returns a list of Web elements
	 *@param locator - String Page Element locator
	 *@param by - String Page Element locator Type
	 */
	public List<WebElement> getListOfElements(String locator, String by){

		List<WebElement> elements = null;

		try {
			By locatorText = createLocator(by, locator);
			elements = driver.findElements(locatorText);
			return elements;
		} catch (Exception e) {
			System.out.println(e.toString());
			return elements;
		}
	}

	/**
	 *This function returns true if the String contains only alphabets and spaces
	 *@param s - String the word to be validated
	 */
	public boolean validateOnlyLettersSpaces(String s){
		for(int i=0;i<s.length();i++){
			char ch = s.charAt(i);
			if (Character.isLetter(ch) || ch == ' ') {
				continue;
			}
			return false;
		}
		return true;
	}

	/**
	 *This function returns true if the String contains only alphabets, digits and spaces
	 *@param s - String the word to be validated
	 */
	public boolean validateAlphaNumeric(String s){
		for(int i=0;i<s.length();i++){
			char ch = s.charAt(i);
			if (Character.isLetter(ch) || Character.isDigit(ch)|| ch == ' ') {
				continue;
			}
			return false;
		}
		return true;
	}

	
	/**
	*This function returns true if the passed array has all the values in a column are sorted in ascending order.
	*@param arrayContent - String[] Array with all the Strings
	*returns true if the Array Content is Sorted in Ascending order
 	*/
	
public boolean checkSortingOfWebTableColoumn(String[] arrayContent){
		
		int flag = 0;
		
		for (int i = 0; i < arrayContent.length - 1; i++) {
			String s1 = arrayContent[i];
			String s2 = arrayContent[i+1];
	        int comparisonResult = s1.compareToIgnoreCase(s2);

	        if(comparisonResult > 0){
	            flag = 1;
	            System.out.println(s1);
	            System.out.println(s2);
	            break;
	        }
		}	
		if(flag == 0){
			return true;
		}else{
			return false;
		}
    }
	/**
	 *This function returns true if the String is a Date
	 *@param s - String the word to be validated
	 */
	public boolean validateDate(String s){
		boolean checkFormat;

		if (s.matches("([0][0-9]||[1][0-2])/([0][1-9]||[1-2][0-9]||[3][0-1])/([1-2][0-9][0-9][0-9])"))
			checkFormat=true;
		else
			checkFormat=false;
		
		return checkFormat;
	}

	/**
	 *This function returns true if the String contains only alphabets, digits and spaces
	 *@param locatorText - String Page Element locator
	 *@param byText - String Page Element locator Type
	 *@param timeout - int Time to wait for the existence of an object in SECONDS.
	 *@param stepDesc - String Step description
	 *@param passLog - String Pass statement
	 *@param failLog - String Fail statement
	 */
	public void verifyElement(String locatorText, String byText, int timeout, String stepDesc, String passLog, String failLog) {

		try {
			WebDriverWait wait = new WebDriverWait(driver, timeout);
			By locator = createLocator(byText, locatorText);
			
			if(wait.until(ExpectedConditions.visibilityOfElementLocated(locator)) != null) {
				Reports.add("Pass", stepDesc, passLog, LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}else {
				Reports.add("Fail", stepDesc, failLog, LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}
		} catch (Exception e) {
			Reports.add("Fail", stepDesc, failLog, LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			e.printStackTrace();
		}

	}

	/**
	 *This function returns true if the String contains only alphabets, digits and spaces
	 *@param locatorText - String Page Element locator
	 *@param byText - String Page Element locator Type
	 *@param timeout - int Time to wait for the existence of an object in SECONDS.
	 */
	public boolean verifyElementWithoutLog(String locatorText, String byText, int timeout) {

		WebDriverWait wait = new WebDriverWait(driver, timeout);
		By locator = createLocator(byText, locatorText);
		if(wait.until(ExpectedConditions.visibilityOfElementLocated(locator)) != null) {
			return true;
		}else {
			return false;
		}

	}


	/**
	 *This function highlights the element on the page
	 *@param element - WebElement to be highlighted
	 *@param colour - String highlight colour
	 */
	public void highlightElement(WebElement element, String colour) throws InterruptedException {
		Thread.sleep(2000);
		for (int i = 0; i <2; i++) {
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "color: "+colour+"; border: 2px solid "+colour+";");
			Thread.sleep(1000);
			js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
		}			
	}


	/**
	 *This function moves the cursor to the specified element
	 *@param element - WebElement Page element on which cursor needs to be moved
	 */
	public void moveToPageElement(WebElement element)
	{

		try {
			AUTBaseTest.moveToElement(element);
		} catch (Exception e) {
			System.out.println(e.toString());

		}
	}


	/**
	 *This function converts String to character array
	 *@param str - String the string value which will be converted to character array
	 */
	public String[] convertToArray(String str) {

		String[] arrNew;
		arrNew =  new String[str.length()];

		for (int i = 0; i < str.length(); i++) {
			char val = str.charAt(i);
			arrNew[i] = Character.toString(val);
		}
		return arrNew;
	}

	/**
	 *This function checks the ascending sorting based on the ascii value
	 *@params strArray1 - String[] compare with
	 *@params strArray2 - String[] compare to
	 *@return flag - int if flag = -1 or 0 then ascending, if flag = 0 then not ascending
	 */
	public int checkAscendingSorting(String[] strArray1, String[] strArray2 ) {
		int flag = 0;
		try
		{

			if (strArray1.length > strArray2.length) {
				for (int i1 = 0; i1 < strArray2.length; i1++) {
					if ((strArray1[i1].compareTo(strArray2[i1]) < 0)) {
						flag = -1;
						break;
					}else if((strArray1[i1].compareTo(strArray2[i1]) == 0)){
						flag = 0;
					}else {
						flag = 1;
						break;
					}			
				}
			} else {
				for (int i1 = 0; i1 < strArray1.length; i1++) {
					if ((strArray1[i1].compareTo(strArray2[i1]) < 0)) {
						flag = -1;
						break;
					}else if((strArray1[i1].compareTo(strArray2[i1]) == 0)){
						flag = 0;
					}else {
						flag = 1;
						break;
					}	
				}	
			}
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
		return flag;
	}

	/**
	 *This function checks the descending sorting based on the ascii value
	 *@params strArray1 - String[] compare with
	 *@params strArray2 - String[] compare to
	 *@return flag - int if flag = 1 or 0 then descending, if flag = -1 then not descending
	 */
	public int checkDescendingSorting(String[] strArray1, String[] strArray2 ) {
		int flag = 0;
		try
		{
			if (strArray1.length > strArray2.length) {
				for (int i1 = 0; i1 < strArray2.length; i1++) {
					if ((strArray1[i1].compareTo(strArray2[i1]) < 0)) {
						flag = 1;
						break;
					}else if((strArray1[i1].compareTo(strArray2[i1]) == 0)){
						flag = 0;
					}else {
						flag = -1;
						break;
					}			
				}
			} else {
				for (int i1 = 0; i1 < strArray1.length; i1++) {
					if ((strArray1[i1].compareTo(strArray2[i1]) < 0)) {
						flag = 1;
						break;
					}else if((strArray1[i1].compareTo(strArray2[i1]) == 0)){
						flag = 0;
					}else {
						flag = -1;
						break;
					}	
				}	
			}
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
		return flag;
	}

	/**
	 *This function wait till the time specified for the attribute to ascertain the specified partial value (returns true or false)
	 *@param element - WebElement Element whose attribute's value needs to be checked
	 *@param attribuiteName - String Attribute Name whose value needs to be checked
	 *@param attributeValue - String Attribute Value that needs to be achieved
	 *@param timeout - long Maximum Time in milliseconds to wait for the attribute value to change
	 */
	public boolean waitForPartialAttributeValue(WebElement element, String attribuiteName, String attributeValue, long timeout) {	

		long startTime = System.currentTimeMillis();
		String attValue;
		do{
			attValue = element.getAttribute(attribuiteName).trim();
		}while(!attValue.contains(attributeValue) && System.currentTimeMillis()-startTime<timeout);

		if (attValue.contains(attributeValue)) {
			return true;
		} else {
			return false;
		}
	}


	/**
	 *This function wait till the time specified for the attribute to ascertain the specified exact value (returns true or false)
	 *@param element - WebElement Element whose attribute's value needs to be checked
	 *@param attribuiteName - String Attribute Name whose value needs to be checked
	 *@param attributeValue - String Attribute Value that needs to be achieved
	 *@param timeout - long Maximum Time in milliseconds to wait for the attribute value to change
	 */
	public boolean waitForExactAttributeValue(WebElement element, String attribuiteName, String attributeValue, long timeout) {	


		long startTime = System.currentTimeMillis();
		String attValue;
		do{
			attValue = element.getAttribute(attribuiteName).trim();
		}while(!attValue.contentEquals(attributeValue) && System.currentTimeMillis()-startTime<timeout);

		if (attValue.contentEquals(attributeValue)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 *This function performs click action on a radio button
	 *@param locator - String Page Element locator
	 *@param locatorType - String Page Element locator Type
	 */


	public void clickRadioButton(String locator, String locatorType)
	{
		try
		{
			objAUTBaseTest.clickRadioButton(locator, locatorType);
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
	}

	/**
	 *This function performs closing of all open browsers
	 */

	public void killBrowser()
	{
		try
		{
			objAUTBaseTest.killBrowser();
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
	}


	/**
	 *This function performs click action on a radio button
	 *@param locator - String Page Element locator
	 *@param locatorType - String Page Element locator Type
	 *@param value -selects dropdown list value using either Index, Value or Visible Text as per Value 
	 *@param selectType -Select Type (index, visibletext or value etc.) provided as test input
	 */

	public void selectDropDownValueByType(String locator, String locatorType, String value, String selectType)
	{
		try
		{
			objAUTBaseTest.selectDropDownValueByType(locator, locatorType, value, selectType);
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}

	}

	/**
	 *This function performs execution of a database query and returns the output result set
	 *@param dbType - Type of database application is using SQL/Oracle
	 *@param sqlQuery - SQL query user wants to execute
	 *@param queryType -Query Type (SELECT, INSERT, UPDATE or DELETE) 
	 *@param dbURL -Database connection string
	 *@param uid -Database connection userid
	 *@param pwd -Database connection password
	 *@return resultset of the query
	 */


	public Map<String,List<Object>> executeDatabaseAction(String dbType,String sqlQuery,String queryType,String dbURL,String uid,String pwd)
	{
		Map<String, List<Object>> resultMap = new HashMap<String, List<Object>>();
		try
		{
			resultMap= objAUTBaseTest.executeDatabaseAction(dbType, sqlQuery, queryType, dbURL, uid, pwd);
			return resultMap;

		}
		catch(Exception e)
		{
			System.out.println(e.toString());
			return resultMap;

		}

	}

	/**
	 *This function performs hover action on any webelement
	 *@param locator - String Page Element locator
	 *@param locatorType - String Page Element locator Type
	 */


	public void mouseHover(String locator, String locatorType)
	{
		WebElement element;
		try
		{
			element=objAUTBaseTest.getElement(locator, locatorType);
			new Actions(driver).moveToElement(element).build().perform();

		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
	}

	/**
	 *This function upload file from application
	 *@param file - File need to upload
	 */


	public void uploadFile(String file)
	{
		Robot robot;
		try
		{
			File filePath = new File(file);
			String   path = filePath.getCanonicalPath();
			copyTextToClipboard(path);
			robot = new Robot();
			pasteViaRobot(robot);
			pressEnterViaRobot(robot, 1);


		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
	}
	
	/**
	*This function returns true if the element gets visible within the specified time
	*@param locatorText - String Page Element locator
	*@param byText - String Page Element locator Type
	*@param timeout - int Time to wait for the existence of an object in SECONDS.
	*/
	public boolean waitForElement(String locatorText, String byText, int timeout) {
		try {
			WebDriverWait wait = new WebDriverWait(driver,timeout);
			By locator = createLocator(byText, locatorText);
			if(wait.until(ExpectedConditions.visibilityOfElementLocated(locator)) != null) {
				return true;
				}
			else {
				return false;
				}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}
	
	/**
	*This function returns true if the passed array has all the values alphabetically sorted in ascending order.
	*@param arrayContent - String[] Array with all the Strings
	*returns true if the Array Content is Sorted in Ascending order
 	*/
	public boolean checkSortingOfDropdownValues(String[] arrayContent){
		
		int flag = 0;
		
		for (int i = 0; i < arrayContent.length - 1; i++) {
			String s1 = arrayContent[i];
			String s2 = arrayContent[i+1];
	        int comparisonResult = s1.compareToIgnoreCase(s2);

	        if(comparisonResult > 0){
	            flag = 1;
	            System.out.println(s1);
	            System.out.println(s2);
	            break;
	        }
		}	
		if(flag == 0){
			return true;
		}else{
			return false;
		}
    }
	
	/**
	*This function returns true if the passed array has all the values alphabetically sorted in descending order.
	*@param arrayContent - String[] Array with all the Strings
	*returns true if the Array Content is Sorted in Ascending order
 	*/
	public boolean checkDescendingSortingOfValues(String[] arrayContent){
		
		int flag = 0;
		
		for (int i = 0; i < arrayContent.length - 1; i++) {
			String s1 = arrayContent[i];
			String s2 = arrayContent[i+1];
	        int comparisonResult = s1.compareToIgnoreCase(s2);

	        if(comparisonResult < 0){
	            flag = 1;
	            System.out.println(s1);
	            System.out.println(s2);
	            break;
	        }
		}	
		if(flag == 0){
			return true;
		}else{
			return false;
		}
    }
	
	/**
	 *This function returns current system date in DD/MM/YYYY format
	 */
	 public String getCurrentSystemDate() {
		 String date1 = null;
		 try {
			 DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			 Date date = new Date();
			 date1= dateFormat.format(date);
			 System.out.println(date1);	 
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		 return date1;
	}
	 
	 /**This function sets data for a particular column in the datasheet at runtime
	 * @param sheetName String The Sheet you want to manipulate
	 * @param colName String The name of the Column you want to write data
	 * @param data String The data you want to write
	 */
	 public void writeExcelData(String sheetName, String colName, String data) {
		 try {
			 String homePath = System.getProperty("user.dir");
			 String excelPath = homePath + "//Resources//Data//Data.xls";
			 File file = new File(excelPath);
			 FileInputStream fip = new FileInputStream(file);
			 HSSFWorkbook workbook = new HSSFWorkbook(fip);
			 HSSFSheet worksheet = workbook.getSheet(sheetName);
			 HSSFRow activeRow = worksheet.getRow(0);
			 HSSFRow dataRow = worksheet.getRow(1);
			 int colCount = activeRow.getPhysicalNumberOfCells();
			 int colIndex = -1;
			 for(int i = 0;i < colCount;i++) {
				 HSSFCell activeCell = activeRow.getCell(i);
				 if(activeCell.getStringCellValue().equalsIgnoreCase(colName)){
					 colIndex = i;
					 break;
				 	}
				 }
			 if(colIndex != -1) {
				 HSSFCell dataCell = dataRow.getCell(colIndex);
				 dataCell.setCellValue(data);
				 FileOutputStream fileOut = new FileOutputStream(file);
				 workbook.write(fileOut);
				 workbook.close(); 
			 }else {
				 System.err.println("No columns found with that name");
			 }
		 } catch (Exception e) {
			 e.printStackTrace();
		 	}
	 }
	 
	 /**This function returns data of a particular column in the datasheet at runtime
	 * @param sheetName String The Sheet you want to manipulate
	 * @param colName String The name of the Column you want to write data
	 */
	 public String fetchExcelData(String sheetName, String colName) {
		 String value = "";
		 try {
			 String homePath = System.getProperty("user.dir");
			 String excelPath = homePath + "//Resources//Data//Data.xls";
			 File file = new File(excelPath);
			 FileInputStream fip = new FileInputStream(file);
			 HSSFWorkbook workbook = new HSSFWorkbook(fip);
			 HSSFSheet worksheet = workbook.getSheet(sheetName);
			 HSSFRow activeRow = worksheet.getRow(0);
			 HSSFRow dataRow = worksheet.getRow(1);
			 int colCount = activeRow.getPhysicalNumberOfCells();
			 int colIndex = -1;
			 for(int i = 0;i < colCount;i++) {
				 HSSFCell activeCell = activeRow.getCell(i);
				 if(activeCell.getStringCellValue().equalsIgnoreCase(colName)){
					 colIndex = i;
					 break;
				 	}
				 }
			 if(colIndex != -1) {
				 HSSFCell dataCell = dataRow.getCell(colIndex);
				 value = dataCell.getStringCellValue();
				 workbook.close(); 
			 }else {
				 System.err.println("No columns found with that name");
			 }
			 return value;
		 } catch (Exception e) {
			 e.printStackTrace();
			 return value;
		 	}
	 }
	 
	 /**
	 *Function to fetch displayed text in a web element
	 **@param locator - String Page Element locator
	 *@param by - String Page Element locator Type
	 */		
	public String getDisplayedText(String locator, String by) {
		try {
			return objAUTBaseTest.getDisplayedText(locator, by);
		} catch (Exception e) {	
			e.printStackTrace();
			return null;
		}
	}

	/**
	 *Function to Scroll an element into view
	 **@param element - WebElement The object to be scrolled to
	 */
	public void scrollIntoView(WebElement element){
		
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 *Function to Wait for page load to finish
	 */
	public void waitForPageLoading() {
        ExpectedCondition<Boolean> pageLoadCondition = new
                ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver driver) {
                        return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
                    }
                };
        WebDriverWait wait = new WebDriverWait(driver, 300);
        wait.until(pageLoadCondition);
	}
	
	/**
	 *Function to Wait for the invisibility of an object
	 */
	public void waitForInvisibility(String locator, String locatorType, int timeOut){
		
		try {
			WebDriverWait wait = new WebDriverWait(driver, timeOut);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(createLocator(locator, locatorType)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 *Function to Wait for the absence of an object
	 */
	public void waitForAbsence(String locator, String locatorType, int timeOut){
		
		try {
			WebDriverWait wait = new WebDriverWait(driver, timeOut);
			wait.until(ExpectedConditions.presenceOfElementLocated(createLocator(locator, locatorType)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}




