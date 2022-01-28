package util;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Properties;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
//import org.testng.ITest;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
//import org.testng.xml.XmlSuite;

public class TestCaseBase{
	
	public WebDriver driver_original;
	public static ThreadLocal<WebDriver> threadDriver = new ThreadLocal<WebDriver>();
	public static String browserFlag;
	public static String onGrid;
	public static String host;
	public static String port;
	public static int ieCountCurrent = 0;
	public static int firefoxCountCurrent = 0;
	public static int chromeCountCurrent = 0;
	private LogWebDriverEventListener eventListener;
	protected  Log log = LogFactory.getLog(this.getClass());
	public static EventFiringWebDriver driver;
	public String actualResult;
	public HashMap<String,String> expected;
	public CustomAssertion assertion;
	
	
	/**
	 * Objective: Set up the browser which is specified in testng.xml per group
	 * 
	 * @param browserFlagO
	 *            : Defined in testng's xml
	 * @param runngOnGrid: using grid or not, defined in testng's xml file 
	 * 					
	 * @param hubPort: hub port for grid
	 * @param hubHost: hub host for grid
	 * 
	 */
	@Parameters({"browserFlagO","runningOnGrid","hubHost","hubPort"})
	@BeforeMethod(alwaysRun = true)
	public void setUpBrowser(@Optional("chrome")String browserFlagO,@Optional("false")String runningOnGrid,@Optional("0")String hubHost,@Optional("0")String hubPort) throws Exception {
		log.info("running TEST Case:"+this.getClass().getName());//print test case name
		initParams(browserFlagO, runningOnGrid, hubHost, hubPort);//set param to test case base
		//System.setProperty("webdriver.firefox.bin", "C:\\Program Files\\Mozilla Firefox\\firefox.exe");
		selectBrowser();//select a browser to run tests
		threadDriver.set(driver); // for web driver is not thread safe
		FunctionUtil.maximeBrowser();// Maximize the browser
		setDefaultTestData(); //set default test data for each test cases
	}

	private void selectBrowser() throws Exception {
		if (browserFlag.equals("ie")) 
		{
			setUpIEWin32(onGrid);
		} else if (browserFlag.equals("firefox")) {
			//setUpFirefoxWithDefaultProfile(onGrid);
			setUpFirefoxWithNewProfile(onGrid);
		} else if (browserFlag.equals("chrome")) {
			setUpChromeWin32(onGrid);
		} else if (browserFlag.equals("random")) {
			setUpRandomBrowserPerCase(onGrid);
		} else if (browserFlag.equals("percentage_specified")) {
			setupBrowserPerPercentage();
		}
	}

	private void setupBrowserPerPercentage() throws Exception {
		Properties PROPERTIES_RESOURCES = SystemUtil
				.loadPropertiesResources("/browser-percentage.properties");
		String ie = PROPERTIES_RESOURCES.getProperty("ie.percentage");
		String firefox = PROPERTIES_RESOURCES
				.getProperty("firefox.percentage");
		String chrome = PROPERTIES_RESOURCES
				.getProperty("googlechrome.percentage");
		int testcaseCount = Integer.parseInt(PROPERTIES_RESOURCES.getProperty("testcase.count"));
		newBrowserPerPercentage(ie, firefox, chrome, testcaseCount,onGrid);
	}

	private void initParams(String browserFlagO, String runningOnGrid,
		String hubHost, String hubPort) {
		browserFlag = browserFlagO;
		onGrid=runningOnGrid;
		host=hubHost;
		port=hubPort;
		actualResult=null;
		expected=new HashMap<String,String>();
		assertion=new CustomAssertion();
		
		log.info("onGrid=" + runningOnGrid);
		log.info("browserFlag=" + browserFlag);
		if (!onGrid.equals("false")){
			log.info("hubHost=" + hubHost);
			log.info("hubPort=" + hubPort);
		}
		
		
	}

