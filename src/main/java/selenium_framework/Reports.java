package selenium_framework;

import selenium_framework.LogAs;
import selenium_framework.AuthorDetails;
import selenium_framework.Directory;
import selenium_framework.Platform;
import selenium_framework.Steps;
import selenium_framework.CaptureScreen;

import com.google.common.io.Files;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.imageio.ImageIO;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.winium.WiniumDriver;
import org.testng.ITestResult;
import org.testng.Reporter;

public class Reports
{
  private static WebDriver driver;
  private static WiniumDriver windriver;
  public static final int MAX_BAR_REPORTS = 10;
  public static final String MESSAGE = "Reporter: Preparing Reports";
  public static String indexPageDescription = "Reports Description";
  public static String currentRunDescription = "Here you can give description about the current Run";
  private static String screenShotNumber;
  private static long lastExecutionTime;
  private static long currentExecutionTime;
  public static final String EMPTY = "";
  public static final String STEP_NUM = "STEP";
  public static final String PASSED_BUT_FAILED = "passedButFailed";
  
  
  public static void setWebDriver(WebDriver paramWebDriver)
  {
    driver = paramWebDriver;
    Platform.prepareDetails(driver);
  }
  
  public static void setWindowsDriver(WiniumDriver paramWebDriver)
  {
    windriver = paramWebDriver;
    Platform.prepareDetails(windriver);
  }
  
  public static WebDriver getWebDriver()
  {
    return driver;
  }
  
  public static WiniumDriver getWindowsDriver()
  {
    return windriver;
  }
  public static void setAuthorInfo(String paramString1, String paramString2, String paramString3)
  {
    AuthorDetails localAuthorDetails = new AuthorDetails();
    localAuthorDetails.setAuthorName(paramString1);
    localAuthorDetails.setCreationDate(paramString2);
    localAuthorDetails.setVersion(paramString3);
    Reporter.getCurrentTestResult().setAttribute("authorInfo", localAuthorDetails);
  }
  
  public static void setTestCaseReqCoverage(String paramString)
  {
    Reporter.getCurrentTestResult().setAttribute("reqCoverage", paramString);
  }
  
  private static void stepFailureHandler(ITestResult paramITestResult, Steps paramSteps, LogAs paramLogAs)
  {
    if (paramLogAs == LogAs.FAILED)
    {
      buildReportData(paramSteps);
      if (Directory.continueExecutionAfterStepFailed) {
        paramITestResult.setAttribute("passedButFailed", "passedButFailed");
      } else {
        throw new ReporterStepFailedException();
      }
      return;
    }
    buildReportData(paramSteps);
  }
  
  public static void add(String paramString, LogAs paramLogAs, CaptureScreen paramCaptureScreen)
  {
    if (paramCaptureScreen != null) {
      if (paramCaptureScreen.isCaptureBrowserPage()) {
        takeBrowserPageScreenShot();
      } else if (paramCaptureScreen.isCaptureDesktop()) {
        takeDesktopScreenshot();
      } else if (paramCaptureScreen.isCaptureWebElement()) {
        takeWebElementScreenShot(paramCaptureScreen.getElement());
      }
    }
    Steps localSteps = new Steps();
    localSteps.setDescription(paramString);
    localSteps.setInputValue("");
    localSteps.setExpectedValue("");
    localSteps.setActualValue("");
    localSteps.setTime(getExecutionTime());
    localSteps.setLineNum(getLineNumDesc());
    localSteps.setScreenShot(screenShotNumber);
    localSteps.setLogAs(paramLogAs);
    //System.out.println(Reporter.getCurrentTestResult() );
    //System.out.println( localSteps );
    //System.out.println(paramLogAs);
    stepFailureHandler(Reporter.getCurrentTestResult(), localSteps, paramLogAs);
  }
  
