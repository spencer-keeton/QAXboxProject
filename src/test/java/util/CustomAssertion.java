package util;

import java.util.HashMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CustomAssertion {
	private Log log = LogFactory.getLog(this.getClass());

	public void equals(String actualResult, HashMap<String, String> expected,
			String key) {
		
		log.info("Expected Result---" + key + "=" + expected.get(key));
		log.info("Actual Result---" + key + "=" + actualResult);
		if(actualResult.equals(expected.get(key)))
		{
			log.info("Asserting actual_result equals expected_result...");
			log.info("Pass");
		}else{
			log.error("Fail");
		}
		
		assert (actualResult.equals(expected.get(key)));
	}

	public void contains(String actualResult, HashMap<String, String> expected,
			String key) {
		
		log.info("Expected Result---" + key + "=" + expected.get(key));
		log.info("Actual Result---" + key + "=" + actualResult);
		if(actualResult.contains(expected.get(key)))
		{
			log.info("Asserting actual_result contains expected_result...");
			log.info("Pass");
		}else{
			log.error("Fail");
		}
		
		assert (actualResult.contains(expected.get(key)));

	}

}
