package selenium_framework;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


public class ZipTestResult 
{
	public static void zipResult() throws IOException
	{
		File directoryToZip = new File(Directory.REPORTSDir);
		List<File> fileList = new ArrayList<File>();
		getAllFiles(directoryToZip, fileList);
		writeZipFile(directoryToZip, fileList);
	}
	
	public static void getAllFiles(File dir, List<File> fileList) 
	{
		File[] files = dir.listFiles();
		for (File file : files) 
		{
			fileList.add(file);
			if (file.isDirectory()) 
			{
				getAllFiles(file, fileList);
			} 
		}
	}
	
	public static void writeZipFile(File directoryToZip, List<File> fileList) 
	{
		try 
		{
			FileOutputStream fos = new FileOutputStream(directoryToZip.getName() + ".zip");
			ZipOutputStream zos = new ZipOutputStream(fos);
			for (File file : fileList) 
			{
				if (!file.isDirectory()) 
				{ 
					addToZip(directoryToZip, file, zos);
				}
			}
			zos.close();
			fos.close();
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public static void addToZip(File directoryToZip, File file, ZipOutputStream zos) throws FileNotFoundException,IOException 
	{
		FileInputStream fis = new FileInputStream(file);
		String zipFilePath = file.getCanonicalPath().substring(directoryToZip.getCanonicalPath().length() + 1,file.getCanonicalPath().length());
		ZipEntry zipEntry = new ZipEntry(zipFilePath);
		zos.putNextEntry(zipEntry);

		byte[] bytes = new byte[1024];
		int length;
		while ((length = fis.read(bytes)) >= 0) 
		{
			zos.write(bytes, 0, length);
		}
		zos.closeEntry();
		fis.close();
	}
}