  public static void add(String paramString1, String paramString2, LogAs paramLogAs, CaptureScreen paramCaptureScreen)
  {
    if (paramCaptureScreen != null) {
      if (paramCaptureScreen.isCaptureBrowserPage()) {
        takeBrowserPageScreenShot();
      } else if (paramCaptureScreen.isCaptureDesktop()) {
        takeDesktopScreenshot();
      } else if (paramCaptureScreen.isCaptureWebElement()) {
        takeWebElementScreenShot(paramCaptureScreen.getElement());
      }
    }
    Steps localSteps = new Steps();
    localSteps.setDescription(paramString1);
    localSteps.setInputValue(paramString2);
    localSteps.setExpectedValue("");
    localSteps.setActualValue("");
    localSteps.setTime(getExecutionTime());
    localSteps.setLineNum(getLineNumDesc());
    localSteps.setScreenShot(screenShotNumber);
    localSteps.setLogAs(paramLogAs);
    stepFailureHandler(Reporter.getCurrentTestResult(), localSteps, paramLogAs);
  }
  
  public static void add(String paramString1, String paramString2, String paramString3, LogAs paramLogAs, CaptureScreen paramCaptureScreen)
  {
    if (paramCaptureScreen != null) {
      if (paramCaptureScreen.isCaptureBrowserPage()) {
        takeBrowserPageScreenShot();
      } else if (paramCaptureScreen.isCaptureDesktop()) {
        takeDesktopScreenshot();
      } else if (paramCaptureScreen.isCaptureWebElement()) {
        takeWebElementScreenShot(paramCaptureScreen.getElement());
      }
    }
    Steps localSteps = new Steps();
    localSteps.setDescription(paramString1);
    localSteps.setInputValue("");
    localSteps.setExpectedValue(paramString2);
    localSteps.setActualValue(paramString3);
    localSteps.setTime(getExecutionTime());
    localSteps.setLineNum(getLineNumDesc());
    localSteps.setScreenShot(screenShotNumber);
    localSteps.setLogAs(paramLogAs);
    stepFailureHandler(Reporter.getCurrentTestResult(), localSteps, paramLogAs);
  }
  
  public static void add(String paramString1, String paramString2, String paramString3, String paramString4, LogAs paramLogAs, CaptureScreen paramCaptureScreen)
  {
    if (paramCaptureScreen != null) {
      if (paramCaptureScreen.isCaptureBrowserPage()) {
        takeBrowserPageScreenShot();
      } else if (paramCaptureScreen.isCaptureDesktop()) {
        takeDesktopScreenshot();
      } else if (paramCaptureScreen.isCaptureWebElement()) {
        takeWebElementScreenShot(paramCaptureScreen.getElement());
      }
    }
    Steps localSteps = new Steps();
    localSteps.setDescription(paramString1);
    localSteps.setInputValue(paramString2);
    localSteps.setExpectedValue(paramString3);
    localSteps.setActualValue(paramString4);
    localSteps.setTime(getExecutionTime());
    localSteps.setLineNum(getLineNumDesc());
    localSteps.setScreenShot(screenShotNumber);
    localSteps.setLogAs(paramLogAs);
    stepFailureHandler(Reporter.getCurrentTestResult(), localSteps, paramLogAs);
  }
  
  private static void buildReportData(Steps paramSteps)
  {
    screenShotNumber = null;
    int i = Reporter.getOutput().size() + 1;
    Reporter.getCurrentTestResult().setAttribute("STEP" + i, paramSteps);
    Reporter.log("STEP" + i);
  }
  
  private static String getExecutionTime()
  {
    currentExecutionTime = System.currentTimeMillis();
    long l = currentExecutionTime - lastExecutionTime;
    if (l > 1000L)
    {
      l /= 1000L;
      lastExecutionTime = currentExecutionTime;
      return l + " Sec";
    }
    lastExecutionTime = currentExecutionTime;
    return l + " Milli Sec";
  }
  
  private static String getLineNumDesc()
  {
    String str = "" + Thread.currentThread().getStackTrace()[3].getLineNumber();
    return str;
  }
  
