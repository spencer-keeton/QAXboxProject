package xbox.basepages;

import java.util.ArrayList;
import java.util.Properties;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import util.FunctionUtil;
import util.Page;
import util.SystemUtil;

public class XboxUserPage extends Page{
	
	Properties PROPERTIES_RESOURCES = SystemUtil
			.loadPropertiesResources("/testdata_XboxGameListTest.properties");
	String user = PROPERTIES_RESOURCES.getProperty("xbox.user");
	
	public String title = user + " - Xbox Gamertag";
	
	public ArrayList<WebElement> actualGameList = new ArrayList<WebElement>();
	
	@FindBy(css = "a[href=\'/search\']")
	public WebElement pageLoad;
	
	@FindBy(linkText = "League of Legends")
	public WebElement listedGame1;

	@FindBy(linkText = "Fortnite")
	public WebElement listedGame2;
	
	@FindBy(linkText = "DARK SOULS™ II")
	public WebElement listedGame3;
	
	@FindBy(linkText = "Skyrim")
	public WebElement listedGame4;
	
	public void initActualGame() {
		actualGameList.add(listedGame1);
		actualGameList.add(listedGame2);
		actualGameList.add(listedGame3);
		actualGameList.add(listedGame4);
	}
	
	public boolean userTitleCheck() {
		boolean isUser = false;
		
		if(FunctionUtil.isExist(pageLoad)) {
			isUser = true;
		}else {
			isUser = false;
		}
		
		return isUser;
	}
	
	public boolean checkGame1() {
		boolean isLeague = false;
		
		if(FunctionUtil.isExist(listedGame1)) {
			isLeague = true;
		}else {
			isLeague = false;
		}
		
		return isLeague;
	}
	
	public boolean checkGame2() {
		boolean isFortnite = false;
		
		if(FunctionUtil.isExist(listedGame2)) {
			isFortnite = true;
		}else {
			isFortnite = false;
		}
		
		return isFortnite;
	}
	
	public boolean checkGame3() {
		boolean isSouls = false;
		
		if(FunctionUtil.isExist(listedGame3)) {
			isSouls = true;
		}else {
			isSouls = false;
		}
		
		return isSouls;
	}
	
	public boolean checkGame4() {
		boolean isSkyrim = false;
		
		if(FunctionUtil.isExist(listedGame4)) {
			isSkyrim = true;
		}else {
			isSkyrim = false;
		}
		
		return isSkyrim;
	}
}
