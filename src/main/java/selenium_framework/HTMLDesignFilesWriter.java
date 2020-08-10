package selenium_framework;

//import testng.Directory;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.channels.FileChannel;

import javax.imageio.stream.FileImageOutputStream;

public class HTMLDesignFilesWriter 
{
	  public static void writeCSS() throws IOException
	  {
	    copyFile("/styles/design.css", Directory.CSSDir);
	    copyFile("/styles/jquery.jqplot.css", Directory.CSSDir);
	    copyFile("/styles/jquery-ui.min.css", Directory.CSSDir);
	  }
	  
	  public static void writeJS() throws IOException
	  {
	    copyFile("/js/excanvas.js", Directory.JSDir);
	    copyFile("/js/jqplot.barRenderer.min.js", Directory.JSDir);
	    copyFile("/js/jqplot.categoryAxisRenderer.min.js", Directory.JSDir);
	    copyFile("/js/jqplot.highlighter.min.js", Directory.JSDir);
	    copyFile("/js/jqplot.pieRenderer.min.js", Directory.JSDir);
	    copyFile("/js/jqplot.pointLabels.min.js", Directory.JSDir);
	    copyFile("/js/jquery.jqplot.min.js", Directory.JSDir);
	    copyFile("/js/jquery.min.js", Directory.JSDir);
	    copyFile("/js/jquery-ui.min.js", Directory.JSDir);
	  }
	  
	  public static void writeIMG() throws IOException
	  {
	    copyImage("/images/fail.png", Directory.IMGDir);
	    copyImage("/images/pass.png", Directory.IMGDir);
	    copyImage("/images/skip.png", Directory.IMGDir);
	    copyImage("/images/atu.jpg", Directory.IMGDir);
	    copyImage("/images/loginfo.png", Directory.IMGDir);
	    copyImage("/images/logpass.png", Directory.IMGDir);
	    copyImage("/images/logfail.png", Directory.IMGDir);
	    copyImage("/images/logwarning.png", Directory.IMGDir);
	  }
	  
	  /*public static void writeProp() throws FileNotFoundException
	  {
		  copyFile("/property/report.properties",Directory.PROPERTYfile);
	  }*/
	  
	  public static void copyImage(String paramString1, String paramString2) throws IOException
	  {
	    File localFile = new File(paramString1);
	    //paramString1 = localFile.getCanonicalPath();
	    //System.out.println("html copy image"+paramString1);
	    //InputStream localInputStream = HTMLDesignFilesWriter.class.getClassLoader().getResourceAsStream(paramString1); commented file is getting copied but no image is getting displayed
	    InputStream localInputStream = HTMLDesignFilesWriter.class.getClass().getResourceAsStream(paramString1);
	    BufferedReader reader = new BufferedReader(new InputStreamReader(localInputStream));
	    //System.out.println("local input stream:"+localInputStream);
	    //InputStream localInputStream = new FileInputStream(paramString1); commented as this line required for decompiled code
	    FileImageOutputStream localFileImageOutputStream = null;
	    try
	    {
	      localFileImageOutputStream = new FileImageOutputStream(new File(paramString2 + Directory.SEP + localFile.getName()));
	      //System.out.println("local File Image OutputStream"+paramString2 + Directory.SEP + localFile.getName());
	      int i = 0;
	      while ((i = localInputStream.read()) >= 0) {
	        localFileImageOutputStream.write(i);
	      }
	      localFileImageOutputStream.close();
	      return;
	    }
	    catch (Exception localException2) {}finally
	    {
	      try
	      {
	        localInputStream.close();
	        localFileImageOutputStream.close();
	        localFile = null;
	      }
	      catch (Exception localException4)
	      {
	        localInputStream = null;
	        localFileImageOutputStream = null;
	        localFile = null;
	      }
	    }
	  }
	  
	  public static void copyFile(String paramString1, String paramString2) throws IOException
	  {
	    File localFile = new File(paramString1);
	    //paramString1 = localFile.getCanonicalPath();
	    InputStream localInputStream = HTMLDesignFilesWriter.class.getClass().getResourceAsStream(paramString1);
	    BufferedReader reader = new BufferedReader(new InputStreamReader(localInputStream));
	    //InputStream localInputStream = new FileInputStream(paramString1); commented as this line required for decompiled code
	    //System.out.println("local input stream:"+HTMLDesignFilesWriter.class.getClassLoader());
	    FileOutputStream localFileOutputStream = null;
	    try
	    {
	      localFileOutputStream = new FileOutputStream(paramString2 + Directory.SEP + localFile.getName());
	      //System.out.println("local File OutputStream"+paramString2 + Directory.SEP + localFile.getName());
	      int i = 0;
	      while ((i = localInputStream.read()) >= 0) {
	    	//System.out.println(i);
	        localFileOutputStream.write(i);
	      }
	      return;
	    }
	    catch (FileNotFoundException localFileNotFoundException) {}catch (IOException localIOException) {}finally
	    {
	      try
	      {
	        localInputStream.close();
	        localFileOutputStream.close();
	        localFile = null;
	      }
	      catch (Exception localException4)
	      {
	        localInputStream = null;
	        localFileOutputStream = null;
	        localFile = null;
	      }
	    }
	  }
	  
	  /*public static void CopyFile (String source,String dest) throws IOException
	  {
		  		File tmpFile = new File(source);
		  		source = tmpFile.getCanonicalPath();
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
			}*/

}
