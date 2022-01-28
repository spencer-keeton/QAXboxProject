package util;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Waiting {
	
	
	//use the system util to read common properties
	private static Properties PROPERTIES_RESOURCES = SystemUtil
			.loadPropertiesResources("/testdata_common.properties");
	private static int MAX_WAIT_FOR_ELEMENT = Integer.parseInt(PROPERTIES_RESOURCES
			.getProperty("maxWaitForElement"));
	
	/**
	 * wait until the element exist
	 * 
	 * @param element
	 *            : the element to wait for
	 * @param timeout
	 *            : waiting until the timeout
	 */
	public static void until(WebElement element, int timeout) {
		WebDriverWait wait = new WebDriverWait(TestCaseBase.threadDriver.get(),
				timeout);
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	/**
	 * wait until the element exist
	 * 
	 * @param element
	 *            : the element to wait for
	 * @param timeout
	 *            : waiting until default timeout 30s
	 */
	public static void until(WebElement element) {
		WebDriverWait wait = new WebDriverWait(TestCaseBase.threadDriver.get(),
				MAX_WAIT_FOR_ELEMENT);
		wait.until(ExpectedConditions.visibilityOf(element));
		
	}
	
	
	
	
	
	/**
	 * Objective: Wait for a certain time
	 * 
	 * @param time
	 *            : waiting in seconds
	 * @throws Exception
	 */
	static public void implicitly(int time) {
		TestCaseBase.threadDriver.get().manage().timeouts()
				.implicitlyWait(time, TimeUnit.SECONDS);
	}

	/**
	 * Objective: wait for a certain time which is set in properties files
	 * 
	 * @throws Exception
	 */
	static public void implicitly() {
		implicitly(MAX_WAIT_FOR_ELEMENT);
		
	}
	
}
