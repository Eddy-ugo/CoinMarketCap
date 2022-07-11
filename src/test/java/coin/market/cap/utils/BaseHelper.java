package coin.market.cap.utils;

import static coin.market.cap.step_definitions.Hooks.commonWEBHelper;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.RestAssured;
import io.restassured.http.Method;

public class BaseHelper {
	// region VARIABLES
	private Map<String, Object> finalJSONKeyValues;
	private static WebDriver driver;
	private static WebDriverWait webDriverWait;
	protected By successMsg = By.xpath("//div[@id='notistack-snackbar']");

	// To initialize the rest request with base URL
	protected void initializeRestRequest(String baseURL) {
		RestAssured.baseURI = baseURL;
		GlobalVariables.httpRequest = RestAssured.given();
	}

	// To add authorization to the request
	protected void addBasicAuthorization(String username, String password) {
		GlobalVariables.httpRequest.auth().preemptive().basic(username, password);
	}

	// To add authorization to the request
	protected void addOauth2Authorization(String accessToken) {
		GlobalVariables.httpRequest.auth().preemptive().oauth2(accessToken);
	}

	// To Add header in the request
	protected void addRequestHeader(Map<String, String> headers) {
		GlobalVariables.httpRequest.headers(headers);
	}

	// To update the added headers in the request
	protected void updateRequestHeader(String headerKey, String value) {
		GlobalVariables.httpRequest.headers(headerKey, value);
	}

	// To add the parameters in the request
	protected void addRequestParameters(Map<String, String> parameters) {
		GlobalVariables.httpRequest.params(parameters);
	}

	// To update the value of given node by jsonPath in the JSON request body
	protected void updateAttributeInJSONRequestBody(String jsonString, String jsonPath, String newValue) {
		DocumentContext json = JsonPath.parse(jsonString);
		GlobalVariables.requestPayload = json.set(jsonPath, newValue).jsonString();
	}

	// To set the request body
	protected void generatedJSONPayload() {
		GlobalVariables.httpRequest.body(GlobalVariables.requestPayload);
	}

	// To submit the request
	protected void submitRequest(Method method, String uri) {
		GlobalVariables.httpResponse = null;
		String filePath;
		String fileName = uri.split("/")[uri.split("/").length - 1];
		String folderName = GlobalVariables.outputFilesPath + File.separator + fileName + File.separator
				+ new SimpleDateFormat("YYYYMMdd_hhmmss").format(new Date()).toString();
		FileHelper.createFolder(folderName);
		if (method != Method.GET) {
			filePath = folderName + File.separator + fileName + "_Request.txt";
			FileHelper.createFile(filePath, GlobalVariables.requestPayload);
			GlobalVariables.newlyCreatedRequestPayloadFile = filePath;
		}
		try {
			System.out.println(
					"\n\n******************************************************************************************************************************************************************\n\n");

			GlobalVariables.httpRequest.log().all();
			GlobalVariables.httpResponse = GlobalVariables.httpRequest.request(method, uri);
			System.out.println("\n\nRESPONSE BODY : " + GlobalVariables.httpResponse.getBody().asString());

			System.out.println(
					"\n\n******************************************************************************************************************************************************************\n\n");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		Assert.assertEquals(GlobalVariables.httpResponse.statusCode(), 200);
		filePath = folderName + File.separator + fileName + "_Response.txt";
		FileHelper.createFile(filePath, GlobalVariables.httpResponse.getBody().asString());
		GlobalVariables.newlyCreatedResponsePayloadFile = filePath;
	}

	// To get Single key value of the given jsonPath from the Response
	protected String getSingleValueFromResponse(String jsonPath) {
		String nodeValue = null;
		io.restassured.path.json.JsonPath jsonPathValue = GlobalVariables.httpResponse.jsonPath();
		nodeValue = jsonPathValue.getString(jsonPath);

		return nodeValue;
	}

	// To get Single key value of the given jsonPath from the Request
	protected String getSingleValueFromJson(String completeJson, String jsonPath) {
		String nodeValue = null;
		Object jsonNode = JsonPath.parse(completeJson).read("$." + jsonPath, Object.class);

		nodeValue = jsonNode.toString();
		return nodeValue;
	}

	// To get/return the response as String
	protected String returnResponseAsString() {
		return GlobalVariables.httpResponse.asString();
	}

	// To Assert the status code
	protected void assertStatusCode(int statusCode) {
		Assert.assertEquals(statusCode, GlobalVariables.httpResponse.statusCode());
	}

	// To assert the key value in the response
	protected void assertKeyValueInResponse(String jsonPath, String value) {
		Assert.assertEquals(value, getSingleValueFromResponse(jsonPath));
	}

	// To validate JSON with Schema
	protected boolean validateJSONSchema(String jsonString, String jsonSchemaString) {
		boolean result = false;
		ObjectMapper objectMapper = new ObjectMapper();
		JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7);

		try {
			JsonNode json = objectMapper.readTree(jsonString);

			JsonSchema jsonSchema = schemaFactory.getSchema(jsonSchemaString);

			Set<ValidationMessage> validationRessult = jsonSchema.validate(json);

			if (validationRessult.isEmpty()) {
				System.out.println("No JSON Schema Validation Errors");
				result = true;
			} else {
				System.out.println("JSON Schema has following Validation Errors");
				validationRessult.forEach(vm -> System.out.println(vm.getMessage()));
				result = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				throw e;
			} catch (JsonProcessingException ex) {
				ex.printStackTrace();
			}
			result = false;
		}
		return result;
	}

