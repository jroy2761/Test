package selenium_framework;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;  
import javax.mail.internet.*;  
  

public class EmailTestReport 
{
	public static void sendEmail()
	{
	 	Properties mailProp = System.getProperties();
	 	mailProp.put("mail.smtp.starttls.enable",Directory.mailStart);
	 	mailProp.put("mail.smtp.host", Directory.mailHost);
	 	mailProp.put("mail.smtp.user", Directory.mailUSer);
	 	mailProp.put("mail.smtp.password", Directory.mailPassword);
	 	mailProp.put("mail.smtp.port", Directory.mailPort);
	 	mailProp.put("mail.smtp.auth", Directory.mailAuth);
			    	 
		Session session = Session.getDefaultInstance(mailProp);
		MimeMessage message = new MimeMessage(session);
		ArrayList ToArray=new ArrayList();
		ArrayList CcArray=new ArrayList();
		try 
		{
			message.setFrom(new InternetAddress(Directory.mailUSer));
			
			StringTokenizer stTo = new StringTokenizer(Directory.mailRecvTo,",");
			while (stTo.hasMoreTokens()) 
			{
				ToArray.add(stTo.nextToken());
			}
						
			int sizeTo = ToArray.size();
			InternetAddress[] addressTo = new InternetAddress[sizeTo];
			for (int i = 0; i < sizeTo; i++)
			{
				addressTo[i] = new InternetAddress(ToArray.get(i).toString());
			}
			message.setRecipients(Message.RecipientType.TO, addressTo);
			
			StringTokenizer stCc = new StringTokenizer(Directory.mailRecvCc,",");
			while (stCc.hasMoreTokens()) 
			{
				CcArray.add(stCc.nextToken());
			}
			int sizeCc = CcArray.size();
			InternetAddress[] addressCc = new InternetAddress[sizeCc];
			for (int i = 0; i < sizeCc; i++)
			{
				addressCc[i] = new InternetAddress(CcArray.get(i).toString());
			}
			message.setRecipients(Message.RecipientType.CC, addressCc);
			DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");
			Date date = new Date();
			message.setSubject(Directory.projName+"_"+dateFormat.format(date)+"_"+Directory.reportHeader);
			message.setText(Directory.projName+"_"+dateFormat.format(date)+"_"+Directory.reportHeader);
		 	BodyPart objMessageBodyPart = new MimeBodyPart();
		 	objMessageBodyPart.setText("Hi All," +
		 	"\n\nKindly download and extract the attachment to view test execution report."+
		 	"\nPlease find test execution summary below : "+
		 	"\n\nTotal Number of Test Cases Executed:"+CurrentRunPageWriter.totalTestCases+
		 	"\nTotal Number of Test Cases Passed:"+CurrentRunPageWriter.passedTestCases+
		 	"\nTotal Number of Test Cases Failed:"+CurrentRunPageWriter.failedTestCases+
		 	"\nTotal Number of Test Cases Skiped:"+CurrentRunPageWriter.skipedTestCases);
		 	Multipart multipart = new MimeMultipart();
		 	multipart.addBodyPart(objMessageBodyPart);
		 	objMessageBodyPart = new MimeBodyPart();
		 	String filename = Directory.REPORTSDIRName+".zip";
		 	DataSource source = new FileDataSource(filename);
		    objMessageBodyPart.setDataHandler(new DataHandler(source));
		 	objMessageBodyPart.setFileName(filename);
		    multipart.addBodyPart(objMessageBodyPart);
		    message.setContent(multipart);
		 	Transport transport = session.getTransport("smtp");
		 	transport.connect(Directory.mailHost, Directory.mailUSer, Directory.mailPassword);
		 	transport.sendMessage(message, message.getAllRecipients());
		 	transport.close();
		 }
		catch (AddressException ae) 
		{
			ae.printStackTrace();
		}
		catch (MessagingException me) 
		{
		 	me.printStackTrace();
		}
	}
}
