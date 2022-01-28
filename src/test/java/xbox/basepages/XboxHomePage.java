package xbox.basepages;

import util.FunctionUtil;
import util.Page;
import util.TestCaseBase;
import util.SystemUtil;
import util.Waiting;
import xbox.basepages.XboxUserPage;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.util.Properties;

public class XboxHomePage extends Page {
	
	public static String TITLE = "Xbox Gamertag";
	
	@FindBy(css = ".btn.btn-primary")
	public WebElement pageLoad;
	
	@FindBy(css = ".form-control.form-control-lg")
	public WebElement gamertagSearchBar;
	
	@FindBy(css = ".btn.btn-primary")
	public WebElement gamertagSearchButton;
	
	public XboxHomePage open() {
		//read the url from property file
		Properties PROPERTIES_RESOURCES = SystemUtil
				.loadPropertiesResources("./testdata_xbox.properties");
		String URL = PROPERTIES_RESOURCES.getProperty("xbox.url");
		//String URL = "https://www.xboxgamertag.com/";
		TestCaseBase.threadDriver.get().navigate().to(URL);

		return this;
	}
	
	public XboxHomePage search (String search) {
		Waiting.until(gamertagSearchBar);
		gamertagSearchBar.clear();
		gamertagSearchBar.sendKeys(search);
		
		return this;
	}
	
	public XboxUserPage gotoXboxUserPage() {
		Waiting.until(gamertagSearchButton);
		gamertagSearchButton.click();
		
		return new XboxUserPage();
	}
	
	public XboxHomePage waitPageLoad() throws InterruptedException {
		//Thread.sleep(2000);
		//Waiting.implicitly(2);
		
		if (FunctionUtil.isExist(pageLoad)) {
			Waiting.until(pageLoad,5000);
		}	
		
		return this;
	}	
	
}
