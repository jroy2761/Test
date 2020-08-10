package selenium_framework;


import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;


import javax.swing.JOptionPane;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.SAXException;

import selenium_framework.CaptureScreen;
import selenium_framework.CaptureScreen.ScreenshotOf;
import selenium_framework.LogAs;
import selenium_framework.Reports;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;


public class CommonUtils 

{
	public static  int startRow = 0;
	public static  int startCol = 0;
	public static  int endRow = 0;
	public static  int endCol = 0;

	
		
	/**
	* @Method_Name :getDataTableArray
	* Generic method to fetch test data from excel data 
	* source for test script execution, used along with TestNG 
	* Data Provider declaration.Method takes test data file location path, 
	* excel sheet name and test data tag name (Start and End Tag identifier 
	* for the respective test data input) and returns a two dimensional string 
	* array that is fed to Test Method for execution
	* @param xlFilePath :Location of excel data sheet
	* @param sheetName :Sheet name of the excel file
	* @param tableName :Table name or tag name
	* @return String array/Sting[][]
	* @throws FileNotFoundException
	* @author Payel
	*/ 
	/*public String[][] getDataTableArray(String xlFilePath, String sheetName, String tableName) throws FileNotFoundException
	{
        String[][] tabArray=null;
        try
        {
        	Workbook workbook = Workbook.getWorkbook(new File(xlFilePath));
        	Sheet sheet = workbook.getSheet(sheetName); 
        
        	int ci,cj;
        	Cell tableStart=sheet.findCell(tableName);
        	startRow=tableStart.getRow();
        	startCol=tableStart.getColumn();

        	Cell tableEnd= sheet.findCell(tableName, startCol+1,startRow+1, 100, 64000,  false);                

        	endRow=tableEnd.getRow();
        	endCol=tableEnd.getColumn();
        	System.out.println("startRow="+startRow+", endRow="+endRow+", " +
                			"startCol="+startCol+", endCol="+endCol);
        	tabArray=new String[endRow-startRow-1][endCol-startCol-1];
        	ci=0;

        	for (int i=startRow+1;i<endRow;i++,ci++)
        	{
        		cj=0;
        		for (int j=startCol+1;j<endCol;j++,cj++)
        		{
        			tabArray[ci][cj]=sheet.getCell(j,i).getContents();
        		}
        	}
        	Reports.add("Pass",xlFilePath+","+sheetName+","+tableName,"Data should fetch from Excel sheet","Data fetched from Excel Sheet", 
					LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
        }
        catch(Exception e)
        {
        	Reports.add("Fail",xlFilePath+","+sheetName+","+tableName,"Data should fetch from Excel sheet",e.getMessage(), 
					LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
        }
        return(tabArray);
	}*/
	
	
	/**
	* @Method_Name :uploadFileWithRobot
	* Method to achieve file upload in a web page from drive location
	* when File Path Location is provided as an argument.Method utilizes Window 
	* Controls during file upload with Robot Class Implementation using Short Cut Key Controls
	* @param fileLocation : Location where user want to save the file
	* @return String
	* @throws InterruptedException,AWTException
	* @author Arindam
	*/
	public String uploadFileWithRobot(String fileLocation) throws InterruptedException,AWTException
	{
		String path = null;
		try
		{
			File filePath = new File(fileLocation);
			path=filePath.getCanonicalPath();
			copyTextToClipboard(path);
			Robot robo = new Robot();
			pressEnterViaRobot(robo, 1);
			pasteViaRobot(robo);
			Thread.sleep(10000);
			pressEnterViaRobot(robo, 1);
			Thread.sleep(10000);
			Reports.add("Pass","File should get uploaded using Robot from "+fileLocation,"File uploaded from "+fileLocation+" using Robot", 
					LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));		
		}
		catch(Exception e)
		{
			Reports.add("Fail","File should get uploaded using Robot from "+fileLocation,e.getMessage(), 
					LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		return path;
		
	}
	
	
	
	
	/**
	* @Method_Name :readExcel
	* Generic method to read excel file
	* Method takes row number, column number, excel file name and 
	* location, text to be written into respective excel cell
	* @param filePath: File location
	* @return List : return data from 1st row 1st column and 2nd row 1st column
	* @throws: BiffException, IOException,FileNotFoundException
	* @author Payel
	*/
	public List<String> readExcel(String filePath) throws BiffException, IOException,FileNotFoundException
	{
		List<String> userNewTemp = new ArrayList<String>();
		try
		{
			FileInputStream FilePath = new FileInputStream(filePath);
			Workbook wb = Workbook.getWorkbook(FilePath); 
			Sheet sh = wb.getSheet(0); 
			userNewTemp.add(sh.getCell(0, 1).getContents().trim());
			userNewTemp.add(sh.getCell(1, 1).getContents().trim());
			Reports.add("Pass","Read Excel file from "+filePath,"Excel file has been read from "+filePath, 
					LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		catch(Exception e)
		{
			Reports.add("Fail","Read Excel file from "+filePath,e.getMessage(), 
					LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		return userNewTemp;
	}
	
	
	/**
	* @Method_Name :writeExcel
	* Generic method to write the output result into excel file
	* Method takes row number, column number, excel file name and 
	* location, text to be written into respective excel cell
	* @param row : Excel sheet row
	* @param cloumn : Excel Sheet column
	* @param text : Text user want to write
	* @param filePath : file location of excel file
	* @return void
	* @throws :FileNotFoundException
	* @author Payel
	*/
	public void writeExcel(int a,int b,String text,String filePath) throws FileNotFoundException
	{ 
	    try
	    {
	    	FileOutputStream f = new FileOutputStream(filePath,true);
	    	WritableWorkbook book = Workbook.createWorkbook(f); 
	    	WritableSheet sheet = book.createSheet("Test_Results",1);
	    	Label i = new Label(a, b, text);
	    	sheet.addCell(i);
	    	book.write(); 
	    	book.close();
	    	Reports.add("Pass","Write Excel file in "+filePath,"Excel file has been wrote in "+filePath, 
					LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
	    }
	    catch (Exception e)
	    {
	    	Reports.add("Fail","Write Excel file in "+filePath,e.getMessage(), 
					LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
	    }
	}

	
	/*
	* @Method_Name :DeleteDataFromDB
	* @Description :Generic function to delete data from database
	* @param sqlQuery : SQL query that user need to execute
	* @return void
	* @author Payel
	
	public void DeleteDataFromDB(String sqlQuery )  throws SQLException, ClassNotFoundException
	{
		// Connection object
	    Connection conn = null;
	    // Statement object
	    Statement stmt = null;
	    //Loading the required JDBC Driver class 
		try
		{
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			//Creating a connection to the database 
			conn = DriverManager.getConnection(GenericConstants.DB_URLSQLSERVER,GenericConstants.DB_USER,GenericConstants.DB_PASSWORD);
	
			System.out.println("Obtained database connection");
			if(conn == null)
			{
				System.out.println("Connection object is null");
			}
		
			//Executing SQL query and fetching the result 
			stmt = conn.createStatement(); 
			stmt.execute(sqlQuery); 
		
			System.out.print("Data is deleted successfully");

		} 
		catch (Exception e)
		{
			System.out.println("Error Trace in getConnection() : " + e.getMessage());
            e.printStackTrace();
		}

		stmt.close();
		conn.close();
	}*/


	/**
	* @Method_Name :getTableArray
	* Generic method to fetch test data from excel data source 
	* for test script execution, used along with TestNG Data Provider 
	* declaration.Method takes test data file location path, excel sheet 
	* name and test data tag name (Start and End Tag identifier for the respective 
	* test data input) and returns a two dimensional string array that is fed to Test 
	* Method for execution
	* @param xlFilePath : Excel file path
	* @param sheetName : Excel sheet name
	* @param tableName : Excel table name
	* @return String[][] : String array of data
	* @throws FileNotFoundException
	* @author Payel
	*/
	public String[][] getTableArray(String xlFilePath, String sheetName, String tableName) throws FileNotFoundException
	{
        String[][] tabArray=null;
        xlFilePath = Directory.CURRENTDir + xlFilePath;
        try
        {
            Workbook workbook = Workbook.getWorkbook(new File(xlFilePath));
            Sheet sheet = workbook.getSheet(sheetName); 
            
            int ci,cj;
            Cell tableStart=sheet.findCell(tableName);
            startRow=tableStart.getRow();
            startCol=tableStart.getColumn();

            Cell tableEnd= sheet.findCell(tableName, startCol+1,startRow+1, 100, 64000,  false);                

            endRow=tableEnd.getRow();
            endCol=tableEnd.getColumn();	
            tabArray=new String[endRow-startRow-1][endCol-startCol-1];
            ci=0;

            for (int i=startRow+1;i<endRow;i++,ci++)
            {
                cj=0;
                for (int j=startCol+1;j<endCol;j++,cj++)
                {
                    tabArray[ci][cj]=sheet.getCell(j,i).getContents();
                }
            }
            
        }
        catch(Exception e)
        {
        	
        }
        return(tabArray);
    }
	
	
	/**
	* @Method_Name :executeDatabaseAction
	* Generic method to execute a database query, store  and return 
	* the output result set.Method takes Type of Database (SQLServer or Oracle), 
	* SQL Query to execute, Query Type (SELECT, INSERT, UPDATE or DELETE) , 
	* Database connection string, Database Connection UserId and Password and returns 
	* the result set in a two dimensional Map object
	* @param dbType : Type of database application is using SQL/Oracle
	* @param sqlQuery : SQL query user wants to execute
	* @param dbURL:Database host string
	* @param uid: Database user id
	* @param pwd: Database password
	* @throws SQLException
	* @return Map<String, List<Object>> : Returns result set of the query
	* @author Arindam
	*/
	public Map<String, List<Object>> executeDatabaseAction
	(String dbType,String sqlQuery,String queryType,String dbURL,String uid,String pwd)throws SQLException
	{
		Connection conn = null ;
		Statement stmt = null;
		ResultSet result = null;
		ResultSetMetaData rsmd = null;
		Map<String, List<Object>> map = null;
		String url;
		int rows;
		try
		{
			if (dbType.equalsIgnoreCase("sqlserver"))
			{
				url = "jdbc:microsoft:sqlserver://"+dbURL ;
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				conn = DriverManager.getConnection(url,uid,pwd);
				//System.out.println("Obtained database connection");
				Reports.add("Pass","SqlServer "+url+" should get connected","Sqlserver "+url+" connection successful" , 
						LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}
			
			else if (dbType.equalsIgnoreCase("oracle"))
			{
				url = "jdbc:oracle:thin:@"+dbURL;
				Class.forName("oracle.jdbc.driver.OracleDriver");
				conn = DriverManager.getConnection(url,uid,pwd);
				//System.out.println("Obtained database connection");
				Reports.add("Pass","Oracle "+url+" should get connected","Oracle "+url+" connection successful" , 
						LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}
			else if(dbType.equals("MySQL"))
			{
				url = "jdbc:mysql://"+dbURL+"?autoReconnect=true&useSSL=false";
				Class.forName("com.mysql.jdbc.Driver");
		       	conn = DriverManager.getConnection(url,uid,pwd);
		       	Reports.add("Pass","MySQL "+url+" should get connected","MySQL "+url+" connection successful" , 
						LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}
			
			if(conn == null)
			{
				//System.out.println("Connection object is null");
				Reports.add("Warning","Connection should not be null","Connection is null" , 
						LogAs.WARNING, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}
			
			if(queryType.equalsIgnoreCase("select"))
			{
				stmt = conn.createStatement(); 
				result = stmt.executeQuery(sqlQuery);
				rsmd = result.getMetaData();
				int columns = rsmd.getColumnCount();
     	
				map = new HashMap<String, List<Object>>(columns);
     	
				for (int i = 1; i <= columns; ++i) 
				{
					map.put(rsmd.getColumnName(i), new ArrayList<Object>());
				}
				while (result.next())
				{
					for (int i = 1; i <= columns; ++i) 
					{
						map.get(rsmd.getColumnName(i)).add(result.getObject(i));
					}
				}
				Reports.add("Pass","Select query should get executed","Select query executed and resultset stored " , 
						LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				return map;
			}
			else if(queryType.equalsIgnoreCase("insert"))
			{
				stmt = conn.createStatement(); 
				rows = stmt.executeUpdate(sqlQuery);
				Reports.add("Pass","Insert query should get executed","Insert query executed" , 
						LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				return null;
			}
			else if(queryType.equalsIgnoreCase("update"))
			{
				stmt = conn.createStatement(); 
				rows = stmt.executeUpdate(sqlQuery);
				Reports.add("Pass","Update query should get executed","Update query executed " , 
						LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				return null;
			}
			else if(queryType.equalsIgnoreCase("delete"))
			{
				stmt = conn.createStatement(); 
				rows = stmt.executeUpdate(sqlQuery);
				Reports.add("Pass","Delete query should get executed","Delete query executed " , 
						LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				return null;
			}	
				
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Reports.add("Fail","Sql query should get executed",e.getMessage() , 
					LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		return map;
	}

	
	/**
	* @Method_Name :copyTextToClip
	* Method to set download file location into clipboard as per 
	* file location path provided as method argument.Uses Java method 
	* to copy text into clipboard which will be pasted on window input 
	* fields for Download / Upload file functionality  interaction with window components
	* @param fileLocation : Location where user wants to save the downloading file
	* @return void
	* @author Arindam
	*/
	public void copyTextToClipboard(String fileLocation)//file name remove as we need only location
	{
		String filePath = null;
		try
		{
			//filePath = fileLocation;
			StringSelection clipboardFilePath = new StringSelection(fileLocation);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(clipboardFilePath,null);
			//Reports.add("Pass","File path "+fileLocation+" should get copied to clipboard","File path "+fileLocation+" copied to clipboard " , 
					//LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Reports.add("Fail","File path "+fileLocation+" should get copied to clipboard",e.getMessage() , 
					LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
	}

	
	/**
	* @Method_Name :renameLatestFile
	* Generic method to rename file with latest timestamp as per user provided name
	* Method takes folder location path and new name of the file, finds the last 
	* downloaded file or file with latest timestamp and changes the file name to user provided name
	* @param fileName : File name user wants to provide to the downloading file
	* @param fileLocation : Location where user wants to save the downloading file
	* @return void
	* @author Arindam
	* @throws IOException 
	*/
	public void renameLatestFile(String fileLocation,String reName) throws IOException
	{
		File lastModifiedFile = null;
		File path = new File(fileLocation);
		String filePath = path.getCanonicalPath();
		File renamefile = new File (filePath+"\\"+reName);
		try
		{
			File folder = new File(fileLocation);
			File[] listOfFiles = folder.listFiles();
		
			if (listOfFiles == null || listOfFiles.length == 0) 
			{
				Reports.add("Info","Filename in "+fileLocation+" should get renamed to "+renamefile,
						"No File in "+fileLocation,	LogAs.INFO, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}
		
			lastModifiedFile = listOfFiles[0];
		
			for (int i = 1; i < listOfFiles.length; i++) 
			{
				if (lastModifiedFile.lastModified() < listOfFiles[i].lastModified()) 
				{
					lastModifiedFile = listOfFiles[i];
				}
			}
			if(lastModifiedFile.renameTo(renamefile))
			{
				Reports.add("Pass","Filename in "+fileLocation+" should get renamed to "+renamefile,
						"Filename in "+fileLocation+" renamed to "+renamefile, 
						LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}
		}
		catch(Exception e)
		{
			Reports.add("Fail","Filename in "+fileLocation+" should get renamed to "+renamefile,e.getMessage() , 
					LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		
	}
	
	
	/**
	* @Method_Name :verifyDownloadedFile
	* Generic method to verify file with latest timestamp as per user provided name
	* Method takes folder location path and new name of the file, finds the last 
	* downloaded file or file with latest timestamp and verify with user provided name
	* @param fileName : File name user wants to provide to the downloading file
	* @param fileLocation : Location where user wants to save the downloading file
	* @return void
	* @author Arindam
	* @throws IOException 
	*/
	public void verifyDownloadedFile(String fileLocation,String fileName) throws IOException
	{
		File lastModifiedFile = null;
		File path = new File(fileLocation);
		String filePath = path.getCanonicalPath();
		File renamefile = new File (filePath+"\\"+fileName);
		try
		{
			File folder = new File(fileLocation);
			File[] listOfFiles = folder.listFiles();
		
			if (listOfFiles == null || listOfFiles.length == 0) 
			{
				Reports.add("Info","Filename in "+fileLocation+" should get renamed to "+renamefile,
						"No File in "+fileLocation,	LogAs.INFO, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}
		
			lastModifiedFile = listOfFiles[0];
		
			for (int i = 1; i < listOfFiles.length; i++) 
			{
				if (lastModifiedFile.lastModified() < listOfFiles[i].lastModified()) 
				{
					lastModifiedFile = listOfFiles[i];
				}
			}
			if(lastModifiedFile.getName().toString().equalsIgnoreCase(fileName))
			{
				Reports.add("Pass","Filename in "+fileLocation+" should get renamed to "+renamefile,
						"Filename in "+fileLocation+" renamed to "+renamefile, 
						LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}
		}
		catch(Exception e)
		{
			Reports.add("Fail","Filename in "+fileLocation+" should get renamed to "+renamefile,e.getMessage() , 
					LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		
	}
	
	
	/**
	* @Method_Name :selectSaveAsFromDownloadManagerTrayIE
	* @Description :Robot Method for Switch to Download Manager Tray (ALT+N)-
	* Select Save As from Download Manager Tray - IE (Steps ALT+N, TAB, DOWN, DOWN, ENTER 
	* @param fileName : File name user wants to provide to the downloading file
	* @param fileLocation : Location where user wants to save the downloading file
	* @return void
	* @author Arindam
	*/
	/*public void selectSaveAsFromDownloadManagerTrayIE(String fileLocation,String fileName)
	{
		try
		{
			Robot robo = new Robot();
			switchDownloadManagerViaRobot(robo);
			pressTabViaRobot(robo,1);
			upKeyPressViaRobot(robo,1);
			downKeyPressViaRobot(robo,1);
			pressEnterViaRobot(robo,1);
			try 
			{
				Thread.sleep(10000);
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
			copyTextToClip(fileLocation,fileName);
			pasteViaRobot(robo);
			pressEnterViaRobot(robo,1);
			try 
			{
				Thread.sleep(10000);
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
			Reports.add("Pass","File should get saved using download manager","File saved using download manager" , 
					LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		catch(AWTException e)
		{
			e.printStackTrace();
			Reports.add("Fail","File should get saved using download manager","Robot event not working" , 
					LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		
	}*/
	
	
	/**
	* @Method_Name :typeTextViaRobot
	* Method to achieve entering text input into window elements
	* Method takes Robot class object, string text and utilizes 
	* Robot class key press methods to enter desired input
	* @param robo : Key board input Robot object
	* @param text : Desired input user wants to provide
	* @return void
	* @author Payel
	*/
	public void typeTextViaRobot(Robot robo, String text)
	{
		try
		{
			for (int i = 0; i < text.length(); i++)
			{			
				int ascii = (int) text.charAt(i);
				robo.keyPress(ascii);
				robo.keyRelease(ascii);
				robo.delay(1000);
				//Reports.add("Pass","Text "+text+" should get entered using Robot","Text "+text+" has been entered using robot" , 
						//LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Reports.add("Fail","Text "+text+" should get entered using Robot",e.getMessage() , 
					LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
	}
	
	
	/**
	* @Method_Name :pressTabViaRobot
	* Method to achieve multiple Tab key press
	* Method takes Robot class object, count and utilizes 
	* Robot class key press methods to click Tab key multiple times as per count provided by user
	* @param robo : Key board input Robot object
	* @param count : Number of times user wants to press the key
	* @return void
	* @author Payel
	*/
	public void pressTabViaRobot(Robot robo, int count)
	{
		try
		{
			for (int i = 0; i < count; i++)
			{			
				robo.keyPress(KeyEvent.VK_TAB);
				robo.keyRelease(KeyEvent.VK_TAB);
				robo.delay(1000);
			}
			//Reports.add("Pass","Tab key should get pressed "+count+" times using Robot","Tab key pressed "+count+" times using Robot" , 
					//LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Reports.add("Pass","Tab key should get pressed "+count+" times using Robot",e.getMessage() , 
					LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
	}
	
	
	/**
	* @Method_Name :openIEDownloadTrayViaRobot
	* Method to open download tray in Internet Explorer browser 
	* using Key Press Robot Events.Method takes Robot class object 
	* and utilizes Robot class key press methods for CTRL+J
	* @param robo : Key board input Robot object
	* @return void
	* @author Arindam
	*/
	public void openIEDownloadTrayViaRobot(Robot robo)
	{				
		try
		{
			robo.keyPress(KeyEvent.VK_CONTROL);
			robo.keyPress(KeyEvent.VK_J);
			robo.delay(1000);
			robo.keyRelease(KeyEvent.VK_J);
			robo.keyRelease(KeyEvent.VK_CONTROL);
			robo.delay(1000);
			//Reports.add("Pass","IE download tray should get opened using Robot","Opened IE download tray using Robot" , 
					//LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Reports.add("Fail","IE download tray should get opened using Robot",e.getMessage() , 
					LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
	}

	
	/**
	* @Method_Name :typeEnterViaRobot
	* Method to achieve multiple Enter key press
	* Method takes Robot class object, count and utilizes 
	* Robot class key press methods to click Enter Key multiple 
	* times as per count provided by user
	* @param robo : Key board input Robot object
	* @param count : number of time user wants to press the key
	* @return void
	* @author Arindam
	*/
	public void pressEnterViaRobot(Robot robo,int count)
	{				
		try
		{
			for (int i = 0; i < count; i++)
			{
				robo.keyPress(KeyEvent.VK_ENTER);
				robo.keyRelease(KeyEvent.VK_ENTER);
				robo.delay(1000);
			}
			//Reports.add("Pass","Enter key should get pressed "+count+" times using Robot","Enter key pressed "+count+" times using Robot" , 
					//LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Reports.add("Fail","Enter key should get pressed "+count+" times using Robot",e.getMessage() , 
					LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
	}
	
	
	/**
	* @Method_Name :copyViaRobot
	* Method to simulate Copy using Key Press Robot Events
	* Method takes Robot class object and utilizes Robot class 
	* key press methods for CTRL+C
	* @param robo : Key board input Robot object
	* @return void
	* @author Arindam
	*/
	public void copyViaRobot(Robot robo)
	{				
		try
		{
			robo.keyPress(KeyEvent.VK_CONTROL);
			robo.keyPress(KeyEvent.VK_C);
			robo.delay(1000);
			robo.keyRelease(KeyEvent.VK_C);
			robo.keyRelease(KeyEvent.VK_CONTROL);
			robo.delay(1000);
			//Reports.add("Pass","Text should get copied using robot","Text copied using Robot" , 
					//LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Reports.add("Fail","Text should get copied using robot",e.getMessage() , 
					LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
	}
	
	
	/**
	* @Method_Name :switchIEDownloadManagerViaRobot
	* Method to simulate switching control to Download 
	* Manager during file download in Internet Explorer browser.
	* Method takes Robot class object and utilizes Robot class key press methods for ALT+N
	* @param robo : Key board input Robot object
	* @return void
	* @author Arindam
	*/
	public void switchIEDownloadManagerViaRobot(Robot robo)
	{				
		try
		{
			robo.keyPress(KeyEvent.VK_ALT);
			robo.keyPress(KeyEvent.VK_N);
			robo.delay(1000);
			robo.keyRelease(KeyEvent.VK_N);
			robo.keyRelease(KeyEvent.VK_ALT);
			robo.delay(1000);
			//Reports.add("Pass","Control should get switched to download manager using Robot","Switched to download manager using Robot" , 
					//LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Reports.add("Fail","Control should get switched to download manager using Robot",e.getMessage() , 
					LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
	}
	
	
	/**
	* @Method_Name :pasteViaRobot
	* Method to simulate Paste using Key Press Robot Events
	* Method takes Robot class object and utilizes 
	* Robot class key press methods for CTRL+V
	* @param robo : Key board input Robot object
	* @return void
	* @author Arindam
	*/
	public void pasteViaRobot(Robot robo)
	{				
		try
		{
			robo.keyPress(KeyEvent.VK_CONTROL);
			robo.keyPress(KeyEvent.VK_V);
			robo.delay(1000);
			robo.keyRelease(KeyEvent.VK_V);
			robo.keyRelease(KeyEvent.VK_CONTROL);
			robo.delay(1000);
			//Reports.add("Pass","Text should get pasted using Robot","Text pasted using Robot" , 
					//LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Reports.add("Fail","Text should get pasted using Robot",e.getMessage() , 
					LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
	}
	
	
	/**
	* @Method_Name :selectAllViaRobot
	* Method to simulate Select All using Key Press Robot Events
	* Method takes Robot class object and utilizes Robot class key press methods for CTRL+A
	* @param robo : Key board input Robot object
	* @return void
	* @author Arindam
	*/
	public void selectAllViaRobot(Robot robo)
	{				
		try
		{
			robo.keyPress(KeyEvent.VK_CONTROL);
			robo.keyPress(KeyEvent.VK_A);
			robo.delay(1000);
			robo.keyPress(KeyEvent.VK_A);
			robo.keyPress(KeyEvent.VK_CONTROL);
			robo.delay(1000);
			//Reports.add("Pass","All text should get selected using Robot","Select all using Robot" , 
					//LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Reports.add("Fail","All text should get selected using Robot",e.getMessage() , 
					LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
			
	}
	
	
	/**
	* @Method_Name :downKeyPressViaRobot
	* Method to achieve multiple Down Arrow key press
	* Method takes Robot class object, count and utilizes 
	* Robot class key press methods to click Down Arrow Key multiple 
	* times as per count provided by user
	* @param robo : Key board input Robot object
	* @param count : Number of time user wants to press the key
	* @return void	
	* @author Arindam
	*/
	public void downKeyPressViaRobot(Robot robo,int count)
	{				
		try
		{
			for (int i = 0; i < count; i++)
			{
				robo.keyPress(KeyEvent.VK_DOWN);
				robo.keyRelease(KeyEvent.VK_DOWN);
				robo.delay(1000);
			}
			//Reports.add("Pass","Down key should get pressed "+count+" times using Robot","Down key pressed "+count+" times using Robot" , 
					//LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Reports.add("Fail","Down key should get pressed "+count+" times using Robot",e.getMessage() , 
					LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
	}
	
	
	/**
	* @Method_Name :upKeyPressViaRobot
	* Method to achieve multiple Up Arrow key press
	* Method takes Robot class object, count and utilizes 
	* Robot class key press methods to click Up Arrow Key multiple 
	* times as per count provided by user
	* @param robo : Key board input Robot object
	* @param count : Number of time user wants to press the key
	* @return void
	* @author Arindam
	*/
	public void upKeyPressViaRobot(Robot robo,int count)
	{				
		try
		{
			for (int i = 0; i < count; i++)
			{
				robo.keyPress(KeyEvent.VK_UP);
				robo.keyRelease(KeyEvent.VK_UP);
				robo.delay(1000);
			}
			//Reports.add("Pass","UP key should get pressed "+count+" times using Robot","UP key pressed "+count+" times using Robot" , 
					//LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Reports.add("Fail","Up key should get pressed "+count+" times using Robot",e.getMessage(), 
					LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
	}
	
	
	/**
	* @Method_Name :rightKeyPressViaRobot
	* Method to achieve multiple Right Arrow key press
	* Method takes Robot class object, count and utilizes 
	* Robot class key press methods to click Right Arrow Key multiple 
	* times as per count provided by user
	* @param robo : Key board input Robot object
	* @param count : Number of time user wants to press the key
	* @return void
	* @author Arindam
	*/
	public void rightKeyPressViaRobot(Robot robo,int count)
	{				
		try
		{
			for (int i = 0; i < count; i++)
			{
				robo.keyPress(KeyEvent.VK_RIGHT);
				robo.keyRelease(KeyEvent.VK_RIGHT);
				robo.delay(1000);
			}
			//Reports.add("Pass","Right key should get pressed "+count+" times using Robot","Right key pressed "+count+" times Robot" , 
					//LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Reports.add("Fail","Right key should get pressed "+count+" times using Robot",e.getMessage() , 
					LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
	}
	
	
	/**
	* @Method_Name :leftKeyPressViaRobot
	* Method to achieve multiple Left Arrow key press
	* Method takes Robot class object, count and utilizes 
	* Robot class key press methods to click Left Arrow Key multiple 
	* times as per count provided by user
	* @param robo : Key board input Robot object
	* @param count : Number of time user wants to press the key
	* @return void
	* @author Arindam
	*/
	public void leftKeyPressViaRobot(Robot robo,int count)
	{				
		try	
		{
			for (int i = 0; i < count; i++)
			{
				robo.keyPress(KeyEvent.VK_LEFT);
				robo.keyRelease(KeyEvent.VK_LEFT);
				robo.delay(1000);
			}
			//Reports.add("Pass","Left key should get pressed "+count+" times using Robot","Left key pressed "+count+" times using Robot" , 
					//LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Reports.add("Fail","Left key should get pressed "+count+" times using Robot",e.getMessage() ,
					LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
	}
	
	
    
 	
 	/**
	* @Method_Name :readPropertiesFile
	* Method to read test configuration related input values 
	* stored in properties file
	* Method takes key  input and properties file location path and 
	* returns the corresponding key value pair from the properties file
	* @param key : Key name whose value you want to fetch
	* @param fileLocation : Place where file is stored
	* @return String
	* @throws FileNotFoundException,IOException
	* @author Arindam
	*/
 	public String readPropertiesFile(String key,String fileLocation) throws FileNotFoundException,IOException
 	{
		
 		File file = new File(fileLocation);
 		String retVal = null;
 		FileInputStream fileInput = null;
 		
 		try 
 		{
 			fileInput = new FileInputStream(file);
 			Properties prop = new Properties();
 			prop.load(fileInput);
 			retVal=prop.getProperty(key);
 			//Reports.add("Pass","Property file should get loaded from "+fileLocation,"Property file has been read from "+fileLocation , 
 					//LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
 		} 
 		catch (Exception e) 
 		{
 			e.printStackTrace();
 			Reports.add("Fail","Property file should get loaded from "+fileLocation,e.getMessage() , 
 					LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
 		} 		
 		return retVal;
 	}
	
 	/**
	* @Method_Name :getCurretntMonth
	* Method to get the current calender month 
	* @return String
	* @author Subrat
	*/
 	public String getCurretntMonth()
	{
		Calendar now = Calendar.getInstance();
	    int year = now.get(Calendar.YEAR);
	    int month = now.get(Calendar.MONTH) + 1;
	    int data = now.get(Calendar.DATE);
	    String time  = new SimpleDateFormat("MMM").format(now.getTime());
	    int iMonth = now.get(Calendar.MONTH) + 1;
	    String txtMonth = now.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
	    return txtMonth;
	    	    
		
	}
	/**
	* @Method_Name :getPOST
	* Method to POST url and get return JSON output
	* @param url : web service URL
	* @param serviceID : serviceID of the Url
	* @return String 
	* @author Subrat
	*/
	public static String getPOST(String url,String serviceID) 
	{
		String line;
	    StringBuffer jsonString = new StringBuffer();
	    try 
	    {

	        URL restURL = new URL(url);
	
	        String payload="{\"serviceId\":\""+serviceID+"\"}";

	        HttpURLConnection connection = (HttpURLConnection) restURL.openConnection();

	        connection.setDoInput(true);
	        connection.setDoOutput(true);
	        connection.setRequestMethod("POST");
	        connection.setRequestProperty("Accept", "application/json");
	        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
	        OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
	        writer.write(payload);
	        writer.close();
	        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	        while ((line = br.readLine()) != null) 
	        {
	                jsonString.append(line);
	        }
	        br.close();
	        connection.disconnect();
	    } catch (Exception e) 
	    {
	    	Reports.add("Pass","REST url"+url+"and serviceID"+serviceID+"should POST",
	    			"REST url"+url+"and serviceID"+serviceID+"has been POST",
	    			LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
	    }
	    String str= jsonString.toString();
	    return jsonString.toString();
	}
	
	/**
	* @Method_Name :getElementJSON
	* Method to get JSON element and store them as String array
	* @param url : web service URL
	* @param arrayName : JSON input
	* @param serviceID : serviceID of the Url
	* @return String 
	* @author Subrat
	*/
	public String[] getElementJSON(String arrayName,String url, String serviceID) 
			throws ClientProtocolException, IOException, SAXException, ParserConfigurationException, JSONException 
	{
		String resultItems="";
		String[] parts= null;
		String result= getPOST(url,serviceID);
		String dataFinal= "";
		JSONArray option_checkElems= null;
		String option_check= "";
		JSONObject jo = new JSONObject(result);
		try
		{
			JSONArray jsonArray= jo.getJSONArray(arrayName);
			int jsonArrayLength= jsonArray.length();
			for(int i=0;i<=jsonArrayLength-1;i++)
			{
				String one=jsonArray.getJSONObject(i).get("FieldName").toString(); // The key in each of the JSON elements
				String two=jsonArray.getJSONObject(i).get("Label").toString();
				String three=jsonArray.getJSONObject(i).get("DisplayType").toString();
				String four=jsonArray.getJSONObject(i).get("ToolTip").toString();
				String five=jsonArray.getJSONObject(i).get("IsMandatory").toString();
				if(three.equals("checkbox")||three.equals("options"))
				{
					option_checkElems= jsonArray.getJSONObject(i).getJSONArray("OptionsList");
					for(int k=0;k<=option_checkElems.length()-1;k++)
					{
						if(k==0)
						{
							option_check= option_checkElems.getJSONObject(k).get("OptionName").toString();
						}
						else
						{
							option_check= option_check + "-" + option_checkElems.getJSONObject(k).get("OptionName").toString();
						}
					}	
				}
				dataFinal= one+":"+two+":"+three+":"+four+":"+five+":"+option_check;
				parts[i]=dataFinal;
				dataFinal="";
			}
		}
		catch(Exception e)
		{
			Reports.add("Fail","JSON input string should convert to String Array",
	    			e.getMessage(),LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		return parts;
}
	
 	
}