package apiTest;

public class PayLoad {


	public static String getPostData()
	{

		String b ="{"+
				"\"location\": {"+
				"\"lat\": -33.8669710,"+
				"\"lng\": 151.1958750"+
				"},"+
				"\"accuracy\": 50,"+
				"\"name\": \"Google Shoes!\","+
				"\"phone_number\": \"(02) 9374 4000\","+
				"\"address\": \"48 Pirrama Road, Pyrmont, NSW 2009, Australia\","+
				"\"types\": [\"shoe_store\"],"+
				"\"website\": \"http://www.google.com.au/\","+
				"\"language\": \"en-AU\""+
				"}";


		return b;
	}

	public static String jiraIssueCreate()
	{

		String b = "{"+
				"\"fields\": {"+
				"\"project\":{"+
				"\"key\": \"DEM\""+
				"},"+
				"\"summary\": \"Issue creation using Rest\","+
				"\"description\": \"Creating bug from another machine 30th July DEMO\","+
				"\"issuetype\": {"+
				"\"name\": \"Bug\""+
				"}"+
				"}}";


		return b;
	}
}
