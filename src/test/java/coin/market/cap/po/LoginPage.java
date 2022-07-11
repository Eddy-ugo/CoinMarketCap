package coin.market.cap.po;

import static coin.market.cap.step_definitions.Hooks.commonWEBHelper;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import coin.market.cap.utils.BaseHelper;
import coin.market.cap.utils.PropertyReader;

public class LoginPage extends BaseHelper {
	public WebDriver driver = getBrowser();

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(this.driver, this);
	}

	public void login(String url, String username, String password) {
		commonWEBHelper.openURL(PropertyReader.readEnvConfigFile(url));
		commonWEBHelper.clickLoginWithOkta();
		commonWEBHelper.enterUserName(PropertyReader.readEnvConfigFile(username));
		commonWEBHelper.enterPassword(PropertyReader.readEnvConfigFile(password));
		commonWEBHelper.clickOnSignIn();
		commonWEBHelper.sleep(1);
	}

}
