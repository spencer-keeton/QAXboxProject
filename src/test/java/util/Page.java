package util;

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.support.PageFactory;



public class Page {
	
	//properties which saves something like the url/title of a page 
	protected static Properties resource;
	protected static Log log = LogFactory.getLog(Page.class);
	
	public Page(){
		PageFactory.initElements(TestCaseBase.threadDriver.get(),this);
	}

	public String getTitle(){
		return TestCaseBase.threadDriver.get().getTitle();
	}
	
	public boolean titleIs(String title) {
		log.info("expected title="+title);
		log.info("actual title="+getTitle());
		return (getTitle().equals(title));
	}
	
	public boolean titleContains(String title) {
		log.info("expected title to contain="+title);
		log.info("actual title="+getTitle());
		return (getTitle().contains(title));
	}
}
