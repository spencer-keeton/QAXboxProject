package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class TestData {

	private static Properties resource = null;
	// it is used to load the test data file
	public static void load(String filename)
	{	
		resource = new Properties();
		try{
			File file=new File("C:\\Sanford\\Eclipse Projects\\XboxAssignment\\testdata\\"+filename);	
			InputStream data_input = new FileInputStream(file);
			//InputStream data_input = SystemUtil.class.getResourceAsStream("/"+filename);
			resource.load(data_input);
			System.out.println("Read test data file "+ filename+"...");
		}catch (Exception e){
			System.out.println("Warning: Not found test data file "+ filename);
		}
	
	}
	/**
	 * Objective: Get the test data, simple use TestData.get("args name");
	 * 
	 * @param argName
	 *            : the test data args name you defined in test data file
	 */
	public static String get(String argName){
		return resource.getProperty(argName).trim();
	}	

}