	/**
	 * Objective: Randomize the browser for each test case
	 * @param onGrid 
	 * 
	 * @throws Exception
	 * 
	 *             Updated by colin @2013-10-24, now this method will not be
	 *             directly used it only be called withthin setUpBrowser when
	 *             browserFlag in testNG.xml is set to 'random'
	 * 
	 */
	private void setUpRandomBrowserPerCase(String onGrid) throws Exception {
		log.info("Setting up random browser...");
		Random rndObj = new Random();
		int rndBrowserIndex = rndObj.nextInt(3);
		if (rndBrowserIndex == 0) {
			setUpIEWin32(onGrid);
			browserFlag = "ie";
		} else if (rndBrowserIndex == 1) {
			setUpFirefoxWithDefaultProfile(onGrid);
			browserFlag = "firefox";
		} else if (rndBrowserIndex == 2) {
			setUpChromeWin32(onGrid);
			browserFlag = "chrome";
		} else {
			log.error("Random select browser fails");
			throw new Exception(
					"No browser is specified for the random number: "
							+ rndBrowserIndex + ".");
		}

	}

	/**
	 * Objective: Set up the browser per percentage by different browsers
	 * 
	 * @param iePercentage
	 *            : The percentage of test cases which to be executed in IE
	 * @param firefoxPercentage
	 *            :The percentage of test cases which to be executed in Firefox
	 * @param chromePercentage
	 *            : The percentage of test cases which to be executed in chrome
	 * @param testCaseCount
	 *            : Total count of test cases
	 * @param onGrid 
	 * @throws Exception
	 */
	private void newBrowserPerPercentage(String iePercentage,
			String firefoxPercentage, String chromePercentage, int testCaseCount, String onGrid)
			throws Exception {
		log.info("Setting up browser per percentage: ie=" + iePercentage
				+ " firefox=" + firefoxPercentage + " chrome="
				+ chromePercentage + " test case count=" + testCaseCount);
		// Convert the percentage to float
		float iePercent = new Float(iePercentage.substring(0,
				iePercentage.indexOf("%"))) / 100;
		float firefoxPercent = new Float(firefoxPercentage.substring(0,
				firefoxPercentage.indexOf("%"))) / 100;

		// Get the rounded ieMaxCount, if ieMaxCount<1, plus 1
		int ieMaxCount = Math.round(iePercent * testCaseCount);
		if (ieMaxCount < 1) {
			ieMaxCount = 1;
		}
		// Get the rounded firefoxMaxCount, if ieMaxCount<1, plus 1
		int firefoxMaxCount = Math.round(firefoxPercent * testCaseCount);
		if (firefoxMaxCount < 1) {
			firefoxMaxCount = 1;
		}
		// Get the chromeMaxCount by math
		int chromeMaxCount = testCaseCount - ieMaxCount - firefoxMaxCount;
		
		// set up the browser by the specified percentage
		if (ieCountCurrent < ieMaxCount) {
			setUpIEWin32(onGrid);
			browserFlag = "ie";
			ieCountCurrent++;
		} else if (ieCountCurrent == ieMaxCount
				&& firefoxCountCurrent < firefoxMaxCount) {
			setUpFirefoxWithDefaultProfile(onGrid);
			browserFlag = "firefox";
			firefoxCountCurrent++;
		} else if (ieCountCurrent == ieMaxCount
				&& firefoxCountCurrent == firefoxMaxCount
				&& chromeCountCurrent < chromeMaxCount) {
			setUpChromeWin32(onGrid);
			browserFlag = "chrome";
			chromeCountCurrent++;
		} else {
			throw new Exception("The current ieCount:" + ieCountCurrent
					+ ", firefoxCount:" + firefoxCountCurrent
					+ "and chromeCount: " + chromeCountCurrent
					+ " doesn't fit the conditions");
		}

		log.info("ie Count Current=" + ieCountCurrent);
		log.info("ie Count Max=" + ieMaxCount);
		log.info("firefox Count Current=" + firefoxCountCurrent);
		log.info("firefox Count Max=" + firefoxMaxCount);
		log.info("googlechrome Count Current=" + chromeCountCurrent);
		log.info("googlechrome Count Max=" + chromeMaxCount);
	}

