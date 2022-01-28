package xbox.basepages;

import util.FunctionUtil;
import util.Page;
import util.TestCaseBase;
import util.SystemUtil;
import util.Waiting;
import xbox.basepages.XboxUserPage;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class XboxLeaderboardPage extends Page{
	
	Properties PROPERTIES_RESOURCES = SystemUtil
			.loadPropertiesResources("./testdata_XboxLeaderboardTest.properties");
	String URL = PROPERTIES_RESOURCES.getProperty("xbox.lburl");
	
	public static String TITLE = "Xbox Leaderboards - Xbox Gamertag";
	
	@FindBy(tagName = "h1")
	public WebElement pageLoad;
	
	
	@FindAll({
		@FindBy(tagName = "p")
	})
	public List<WebElement> actualScoreList;
	
	public XboxLeaderboardPage open() {
		Properties PROPERTIES_RESOURCES = SystemUtil
				.loadPropertiesResources("./testdata_XboxLeaderboardTest.properties");
		String URL = PROPERTIES_RESOURCES.getProperty("xbox.lburl");
		TestCaseBase.threadDriver.get().navigate().to(URL);

		return this;
	}
	
	public XboxLeaderboardPage waitPageLoad() throws InterruptedException {
		//Thread.sleep(2000);
		//Waiting.implicitly(2);
		
		if (FunctionUtil.isExist(pageLoad)) {
			Waiting.until(pageLoad,5000);
		}	
		
		return this;
	}
}
