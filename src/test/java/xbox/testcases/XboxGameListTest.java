package xbox.testcases;

import java.util.ArrayList;
import java.util.Properties;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import xbox.basepages.*;
import util.SystemUtil;
import util.TestCaseBase;

public class XboxGameListTest extends TestCaseBase {
	Properties PROPERTIES_RESOURCES = SystemUtil
			.loadPropertiesResources("/testdata_XboxGameListTest.properties");
	public String user = PROPERTIES_RESOURCES.getProperty("xbox.user");
	
	protected final ArrayList<String> expectedGameList = new ArrayList<String>();
	String eG1 = PROPERTIES_RESOURCES.getProperty("xbox.game1");
	String eG2 = PROPERTIES_RESOURCES.getProperty("xbox.game2");
	String eG3 = PROPERTIES_RESOURCES.getProperty("xbox.game3");
	String eG4 = PROPERTIES_RESOURCES.getProperty("xbox.game4");
	
	@Test(groups = { "ChromeWin32" })
	public void verifyListedGames() throws Exception{
		XboxHomePage xboxHomepage = new XboxHomePage();
		XboxUserPage xboxUserpage = new XboxUserPage();
		xboxHomepage.open();
		xboxHomepage.waitPageLoad();
		Assert.assertTrue(xboxHomepage.titleIs(XboxHomePage.TITLE));
		
		xboxHomepage.search(user);
		xboxHomepage.gotoXboxUserPage();
		xboxHomepage.waitPageLoad();
		
		expectedGameList.add(eG1);
		expectedGameList.add(eG2);
		expectedGameList.add(eG3);
		expectedGameList.add(eG4);
		
		System.out.println(expectedGameList);
		
		Assert.assertTrue(xboxUserpage.titleIs(xboxUserpage.title));
		
		Assert.assertTrue(xboxUserpage.checkGame1());
		Assert.assertTrue(xboxUserpage.checkGame2());
		Assert.assertTrue(xboxUserpage.checkGame3());
		Assert.assertTrue(xboxUserpage.checkGame4());
		
		xboxUserpage.initActualGame();
		for(int i = 0; i < 4; i++) {
			WebElement actualElement = xboxUserpage.actualGameList.get(i);
			String actualGame = actualElement.getText();
			
			Assert.assertEquals(actualGame, expectedGameList.get(i));
		}
		
//		Assert.assertEquals(xboxUserpage.listedGame1.getText(), gameList.get(0));
//		Assert.assertEquals(xboxUserpage.listedGame2.getText(), gameList.get(1));
//		Assert.assertEquals(xboxUserpage.listedGame3.getText(), gameList.get(2));
//		Assert.assertEquals(xboxUserpage.listedGame4.getText(), gameList.get(3));
	}
}
