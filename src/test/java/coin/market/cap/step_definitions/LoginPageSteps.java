package coin.market.cap.step_definitions;

import static coin.market.cap.step_definitions.Hooks.commonWEBHelper;

import coin.market.cap.po.LoginPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class LoginPageSteps {
	LoginPage loginPage = new LoginPage(commonWEBHelper.getBrowser());

	@Given("I open the {string} environment application and signIn with {string} and {string} password")
	public void i_enter_username_as_and_password_as_and_login_to_the_application(String url, String username,
			String password) {
		loginPage.login(url, username, password);
	}
	
	
}