	protected List<String> parseJsonInKeyValues(String completeJson) {
		finalJSONKeyValues = new HashMap<String, Object>();
		// parseJsonByKeys(completeJson,null);
		JsonParser(completeJson);
		return getPathList();
	}

	private void parseJsonByKeys(String completeJson, String key) {
		JSONArray jArray = null;
		JSONArray keysArray = null;
		JSONObject jsonObject = null;
		if (completeJson.startsWith("[")) {
			jArray = new JSONArray(completeJson);

			for (int i = 0; i < jArray.length(); i++) {
				try {
					jsonObject = new JSONObject(jArray.get(i).toString());
					keysArray = jsonObject.names();
					captureKeyValue(jsonObject, keysArray);
				} catch (Exception e) {
//                    String value = jArray.get(i).toString().toLowerCase();
//                    if (!finalJSONKeyValues.containsKey(value))
//                        finalJSONKeyValues.put(value, key);
				}
			}
		} else {
			jsonObject = new JSONObject(completeJson);

			keysArray = jsonObject.names();
			captureKeyValue(jsonObject, keysArray);
		}

	}

	private void captureKeyValue(JSONObject jsonObject, JSONArray keysArray) {
		if (keysArray != null && !(keysArray.length() < 1)) {
			for (int i = 0; i < keysArray.length(); i++) {
				String key = keysArray.getString(i);
				String value = jsonObject.get(key).toString();
				if (value.startsWith("{") || value.startsWith("["))
					parseJsonByKeys(value, key);
				else
					value = value.toLowerCase();
				if (!finalJSONKeyValues.containsKey(value))
					finalJSONKeyValues.put(value, key);
			}
		}
	}

	private List<String> pathList;
	private String json;

	public void JsonParser(String json) {
		this.json = json;
		this.pathList = new ArrayList<String>();
		setJsonPaths(json);
	}

	public List<String> getPathList() {
		return this.pathList;
	}

	private void setJsonPaths(String json) {
		this.pathList = new ArrayList<String>();
		JSONObject object = new JSONObject(json);
		String jsonPath = "$";
		if (json != JSONObject.NULL) {
			readObject(object, jsonPath);
		}
	}

	private void readObject(JSONObject object, String jsonPath) {
		Iterator<String> keysItr = object.keys();
		String parentPath = jsonPath;
		while (keysItr.hasNext()) {
			String key = keysItr.next();
			Object value = object.get(key);
			jsonPath = parentPath + "." + key;

			if (value instanceof JSONArray) {
				readArray((JSONArray) value, jsonPath);
			} else if (value instanceof JSONObject) {
				readObject((JSONObject) value, jsonPath);
			} else { // is a value
				this.pathList.add(jsonPath);
			}
		}
	}

	private void readArray(JSONArray array, String jsonPath) {
		String parentPath = jsonPath;
		for (int i = 0; i < array.length(); i++) {
			Object value = array.get(i);
			jsonPath = parentPath + "[" + i + "]";

			if (value instanceof JSONArray) {
				readArray((JSONArray) value, jsonPath);
			} else if (value instanceof JSONObject) {
				readObject((JSONObject) value, jsonPath);
			} else { // is a value
				this.pathList.add(jsonPath);
			}
		}
	}

