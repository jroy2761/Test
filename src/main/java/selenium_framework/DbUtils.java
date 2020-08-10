package selenium_framework;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import selenium_framework.CaptureScreen.ScreenshotOf;

public class DbUtils {
	static Logger logger = Logger.getLogger(DbUtils.class.getName());
	 public static String PROPERTYfile ="resource.properties";
	 public static String dbid="";
	 public static String dbPassword="";
	 public static String dbType="";
	 public static String dbname="";
	 public static String dbhostport="";
	 public static String dbURL="";

	
	/**
	* @Method_Name :executeDatabaseAction
	* Generic method to execute a database query, store  and return 
	* the output result set.Method takes Type of Database (SQLServer or Oracle), 
	* SQL Query to execute, Query Type (SELECT, INSERT, UPDATE or DELETE) , 
	* Database connection string, Database Connection UserId and Password and returns 
	* the result set in a two dimensional Map object
	* 
	* @param sqlQuery : SQL query user wants to execute
	* @param querytype:Type of Operation that User want to perform on Particular DB(INSERT/UPDATE?SELECT?DELETE)
	* 
	* @throws SQLException
	* @return Map<String, List<Object>> : Returns result set of the query
	* @author Sabyasachi
	 * @throws IOException 
	*/
	public Map<String, List<Object>> executeDatabaseAction
	(String sqlQuery,String queryType)throws SQLException,IOException
	{
		Map<String, List<Object>> map = null;
		 Properties prop = new Properties();
	     InputStream input = null;
	  
    	 input = new FileInputStream(PROPERTYfile);
    	 prop.load(input);
    	 dbid=prop.getProperty("proj.db.userid").trim();
    	 dbPassword=prop.getProperty("proj.db.password").trim();
    	 dbType=prop.getProperty("proj.db.type").trim();
    	 dbname=prop.getProperty("proj.db.name").trim();
    	 dbhostport=prop.getProperty("proj.db.hostport").trim();
    	 dbURL=dbhostport+dbname;
    	 logger.info("the Input Values:"+dbid+" "+dbPassword+" "+dbType+" "+dbURL);
    	 Connection conn = null ;
    		Statement stmt = null;
    		ResultSet result = null;
    		ResultSetMetaData rsmd = null;
    		String url;
    		int rows;
    	 
    	 
    	try
		  {
			
		   if (dbType.equalsIgnoreCase("sqlserver"))
			{
				url = "jdbc:microsoft:sqlserver://"+dbURL ;
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				conn = DriverManager.getConnection(url,dbid,dbPassword);
				//System.out.println("Obtained database connection");
				Reports.add("Pass","SqlServer "+url+" should get connected","Sqlserver "+url+" connection successful" , 
						LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}
			
			else if (dbType.equalsIgnoreCase("oracle"))
			{
				url = "jdbc:oracle:thin:@"+dbURL;
				Class.forName("oracle.jdbc.driver.OracleDriver");
				conn = DriverManager.getConnection(url,dbid,dbPassword);
				//System.out.println("Obtained database connection");
				Reports.add("Pass","Oracle "+url+" should get connected","Oracle "+url+" connection successful" , 
						LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}
			else if(dbType.equals("MySQL"))
			{
				url = "jdbc:mysql://"+dbURL+"?autoReconnect=true&useSSL=false&serverTimezone=UTC";
				logger.info("url is"+url);
				Class.forName("com.mysql.cj.jdbc.Driver");
		       	conn = DriverManager.getConnection(url,dbid,dbPassword);
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
				logger.info("No of rows affected "+rows);
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


}
