package coin.market.cap.po;

import static coin.market.cap.step_definitions.Hooks.commonWEBHelper;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import coin.market.cap.utils.BaseHelper;

public class EventsPage extends BaseHelper {
	public WebDriver driver = getBrowser();
	protected By createEventBtn = By.xpath("//*[text()='Create Event']");
	protected By createEventDialog = By.id("dialog_events");
	
	public EventsPage(WebDriver driver) {
		this.driver = driver;
	}

	public void clickOnCreateEventButton() {
		commonWEBHelper.clickOnElement(createEventBtn);
	}

	public void verifyCreateEventDialog() {
		commonWEBHelper.waitForElementToBeVisible(createEventDialog);
		
	}


}
