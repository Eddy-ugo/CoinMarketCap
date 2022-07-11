package coin.market.cap.po;

import static coin.market.cap.step_definitions.Hooks.commonWEBHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import coin.market.cap.utils.BaseHelper;
import coin.market.cap.utils.WriteToXL;

public class HomePage extends BaseHelper {
	public WebDriver driver = getBrowser();
	protected By showRowsDropdown = By.cssSelector(".table-control-area>div>div");
	protected By filters = By.xpath("//div/button[contains(.,'Filters')]");
	protected By algorithm = By.xpath("//span[contains(.,'Algorithm')]");
	protected By oneMoreFilters = By.xpath("//button[contains(.,'1 More Filter')]");
	protected By mineable = By.xpath("//*[@id='mineable']/span");
	protected By allCryptocurrencies = By.xpath("//button[contains(.,'All Cryptocurrencies')]");
	protected By coins = By.xpath("//button[contains(.,'Coins')]");
	protected By price = By.xpath("//button[contains(.,'Price')]");
	protected By minField = By.xpath("//input[@data-qa-id='range-filter-input-min']");
	protected By maxField = By.xpath("//input[@data-qa-id='range-filter-input-max']");
	protected By applyFilter = By.xpath("//button[text()='Apply Filter']");
	protected By showResults = By.xpath("//button[text()='Show results']");

	public HomePage(WebDriver driver) {
		this.driver = driver;
	}

	public void verifyPageTitle(String title) {
		commonWEBHelper.verifyTitle(title);
	}

	public void selectRowsDropdownvalueTo(String rows) {
		String xpath = "//div[@class='tippy-content']//button[text()='" + rows + "']";
		By rowItem = By.xpath(xpath);

		commonWEBHelper.clickOnElement(showRowsDropdown);
		commonWEBHelper.clickOnElement(rowItem);

	}

	public void selectRowsDropdownvalueTo(String file, String sheet) {
		Map<String, List<String>> map = new LinkedHashMap<String, List<String>>();

		List<WebElement> tHeader = driver.findElements(By.xpath("//th//p"));
		List<String> tHeaderName = new ArrayList<String>();

		for (WebElement header : tHeader) {
			tHeaderName.add(header.getText().trim());
		}
		tHeaderName.remove(0);
		tHeaderName.remove(tHeaderName.size() - 1);

		System.out.println("header: " + tHeaderName);

		List<WebElement> name = driver.findElements(By.xpath("//td[3]/div/a[@class='cmc-link']"));
		List<String> nameItems = new ArrayList<String>();

		for (WebElement header : name) {
			nameItems.add(header.getText().trim());
		}
		System.out.println("Name: " + nameItems);

		List<WebElement> price = driver.findElements(By.xpath("//td[4]/div/a/span"));
		List<String> priceItems = new ArrayList<String>();

		for (WebElement header : price) {
			priceItems.add(header.getText().trim());
		}
		System.out.println("Price: " + priceItems);

		List<WebElement> marketcap = driver.findElements(By.xpath("//td[8]/p/span[2]"));
		List<String> marketCapDItems = new ArrayList<String>();

		for (WebElement header : marketcap) {
			marketCapDItems.add(header.getText().trim());
		}
		System.out.println("marketcap: " + marketCapDItems);

		List<WebElement> volume24h = driver.findElements(By.xpath("//td[9]/div/a/p"));
		List<String> vol24Items = new ArrayList<String>();

		for (WebElement header : volume24h) {
			vol24Items.add(header.getText().trim());
		}
		System.out.println("vol24Items: " + vol24Items);

		List<WebElement> circulatingSupply = driver.findElements(By.xpath("//td[10]/div/div/p"));
		List<String> circSupplyItems = new ArrayList<String>();

		for (WebElement header : circulatingSupply) {
			circSupplyItems.add(header.getText().trim());
		}
		System.out.println("circSupplyItems: " + circSupplyItems);

		List<WebElement> twentyhpercent = driver.findElements(By.xpath("//td[6]/span"));
		List<String> twentyhpercentItems = new ArrayList<String>();

		for (WebElement header : twentyhpercent) {
			WebElement item = new WebDriverWait(driver, 30).ignoring(StaleElementReferenceException.class)
					.until(ExpectedConditions.visibilityOf(header));
			twentyhpercentItems.add(item.getText().trim());
		}
		System.out.println("24h%: " + twentyhpercent);

		List<WebElement> sevendpercent = driver.findElements(By.xpath("//td[7]/span"));
		List<String> sevenDItems = new ArrayList<String>();

		for (WebElement header : sevendpercent) {
			WebElement item = new WebDriverWait(driver, 30).ignoring(StaleElementReferenceException.class)
					.until(ExpectedConditions.visibilityOf(header));
			sevenDItems.add(item.getText().trim());
		}
		System.out.println("7d%: " + sevenDItems);

		List<WebElement> onehpercent = driver.findElements(By.xpath("//td[5]/span"));
		List<String> onehpercentItems = new ArrayList<String>();

		for (WebElement header : onehpercent) {
			onehpercentItems.add(header.getText().trim());
		}
		System.out.println("1h%: " + onehpercentItems);

		map.put("1", tHeaderName);
		map.put("2", nameItems);
		map.put("3", priceItems);
		map.put("4", onehpercentItems);
		map.put("5", sevenDItems);
		map.put("6", twentyhpercentItems);
		map.put("7", marketCapDItems);
		map.put("8", vol24Items);

		try {
			new WriteToXL().writeToExcel(map, file, sheet);
		} catch (Exception e) {

		}
	}

	public void clickOnFilters() {
		commonWEBHelper.clickOnElement(filters);
	}

	public void selectAlgorithm(String algorithm) {
		commonWEBHelper.sleep(3);
		commonWEBHelper.click_JS(this.algorithm);
		String xpath = "//ul/li[text()='" + algorithm + "']";
		By option = By.xpath(xpath);

		commonWEBHelper.sleep(5);
		commonWEBHelper.click_JS(option);
	}

	public void clickOneMoreFilterBtn() {
		commonWEBHelper.click_JS(this.oneMoreFilters);
	}

	public void selectMineable() {
		commonWEBHelper.clickOnElement(this.mineable);
	}

	public void selectAllCryptocurrencies() {
		commonWEBHelper.click_JS(this.allCryptocurrencies);

	}

	public void selectCoins() {
		commonWEBHelper.clickOnElement(this.coins);

	}

	public void clickPrice() {
		commonWEBHelper.click_JS(this.price);
	}

	public void setMinMaxPrice(String min, String max) {
		commonWEBHelper.enterValueInField(minField, min);
		commonWEBHelper.enterValueInField(maxField, max);
	}

	public void clickApplyFilter() {
		commonWEBHelper.clickOnElement(this.applyFilter);

	}

	public void clickShowResults() {
		commonWEBHelper.clickOnElement(this.showResults);

	}
}