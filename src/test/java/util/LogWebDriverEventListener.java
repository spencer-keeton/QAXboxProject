package util;

import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;

public class LogWebDriverEventListener implements WebDriverEventListener {
	private Log log = LogFactory.getLog(this.getClass());
	private By lastFindBy;
	private String originalValue;

	public void beforeNavigateTo(String url, WebDriver selenium) {
		log.info("WebDriver navigating to:'" + url + "'");
	//	Screenshot.bufferLastImage(selenium);
	}

	public void beforeChangeValueOf(WebElement element, WebDriver selenium) {
		originalValue = element.getAttribute("value");
	//	Screenshot.bufferLastImage(selenium);
	}

	public void afterChangeValueOf(WebElement element, WebDriver selenium) {
		log.info("WebDriver changing value in element found " + lastFindBy
				+ " from '" + originalValue + "' to '"
				+ element.getAttribute("value") + "'");
	}

	public void beforeFindBy(By by, WebElement element, WebDriver selenium) {
		lastFindBy = by;
	}

	public void onException(Throwable error, WebDriver selenium) {
		//Screenshot.takeForError(selenium);
		if (error.getClass().equals(NoSuchElementException.class)) {
			log.error("WebDriver error: Element not found " + lastFindBy);
		} else {
			log.error("WebDriver error:", error);
		}
	}

	public void beforeNavigateBack(WebDriver selenium) {
	}

	public void beforeNavigateForward(WebDriver selenium) {
	}

	public void beforeClickOn(WebElement element, WebDriver selenium) {
		log.info("WebDriver clicking on" + element.toString());
		//Screenshot.bufferLastImage(selenium);
	}

	public void beforeScript(String script, WebDriver selenium) {
	//	Screenshot.bufferLastImage(selenium);
	}

	public void afterClickOn(WebElement element, WebDriver selenium) {
		selenium.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	}

	public void afterFindBy(By by, WebElement element, WebDriver selenium) {
	}

	public void afterNavigateBack(WebDriver selenium) {
	}

	public void afterNavigateForward(WebDriver selenium) {
	}

	public void afterNavigateTo(String url, WebDriver selenium) {
		selenium.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	}

	public void afterScript(String script, WebDriver selenium) {
	}

	public void afterNavigateRefresh(WebDriver arg0) {
		// TODO Auto-generated method stub
		
	}

	public void beforeNavigateRefresh(WebDriver arg0) {
		// TODO Auto-generated method stub
		
	}

	public void beforeAlertAccept(WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	public void afterAlertAccept(WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	public void afterAlertDismiss(WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	public void beforeAlertDismiss(WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	public void beforeChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
		// TODO Auto-generated method stub
		
	}

	public void afterChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
		// TODO Auto-generated method stub
		
	}

	public void beforeSwitchToWindow(String windowName, WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	public void afterSwitchToWindow(String windowName, WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	public <X> void beforeGetScreenshotAs(OutputType<X> target) {
		// TODO Auto-generated method stub
		
	}

	public <X> void afterGetScreenshotAs(OutputType<X> target, X screenshot) {
		// TODO Auto-generated method stub
		
	}

	public void beforeGetText(WebElement element, WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	public void afterGetText(WebElement element, WebDriver driver, String text) {
		// TODO Auto-generated method stub
		
	}



}