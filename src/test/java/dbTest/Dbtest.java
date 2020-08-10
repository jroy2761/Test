package dbTest;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jfree.util.Log;
import org.testng.annotations.Test;

import com.fasterxml.jackson.annotation.JsonFormat.Value;

import selenium_framework.AUTBaseTest;
import selenium_framework.CaptureScreen;
import selenium_framework.CommonUtils;
import selenium_framework.DbUtils;
import selenium_framework.LogAs;
import selenium_framework.Reports;
import selenium_framework.CaptureScreen.ScreenshotOf;

public class Dbtest {
	static Logger logger = Logger.getLogger(Dbtest.class.getName());
	DbUtils db=new DbUtils();
	Map<String, List<Object>> map = null;
	String selectquery="select * from employeedetails";
	String insertQuery="insert into employeedetails values(10,'Virat',32);";
	String newEmployeeName = "Virat";
	@Test
	public void f() {

		try {

			//String dbURL="localhost:3306/test";

			db.executeDatabaseAction(insertQuery, "insert");
			map=db.executeDatabaseAction(selectquery, "select");
			/**
			 * Hence we are taking the HashMAp<String,List<Object>, we can not directly apply the hashmap containdvalue functions,
			 * hence we are takng another List<object>val to store the value of particula key Employeename and iterate over it .
			 */
			List<Object>val=map.get("empname");
			logger.info(val);
			//String mod=val.toString();
			
			for(int i=0;i<val.size();i++)
			{
				System.out.println(val.get(i).toString());
				if(val.get(i).toString().equalsIgnoreCase(newEmployeeName))
				{
					System.out.println("matched");
					Reports.add("Pass","Verify "+ newEmployeeName +" has been added",newEmployeeName + " has been added", LogAs.PASSED, 
							new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
					break;
				}
			}
			
			
			/*if(map.containsValue(newEmployeeName)==true)
			{
				Log.info("Passed");
				Reports.add("Pass","Verify "+ newEmployeeName +" has been added",newEmployeeName + " has been added", LogAs.PASSED, 
						new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}*/
			
			/*List<Object> value = null;
			// Need to check why map.size not working
			for(int i=0;i<50;i++) {
				value = map.get("empname");
			}
			for(int i=0;i<50;i++) {
				System.out.println("Values are " + value.get(i).toString());
				if(value.get(i).toString().contentEquals(newEmployeeName)) {
					Reports.add("Pass","Verify "+ newEmployeeName +" has been added",newEmployeeName + " has been added", LogAs.PASSED, 
							new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
					break;
				}
			}*/
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