	// endregion

	// region WEB AUTOMATION METHODS

	public WebDriver initiateBrowser(String browser) {
		switch (browser.toLowerCase()) {
		case "chrome":
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			break;
		case "safari":
			WebDriverManager.safaridriver().setup();
			driver = new SafariDriver();
			break;
		case "firefox":
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			break;
		}
		driver.manage().window().maximize();
		return driver;
	}

	public WebDriver getBrowser() {
		return driver;
	}

	public WebDriverWait getWait() {
		webDriverWait = new WebDriverWait(getBrowser(), 30);
		return webDriverWait;
	}

	public WebElement waitForElementToBeVisible(By by) {
		getWait().until(ExpectedConditions.visibilityOfElementLocated(by));
		return driver.findElement(by);
	}

	public void waitForElementToContainsMatchingText(By by, String text) {
		new WebDriverWait(getBrowser(), 15).until(new ExpectedCondition<Boolean>() {

			@Override
			public Boolean apply(WebDriver driver) {
				String message = getBrowser().findElement(by).getText();
				System.out.println("Flash Msg displayed is:: " + message);
				return message.contains(text);
			}
		});
	}

	public WebElement waitForElemenetToBeClickable(By by) {
		getWait().until(ExpectedConditions.elementToBeClickable(by));
		return driver.findElement(by);
	}

	public WebElement waitForElementToBeHide(By by) {
		try {
			getWait().until(ExpectedConditions.invisibilityOfElementLocated(by));
			return driver.findElement(by);
		} catch (Exception e) {
			return null;
		}
	}

	public WebElement waitForElementToBeEnabled(By by) {
		WebElement el = waitForElementToBeVisible(by);
		getWait().until((ExpectedCondition<Boolean>) driver -> el.isEnabled());
		return el;
	}

	public boolean isElementPresent(By by) {
		try {
			WebElement el = waitForElementToBeVisible(by);
			return el != null;
		} catch (Exception e) {
			return false;
		}

	}

	public void clickOnElement(By by) {
		WebElement el = waitForElemenetToBeClickable(by);
		el.click();
	}

	public void selectValueFromDropdown(By by, String value) {
		WebElement el = waitForElementToBeEnabled(by);
		new Select(el).selectByVisibleText(value);
	}

	public void sendKeys(By by, String text) {
		WebElement el = waitForElemenetToBeClickable(by);
		el.clear();
		el.sendKeys(text);
	}

	public void sendKeysWithSubmit(By by, String text) {
		WebElement el = waitForElemenetToBeClickable(by);
		el.clear();
		el.sendKeys(text);
		el.sendKeys(Keys.ENTER);
	}

	public void sendKeys(By by, Keys key) {
		WebElement el = waitForElemenetToBeClickable(by);
		el.sendKeys(key);
	}

	public String getText(By by) {
		WebElement el = waitForElemenetToBeClickable(by);
		return el.getText();
	}

	public void quitBrowser() {
		if (driver != null)
			driver.quit();
		driver = null;
	}

	public void goToURL(String url) {
		getBrowser().navigate().to(url);
	}

	public void waitForTitle(String title) {
		getWait().until(ExpectedConditions.titleContains(title));
	}

	public void setCheckBoxValue(By by, boolean value) {
		WebElement el = waitForElemenetToBeClickable(by);
		if (el.isSelected() && !value)
			el.click();
		if (!el.isSelected() && value)
			el.click();
	}

	public void sleep(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (Exception e) {

		}
	}

	public boolean pageContainsValue(String value) {
		return getBrowser().getPageSource().toLowerCase().contains(value.toLowerCase());
	}

	public void openURL(String url) {
		getBrowser().get(url);
	}

	public void click_JS(By leagueBtn) {
		WebElement el = getBrowser().findElement(leagueBtn);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", el);
	}

	public static String getStringOfLength(int k) {
		String alphabet = "abcdefghijklmnopqrstuvwxyz";
		String uid = "";
		Random random = new Random();
		for (int i = 0; i < k; i++) {
			char c = alphabet.charAt(random.nextInt(26));
			uid += c;
		}
		return uid;
	}

	public void verifySuccessMsg(String success) {
		commonWEBHelper.waitForElementToContainsMatchingText(successMsg, success);
	}

}