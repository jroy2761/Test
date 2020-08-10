package selenium_framework;



import selenium_framework.ReportLabels;
import selenium_framework.ReporterException;
import selenium_framework.HTMLDesignFilesWriter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.FileImageOutputStream;
import javax.mail.internet.InternetAddress;
import javax.swing.filechooser.FileFilter;

import org.apache.commons.io.FileUtils;

public class Directory 
{
	  public static final String ATU_VERSION = "v1";
	  public static final String CURRENTDir = System.getProperty("user.dir");
	  public static final String SEP = System.getProperty("file.separator");
	  public static String REPORTSDIRName = "Reports";
	  public static String REPORTSDir = CURRENTDir + SEP + REPORTSDIRName;
	  public static String RESULTSDir = REPORTSDir + SEP + "Results";
	  public static String HTMLDESIGNDIRName = "HTML_Design_Files";
	  public static String HTMLDESIGNDir = REPORTSDir + SEP + HTMLDESIGNDIRName;
	  public static String CSSDIRName = "CSS";
	  public static String CSSDir = HTMLDESIGNDir + SEP + CSSDIRName;
	  public static String IMGDIRName = "IMG";
	  public static String IMGDir = HTMLDESIGNDir + SEP + IMGDIRName;
	  public static String JSDIRName = "JS";
	  public static String JSDir = HTMLDESIGNDir + SEP + JSDIRName;
	  public static String RUNName = "Run_";
	  public static String RUNDir = RESULTSDir + SEP + RUNName;
	  public static String SETTINGSFile = RESULTSDir + SEP + "Settings.properties";
	  public static String PROPERTYfile =  "resource.properties";
	  public static final char JS_SETTINGS_DELIM = ';';
	  public static final String REPO_DELIM = "##@##@##";
	  public static final char JS_FILE_DELIM = ',';
	  public static final String MENU_LINK_NAME = "Run ";
	  public static final String SCREENSHOT_TYPE = "PNG";
	  public static final String SCREENSHOT_EXTENSION = ".PNG";
	  public static final String RESOURCEDirName = "Resources";
	  public static final String RESOURCEDir = CURRENTDir + SEP + RESOURCEDirName;
	  public static final String RESOURCEImgDir = RESOURCEDir + SEP + IMGDIRName;
	  public static String logo = null;
	  public static String chromeDriverPath = null;
	  public static String ieDriverPath = null;
	  public static String geckoDriverPath = null;
	  public static String dataSheetPath = null ;
	  public static String authorName = null;
	  public static String phantomJSPath = null;
	  public static String testNGXmlPath = null;
	  public static String SCREENSHOT_DIRName = "img";
	  public static String mailFlag = null;
	  public static String mailStart = null;
	  public static String mailHost = null;
	  public static String mailUSer = null;
	  public static String mailPassword = null;
	  public static String mailPort = null;
	  public static String mailAuth = null ;
	  public static String mailRecvTo = null ;
	  public static String mailRecvCc = null ;
	  public static String projName = null ;
	  public static String winiumPath = null ;
	  public static String reportHeader = null;
	  public static boolean generateConfigReports = true;
	  public static boolean takeScreenshot = true;
	  public static boolean continueExecutionAfterStepFailed = true;

	  
	    
	  public static void init() throws ReporterException, IOException 
	  {
		  Properties prop = new Properties();
		  InputStream input = null;
		  try
		  {
	    	 input = new FileInputStream(PROPERTYfile);
	    	 prop.load(input);
	    	 reportHeader = prop.getProperty("proj.header.text").trim();
	    	 logo = CURRENTDir+prop.getProperty("proj.header.logo").trim();
	    	 geckoDriverPath = prop.getProperty("proj.gecko.path").trim();
	    	 chromeDriverPath = prop.getProperty("proj.chromedriver.path").trim();
	    	 ieDriverPath = prop.getProperty("proj.iedriver.path").trim();
	    	 phantomJSPath = prop.getProperty("proj.phantom.path").trim();
	    	 winiumPath = prop.getProperty("proj.winium.path").trim();
	    	 authorName = prop.getProperty("proj.author.name").trim();
	    	 testNGXmlPath = prop.getProperty("proj.xml.path").trim();
	    	 dataSheetPath = prop.getProperty("proj.data.sheet").trim();
	    	 mailFlag = prop.getProperty("mail.send.flag").trim();
	    	 mailStart = prop.getProperty("mail.smtp.starttls.enable").trim();	
	   	     mailHost = prop.getProperty("mail.smtp.host").trim();	
	   	     mailUSer = prop.getProperty("mail.smtp.sender").trim();	
	   	     mailPassword = prop.getProperty("mail.smtp.password").trim();	
	   	     mailPort = prop.getProperty("mail.smtp.port").trim();	
	   	     mailAuth = prop.getProperty("mail.smtp.auth").trim();
	   	     mailRecvTo = prop.getProperty("mail.smtp.recvto").trim();
	   	     mailRecvCc = prop.getProperty("mail.smtp.recvcc").trim();
	   	     projName = prop.getProperty("proj.client.name").trim();
	   	     String str2 = reportHeader +"of"+ projName;	   	     
	    	try
	    	{
	    		if ((str2 != null) && (str2.length() > 0)) 
	    		{
	    			ReportLabels.HEADER_TEXT.setLabel(str2);
	    		}
	    	}
	    	catch (Exception localException5)
	 	   {
	 	    		throw new ReporterException(localException5.toString());
	 	   }
	   }
	   catch (Exception localException5)
	   {
	    		throw new ReporterException(localException5.toString());
	   }	 
		
 }
	  
