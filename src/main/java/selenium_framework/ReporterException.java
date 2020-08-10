package selenium_framework;

public class ReporterException extends Exception
{
	  private String message;
	  
	  public ReporterException() {}
	  
	  public ReporterException(String paramString)
	  {
	    this.message = paramString;
	  }
	  
	  public String toString()
	  {
	    return "[Reporter Exception] " + this.message;
	  }
}