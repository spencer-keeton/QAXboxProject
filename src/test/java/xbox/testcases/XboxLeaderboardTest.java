package xbox.testcases;

import java.util.ArrayList;
import java.util.Properties;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import xbox.basepages.XboxLeaderboardPage;
import util.SystemUtil;
import util.TestCaseBase;

public class XboxLeaderboardTest extends TestCaseBase{
	Properties PROPERTIES_RESOURCES = SystemUtil
			.loadPropertiesResources("./testdata_XboxLeaderboardTest.properties");
	String URL = PROPERTIES_RESOURCES.getProperty("xbox.lburl");
	
	protected final ArrayList<String> expectedScoreList = new ArrayList<String>();
	String eS1 = PROPERTIES_RESOURCES.getProperty("xbox.score1");
	String eS2 = PROPERTIES_RESOURCES.getProperty("xbox.score2");
	String eS3 = PROPERTIES_RESOURCES.getProperty("xbox.score3");
	String eS4 = PROPERTIES_RESOURCES.getProperty("xbox.score4");
	String eS5 = PROPERTIES_RESOURCES.getProperty("xbox.score5");
	
	@Test(groups = { "ChromeWin32" })
	public void verifyTopFiveScores() throws Exception{
		XboxLeaderboardPage xboxLeaderboard = new XboxLeaderboardPage();
		xboxLeaderboard.open();
		xboxLeaderboard.waitPageLoad();
		Assert.assertTrue(xboxLeaderboard.titleIs(XboxLeaderboardPage.TITLE));
		
		expectedScoreList.add(eS1);
		expectedScoreList.add(eS2);
		expectedScoreList.add(eS3);
		expectedScoreList.add(eS4);
		expectedScoreList.add(eS5);
		
		for(int i = 0; i < 5; i++) {
			WebElement actualElementScore = xboxLeaderboard.actualScoreList.get(i);
			String actualScore = actualElementScore.getText();
			
			Assert.assertEquals(actualScore, expectedScoreList.get(i));
		}
	}
}
