package Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Util {
	
	/**
	   * Opens and reads a file, and returns the contents as one String.
	   */
	  public static String readFileAsString(String filename) 
	  throws IOException
	  {
	    BufferedReader reader = new BufferedReader(new FileReader(filename));
	    String line;
	    StringBuilder sb = new StringBuilder();
	    while ((line = reader.readLine()) != null)
	    {
	      sb.append(line + "\n");
	    }
	    reader.close();
	    return sb.toString();
	  }

	
}
	  