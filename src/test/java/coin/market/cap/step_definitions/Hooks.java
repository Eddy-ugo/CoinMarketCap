package coin.market.cap.step_definitions;

import coin.market.cap.helper.CommonAPIHelper;
import coin.market.cap.helper.CommonWEBHelper;
import io.cucumber.java.After;
import io.cucumber.java.BeforeAll;

public class Hooks {
	
	protected static CommonAPIHelper commonAPIHelper;
	public static CommonWEBHelper commonWEBHelper;

	// Will execute once throughout the execution
	@BeforeAll
	public static void beforeAll() {
		commonAPIHelper = new CommonAPIHelper();
		commonWEBHelper = new CommonWEBHelper();
		commonAPIHelper.initiateBrowser("chrome");
	}

	@After
	public static void afterEachScenario() {
		// commonWEBHelper.closeBrowser();
	}
	
}