	  public static void mkDirs(String paramString)
	  {
	    File localFile = new File(paramString);
	    if (!localFile.exists()) {
	      localFile.mkdirs();
	    }
	  }
	  
	  public static boolean exists(String paramString)
	  {
	    File localFile = new File(paramString);
	    return localFile.exists();
	  }
	  
	  public static void verifyRequiredFiles() throws ReporterException, IOException
	  {
	    init();
	    mkDirs(REPORTSDir);
	    if (!exists(RESULTSDir))
	    {
	      mkDirs(RESULTSDir);
	      SettingsFile.initSettingsFile();
	    }
	    if (!exists(HTMLDESIGNDir))
	    {
	      mkDirs(HTMLDESIGNDir);
	      mkDirs(CSSDir);
	      mkDirs(JSDir);
	      mkDirs(IMGDir);
	 
	      HTMLDesignFilesWriter.writeCSS();
	      HTMLDesignFilesWriter.writeIMG();
	      HTMLDesignFilesWriter.writeJS();
	    }
	    if ((logo != null) && (logo.length() > 0))
	    {
	      String str = new File(logo).getName();
	      if (!new File(IMGDir + SEP + str).exists()) 
	      {
	    	  CopyFile(logo,IMGDir + SEP + str);
	      }
	      ReportLabels.PROJ_LOGO.setLabel(str);
	    }
	  }
	  
	  public static void copyImage(String paramString) throws ReporterException
	  {
	    File localFile = new File(paramString);
	    if (!localFile.exists()) {
	      return;
	    }
	    FileImageInputStream localFileImageInputStream = null;
	    FileImageOutputStream localFileImageOutputStream = null;
	    try
	    {
	      localFileImageInputStream = new FileImageInputStream(new File(paramString));
	      localFileImageOutputStream = new FileImageOutputStream(new File(IMGDir + SEP + localFile.getName()));
	      int i = 0;
	      while ((i = localFileImageInputStream.read()) >= 0) 
	      {
	        localFileImageOutputStream.write(i);
	      }
	      localFileImageOutputStream.close();
	      return;
	    }
	    catch (Exception localException2) {}finally
	    {
	      try
	      {
	        localFileImageInputStream.close();
	        localFileImageOutputStream.close();
	        localFile = null;
	      }
	      catch (Exception localException4)
	      {
	        localFileImageInputStream = null;
	        localFileImageOutputStream = null;
	        localFile = null;
	      }
	    }
	  }
	  
	  public static void CopyFile (String source,String dest)
	  {
				File sourceFile = new File(source);
				File destFile = new File(dest);
				if (!sourceFile.exists()) 
				{
					return;
				}
				if (!destFile.exists())
				 {
					try 
					{
						destFile.createNewFile();
					} 
					catch (IOException e) 
					{
						e.printStackTrace();
					}
				}
				FileChannel source1 = null;
				FileChannel destination = null;

				try 
				{
					source1 = new FileInputStream(sourceFile).getChannel();
					destination = new FileOutputStream(destFile).getChannel();
					if (destination != null && source1 != null) 
					{
						destination.transferFrom(source1, 0, source1.size());
					}

				} 
				catch (FileNotFoundException e) 
				{
					e.printStackTrace();
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
				finally 
				{
					if (source1 != null) 
					{
						try 
						{
							source1.close();
						} 
						catch (IOException e)
						 {
							e.printStackTrace();
						}
					}
					if (destination != null) 
					{
						try 
						{
							destination.close();
						}
						 catch (IOException e)
						 {
							e.printStackTrace();
						}
					}
				}
			}
}