  private static void takeDesktopScreenshot()
  {
    if (!Directory.takeScreenshot) {
      return;
    }
    if (getWindowsDriver() == null)
    {
      screenShotNumber = null;
      return;
    }
    ITestResult localITestResult = Reporter.getCurrentTestResult();
    String str = localITestResult.getAttribute("reportDir").toString() + Directory.SEP + Directory.IMGDIRName;
    screenShotNumber = Reporter.getOutput(Reporter.getCurrentTestResult()).size() + 1 + "";
    File localFile = new File(str + Directory.SEP + screenShotNumber + ".PNG");
    try
    {
      Rectangle localRectangle = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
      BufferedImage localBufferedImage = new Robot().createScreenCapture(localRectangle);
      ImageIO.write(localBufferedImage, "PNG", localFile);
    }
    catch (Exception localException)
    {
      screenShotNumber = null;
    }
  }
  
  private static void takeBrowserPageScreenShot()
  {
    if (!Directory.takeScreenshot) {
      return;
    }
    if (getWebDriver() == null)
    {
      screenShotNumber = null;
      return;
    }
    ITestResult localITestResult = Reporter.getCurrentTestResult();
    String str = localITestResult.getAttribute("reportDir").toString() + Directory.SEP + Directory.IMGDIRName;
    screenShotNumber = Reporter.getOutput(Reporter.getCurrentTestResult()).size() + 1 + "";
    File localFile = new File(str + Directory.SEP + screenShotNumber + ".PNG");
    try
    {
      WebDriver localWebDriver;
      if (getWebDriver().getClass().getName().equals("org.openqa.selenium.remote.RemoteWebDriver")) {
        localWebDriver = new Augmenter().augment(getWebDriver());
      } else {
        localWebDriver = getWebDriver();
      }
      if ((localWebDriver instanceof TakesScreenshot))
      {
        byte[] arrayOfByte = (byte[])((TakesScreenshot)localWebDriver).getScreenshotAs(OutputType.BYTES);
        Files.write(arrayOfByte, localFile);
      }
      else
      {
        screenShotNumber = null;
      }
    }
    catch (Exception localException)
    {
      screenShotNumber = null;
    }
  }
  