	/**
	 * Objective: Close the opened browser which was opened by WebDriver
	 */
	@AfterMethod(alwaysRun = true)
	public void tearDown() throws Exception {
		driver.quit();
		SystemUtil.driverKiller();// for the driver.quit still not kill the ie
									// driver process
	}

	/**
	 * Objective: New fire fox driver with default profile
	 * @param onGrid 
	 * 
	 * @throws Exception
	 */
	private void setUpFirefoxWithDefaultProfile(String onGrid) throws Exception {
		
		if (onGrid.equals("false")){
		driver_original = new FirefoxDriver();}
		else{
		DesiredCapabilities capability = DesiredCapabilities.firefox();
		driver_original = new RemoteWebDriver(new URL("http://"+host+":"+port+"/wd/hub"),capability);
		}
		eventListener = new LogWebDriverEventListener();
		driver = new EventFiringWebDriver(driver_original);
		driver.register(eventListener);
		
	}

	/**
	 * Objective: New fire fox driver with new profile which can be
	 * user-defined.
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	private void setUpFirefoxWithNewProfile(String onGrid) throws Exception {
		FirefoxProfile fp = new FirefoxProfile();
		fp.setEnableNativeEvents(true);
		if (onGrid.equals("false")){
		driver_original = new FirefoxDriver(fp);}
		else{
		DesiredCapabilities capability = DesiredCapabilities.firefox();
		capability.setCapability(FirefoxDriver.PROFILE, fp);
		driver_original = new RemoteWebDriver(new URL("http://"+host+":"+port+"/wd/hub"),capability);
		}
		eventListener = new LogWebDriverEventListener();
		driver = new EventFiringWebDriver(driver_original);
		driver.register(eventListener);
	}

	/**
	 * Objective: New IEwin32 driver
	 * @param onGrid 
	 * 
	 * @throws Exception
	 */
	private void setUpIEWin32(String onGrid) throws Exception {
		File file = new File("./lib/IEDriverServerNew.exe");
		System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
		
		if (onGrid.equals("false")){
			driver_original = new InternetExplorerDriver();
		} else {
			DesiredCapabilities capability = DesiredCapabilities.internetExplorer();
			driver_original = new RemoteWebDriver(new URL("http://"+host+":"+port+"/wd/hub"),capability);	
		}
		
		eventListener = new LogWebDriverEventListener();
		driver = new EventFiringWebDriver(driver_original);
		driver.register(eventListener);
	}

	/**
	 * Objective: New ChromeWin32 driver
	 * @param onGrid 
	 * 
	 * @throws Exception
	 */
	private void setUpChromeWin32(String onGrid) throws Exception {
		File file = new File("./lib/chromedriver.exe");
		System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
		if (onGrid.equals("false")){driver_original = new ChromeDriver();}
		else{
		DesiredCapabilities capability = DesiredCapabilities.chrome();
		driver_original = new RemoteWebDriver(new URL("http://"+host+":"+port+"/wd/hub"),capability);
		}
		eventListener = new LogWebDriverEventListener();
		driver = new EventFiringWebDriver(driver_original);
		driver.register(eventListener);
	}

	/**
	 * Objective: set the default test data file name
	 * 1.get the current test case class name and use this name to get a 
	 * test data file name.
	 * For example, a class "TC_01_GoogleTest" will get a testdata file name
	 * "testdata_TC_01_GoogleTest"
	 * 2.load this properties file
	 */
	private void setDefaultTestData(){
		String s=this.getClass().getName();
		String filename=("testdata_"+s.split("\\.")[s.split("\\.").length-1]+".properties");
		log.info("Setting...filename="+filename);
		TestData.load(filename);

	}


	public String getTestName() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
	
}