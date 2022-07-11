package coin.market.cap.step_definitions;

import static coin.market.cap.step_definitions.Hooks.commonWEBHelper;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

public class Common_web_step_definations {

	@Then("I should see {string} page")
	public void i_should_see_page(String title) {
		commonWEBHelper.verifyTitle(title);
	}

	@Then("I select object as {string}")
	public void i_select_object_as(String object) {
		commonWEBHelper.selectObject(object);
	}

	@Then("I select {string} method")
	public void i_select_method(String method) {
		commonWEBHelper.selectMethod(method);
	}

	@Then("I Set Pretty as {string}")
	public void i_pretty_set_as(String value) {
		commonWEBHelper.setPrettyCheckboxValue(Boolean.parseBoolean(value));
	}

	@Then("I Select Query as {string}")
	public void i_select_query_as(String queryOption) {
		commonWEBHelper.selectQuery(queryOption);
	}

	@Then("I Click on submit button")
	public void i_click_on_submit_button() {
		commonWEBHelper.clickOnSubmitButton();
	}

	@Then("I should see the response")
	public void i_should_see_the_response() {
		commonWEBHelper.waitForStatusCode();
	}

	@Then("I capture the response in a {string} file")
	public void i_capture_the_response_in_a_file(String fileName) {
		commonWEBHelper.captureResponseAndSaveInFile(fileName);
	}

	@Then("I sign-out from the application")
	public void iSignOutFromTheApplication() {
		commonWEBHelper.clickOnSignOut();
	}

	@Then("I wait for {int} seconds")
	public void waitFor(int seconds) {
		commonWEBHelper.sleep(seconds);
	}
	
	@And("I verify the flash success message contains {string}")
	public void i_verify_flash_success_message(String success) {
		commonWEBHelper.verifySuccessMsg(success);
	}

}