  private static void takeWebElementScreenShot(WebElement paramWebElement)
  {
    if (!Directory.takeScreenshot) {
      return;
    }
    if (getWebDriver() == null)
    {
      screenShotNumber = null;
      return;
    }
    ITestResult localITestResult = Reporter.getCurrentTestResult();
    String str = localITestResult.getAttribute("reportDir").toString() + Directory.SEP + Directory.IMGDIRName;
    screenShotNumber = Reporter.getOutput(Reporter.getCurrentTestResult()).size() + 1 + "";
    File localFile1 = new File(str + Directory.SEP + screenShotNumber + ".PNG");
    try
    {
      WebDriver localWebDriver;
      if (getWebDriver().getClass().getName().equals("org.openqa.selenium.remote.RemoteWebDriver")) {
        localWebDriver = new Augmenter().augment(getWebDriver());
      } else {
        localWebDriver = getWebDriver();
      }
      if ((localWebDriver instanceof TakesScreenshot))
      {
        File localFile2 = (File)((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        BufferedImage localBufferedImage1 = ImageIO.read(localFile2);
        Point localPoint = paramWebElement.getLocation();
        int i = paramWebElement.getSize().getWidth();
        int j = paramWebElement.getSize().getHeight();
        BufferedImage localBufferedImage2 = localBufferedImage1.getSubimage(localPoint.getX(), localPoint.getY(), i, j);
        ImageIO.write(localBufferedImage2, "PNG", localFile1);
      }
      else
      {
        screenShotNumber = null;
      }
    }
    catch (Exception localException)
    {
      screenShotNumber = null;
    }
  }
  
  
  /*@Deprecated
  public static void add(String paramString, boolean paramBoolean)
  {
    if (paramBoolean) {
      takeScreenShot();
    }
    Steps localSteps = new Steps();
    localSteps.setDescription(paramString);
    localSteps.setInputValue("");
    localSteps.setExpectedValue("");
    localSteps.setActualValue("");
    localSteps.setTime(getExecutionTime());
    localSteps.setLineNum(getLineNumDesc());
    localSteps.setScreenShot(screenShotNumber);
    localSteps.setLogAs(LogAs.PASSED);
    buildReportData(localSteps);
  }
  
  @Deprecated
  public static void add(String paramString1, String paramString2, boolean paramBoolean)
  {
    if (paramBoolean) {
      takeScreenShot();
    }
    Steps localSteps = new Steps();
    localSteps.setDescription(paramString1);
    localSteps.setInputValue(paramString2);
    localSteps.setExpectedValue("");
    localSteps.setActualValue("");
    localSteps.setTime(getExecutionTime());
    localSteps.setLineNum(getLineNumDesc());
    localSteps.setScreenShot(screenShotNumber);
    localSteps.setLogAs(LogAs.PASSED);
    buildReportData(localSteps);
  }
  
  @Deprecated
  public static void add(String paramString1, String paramString2, String paramString3, boolean paramBoolean)
  {
    if (paramBoolean) {
      takeScreenShot();
    }
    Steps localSteps = new Steps();
    localSteps.setDescription(paramString1);
    localSteps.setInputValue("");
    localSteps.setExpectedValue(paramString2);
    localSteps.setActualValue(paramString3);
    localSteps.setTime(getExecutionTime());
    localSteps.setLineNum(getLineNumDesc());
    localSteps.setScreenShot(screenShotNumber);
    localSteps.setLogAs(LogAs.PASSED);
    buildReportData(localSteps);
  }
  
  @Deprecated
  public static void add(String paramString1, String paramString2, String paramString3, String paramString4, boolean paramBoolean)
  {
    if (paramBoolean) {
      takeScreenShot();
    }
    Steps localSteps = new Steps();
    localSteps.setDescription(paramString1);
    localSteps.setInputValue(paramString2);
    localSteps.setExpectedValue(paramString3);
    localSteps.setActualValue(paramString4);
    localSteps.setTime(getExecutionTime());
    localSteps.setLineNum(getLineNumDesc());
    localSteps.setScreenShot(screenShotNumber);
    localSteps.setLogAs(LogAs.PASSED);
    buildReportData(localSteps);
  }
  
  private static void takeScreenShot()
  {
    if (!Directory.takeScreenshot) {
      return;
    }
    if (getWebDriver() == null)
    {
      screenShotNumber = null;
      return;
    }
    ITestResult localITestResult = Reporter.getCurrentTestResult();
    String str = localITestResult.getAttribute("reportDir").toString() + Directory.SEP + Directory.IMGDIRName;
    screenShotNumber = Reporter.getOutput(Reporter.getCurrentTestResult()).size() + 1 + "";
    File localFile = new File(str + Directory.SEP + screenShotNumber + ".PNG");
    try
    {
      WebDriver localWebDriver;
      if (getWebDriver().getClass().getName().equals("org.openqa.selenium.remote.RemoteWebDriver")) {
        localWebDriver = new Augmenter().augment(getWebDriver());
      } else {
        localWebDriver = getWebDriver();
      }
      if ((localWebDriver instanceof TakesScreenshot))
      {
        byte[] arrayOfByte = (byte[])((TakesScreenshot)localWebDriver).getScreenshotAs(OutputType.BYTES);
        Files.write(arrayOfByte, localFile);
      }
      else
      {
        screenShotNumber = null;
      }
    }
    catch (Exception localException)
    {
      screenShotNumber = null;
    }
  }*/
  
  static
  {
    try
    {
      lastExecutionTime = Reporter.getCurrentTestResult().getStartMillis();
    }
    catch (Exception localException) {}
  }
}
