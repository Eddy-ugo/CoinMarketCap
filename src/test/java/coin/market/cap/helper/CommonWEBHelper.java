package coin.market.cap.helper;

import java.io.File;
import java.util.List;
import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

import coin.market.cap.utils.BaseHelper;
import coin.market.cap.utils.FileHelper;
import coin.market.cap.utils.GlobalVariables;

public class CommonWEBHelper extends BaseHelper {
	protected By usernameField = By.id("okta-signin-username");
	protected By loginWithOkta = By.xpath("//h6[text()='Login with Okta']/..");
	protected By passwordField = By.id("okta-signin-password");
	protected By signInButton = By.id("okta-signin-submit");
	
	protected By objectDropdown = By.id("object");
	protected By prettyCheckbox = By.id("pretty");
	protected By queryDropdown = By.name("queryopt");
	protected By queryValueField = By.name("query-value");
	protected By submitButton = By.id("submit-button");
	protected By statusCodeField = By.id("statusCode");
	protected By signOutButton = By.id("signout-button");
	protected By responseField = By.id("response");
	protected By oktaUsernameField = By.id("okta-signin-username");
	protected By oktaPasswordField = By.id("okta-signin-password");
	protected By oktaSignInButton = By.id("okta-signin-submit");
	protected By oktaSendPush = By.cssSelector("input[data-type='save']");

	protected By getMethodLocator(String method) {
		return By.cssSelector("label[for='verb" + method.toLowerCase(Locale.ROOT) + "']");
	}

	public void openBrowser(String browserName) {
		initiateBrowser(browserName);
	}

	public void openURL(String url) {
		super.openURL(url);
	}

	public void closeBrowser() {
		super.quitBrowser();
	}

	public void navigateToURL(String url) {
		goToURL(url);
	}

	public void enterUserName(String userName) {
		super.sendKeys(usernameField, userName);
	}

	public void enterValueInField(By field,String value) {
		super.sendKeys(field, value);
	}
	
	public void enterValueInFieldAndSubmit(By field,String value) {
		super.sendKeysWithSubmit(field, value);
	}
	
	public String getRandomStringOfLengthK(int N) {
		return super.getStringOfLength(N);
	}
	
	public void enterPassword(String password) {
		super.sendKeys(passwordField, password);
	}

	public void clickOnSignIn() {
		super.clickOnElement(signInButton);
	}

	public void verifyTitle(String title) {
		super.waitForTitle(title);
	}

	public void selectObject(String object) {
		super.selectValueFromDropdown(objectDropdown, object);
	}

	public void selectMethod(String method) {
		super.clickOnElement(getMethodLocator(method));
	}

	public void setPrettyCheckboxValue(boolean value) {
		super.setCheckBoxValue(prettyCheckbox, value);
	}

	public void selectQuery(String query) {
		super.selectValueFromDropdown(queryDropdown, query);
	}

	public void enterQueryValue(String queryValue) {
		super.sendKeys(queryValueField, queryValue);
	}

	public void clickOnSubmitButton() {
		super.clickOnElement(submitButton);
	}
	
	public void clickLoginWithOkta() {
		super.clickOnElement(loginWithOkta);
	}

	public void waitForStatusCode() {
		getWait().until((ExpectedCondition<Boolean>) driver -> super.waitForElementToBeVisible(statusCodeField)
				.getText() != null);
	}

	public void clickOnSignOut() {
		super.clickOnElement(signOutButton);
	}

	public void captureResponseAndSaveInFile(String fileName) {
		String apiResponse = super.getText(responseField);
		fileName = GlobalVariables.outputFilesPath + File.separator + fileName + ".txt";
		FileHelper.createFile(fileName, apiResponse);
	}

	public void loginToOkta(String username, String password) {
		super.sendKeys(oktaUsernameField, username);
		super.sendKeys(oktaPasswordField, password);
		super.clickOnElement(oktaSignInButton);
		super.waitForElementToBeHide(oktaSignInButton);
		if (super.isElementPresent(oktaSendPush)) {
			super.clickOnElement(oktaSendPush);
			super.sleep(40);
		}
		super.waitForTitle("CVP CBS Production Entertainment VMS");
	}

	public boolean isPageContainsValue(String value) {
        boolean result = pageContainsValue(value);
        System.out.println(result +" -> Web Page contains : " + value);
        return result;
    }

	public void click_JS(By leagueBtn) {
		super.click_JS(leagueBtn);
	}

	public void reloadPage() {
		getBrowser().navigate().refresh();
	}
	
	public void enterValue_JS(By by, String value) {
		WebElement ele=getBrowser().findElement(by);
		
		JavascriptExecutor jse = (JavascriptExecutor)getBrowser();
		String script="arguments[0].value='"+value+"';";
		jse.executeScript(script, ele);
	}

	public List<WebElement> getElementInList(By option) {
		return getBrowser().findElements(option);
	}
}