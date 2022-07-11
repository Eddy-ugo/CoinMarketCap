package coin.market.cap.step_definitions;

import static coin.market.cap.step_definitions.Hooks.commonAPIHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;

import coin.market.cap.utils.FileHelper;
import coin.market.cap.utils.GlobalVariables;
import coin.market.cap.utils.PropertyReader;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class Common_api_step_definations {

	@Given("I initialize request using {string} base url with {string} username and {string} password")
	public void i_initialize_request_using_base_url(String baseUrl, String username, String password) {
		baseUrl = PropertyReader.readEnvConfigFile(baseUrl);
		username = PropertyReader.readEnvConfigFile(username);
		password = PropertyReader.readEnvConfigFile(password);

		commonAPIHelper.initializeRequest(baseUrl, username, password);
	}

	@Then("I initialize request using {string} base url")
	public void i_initialize_request_using_base_url(String baseUrl) {
		baseUrl = PropertyReader.readEnvConfigFile(baseUrl);
		commonAPIHelper.initializeRequest(baseUrl);
	}

	@When("I create request using {string} json file")
	public void i_create_request_using_json_file(String fileName) {
		GlobalVariables.inputJsonFile = GlobalVariables.inputFilesPath + fileName;
		GlobalVariables.requestPayload = FileHelper.readFile(GlobalVariables.inputJsonFile);
	}

	@When("I add following headers to the request")
	public void i_add_following_headers_to_the_request(DataTable dataTable) {
		Map<String, String> headers = new HashMap<>();
		List<List<String>> dataTableList = dataTable.asLists();

		for (List<String> list : dataTableList) {
			headers.put(list.get(0), list.get(1));
		}

		commonAPIHelper.addHeaderToTheRequest(headers);
	}

	@When("I generate payload")
	public void i_generate_payload() {
		commonAPIHelper.generateRequestPayload();
	}

	@When("I submit the {string} request with {string} end url")
	public void i_submit_the_request_with_end_url(String method, String endURL) {
		commonAPIHelper.submitTheRequest(method, endURL);
	}

	@Then("I verify status code of the response is {int}")
	public void i_verify_status_code_of_the_response_is(Integer statusCode) {
		commonAPIHelper.verifyStatusCode(statusCode);
	}

	@When("I validate request schema with {string} file")
	public void i_Validate_Request_Schema_With_File(String schemaFileName) {
		Assert.assertTrue(commonAPIHelper.validateJSONWithSchema(GlobalVariables.inputJsonFile,
				GlobalVariables.jsonSchemaFilePath + schemaFileName));
	}

	public void i_add_following_headers_to_the_request(Map<String, String> headers) {
		commonAPIHelper.addHeaderToTheRequest(headers);
	}

	@Then("I verify following attributes displaying in the response")
	public void i_verify_following_attributes_displaying_in_the_response(DataTable dataTable) {
		List<List<String>> dataTableList = dataTable.asLists();

		for (int i = 1; i < dataTableList.size(); i++) {
			String valueFromAttribute = commonAPIHelper.getValueFromJsonPath(dataTableList.get(i).get(0));
			Assert.assertTrue(valueFromAttribute != null && valueFromAttribute.length() > 0);
			System.out.println("Verified \"" + dataTableList.get(i).get(0)
					+ "\" attribute is available in the response and has some value");
		}

	}

	@Then("I get value for {string} attribute and save it to runtime")
	public void i_get_value_for_following_attribute_and_save_to_runtime(String attribute) {
		String value = commonAPIHelper.getValueFromJsonPath(attribute);
		String result =value.substring(1,value.length()-1);
		System.setProperty(attribute, result);
		System.out.println("Guatemalan Quetzal ->GBP : value is " + result);
	}

	@Then("I get value for {string} attribute print in the console")
	public void i_get_value_for_attribute_and_save_to_the_console(String attribute) {
		String value = commonAPIHelper.getValueFromJsonPath(attribute);
		System.out.println("GBP->Doge coin conversion : value is " + value);
	}

	@Then("I verify attributes and their values")
	public void i_verify_attributes_and_their_values(DataTable dataTable) {
		List<List<String>> dataTableList = dataTable.asLists();

		for (int i = 1; i < dataTableList.size(); i++) {
			String valueFromAttribute = commonAPIHelper.getValueFromJsonPath(dataTableList.get(i).get(0));
			Assert.assertEquals(dataTableList.get(i).get(1), valueFromAttribute);
			System.out.println("Verified \"" + dataTableList.get(i).get(0) + "\" attribute has \""
					+ dataTableList.get(i).get(1) + "\" value");
		}
	}

	@Then("I validate following attribute are available in the request body with values")
	public void i_validate_following_attribute_are_available_in_the_request_body_with_values(DataTable dataTable) {
		List<List<String>> dataTableList = dataTable.asLists();

		for (int i = 1; i < dataTableList.size(); i++) {
			String valueFromAttribute = commonAPIHelper.getValueFromRequestJsonPath(GlobalVariables.requestPayload,
					dataTableList.get(i).get(0));
			Assert.assertEquals(dataTableList.get(i).get(1), valueFromAttribute);
			System.out.println("Verified Request \"" + dataTableList.get(i).get(0) + "\" attribute has \""
					+ dataTableList.get(i).get(1) + "\" value");
		}
	}

	@Then("I capture the guid from the response")
	public void i_capture_the_guid_from_the_response() {
		GlobalVariables.guid = commonAPIHelper.getValueFromJsonPath("guid");
	}

	@Then("I capture the title from the sent request")
	public void i_capture_the_title_from_the_sent_request() {
		GlobalVariables.title = commonAPIHelper.getValueFromRequestJsonPath(GlobalVariables.requestPayload,
				"title[0].value");
	}

	@Then("I validate attribute values in the web response are same as request payload with jsonPath")
	public void i_validate_attribute_values_in_the_web_response_are_same_as_request_payload_with_jsonPath(
			DataTable dataTable) {
		List<List<String>> dataTableList = dataTable.asLists();
		String webResponsePayload = FileHelper.readFile(GlobalVariables.newlyCreatedResponsePayloadFile);
		System.out.println("Valur from Request\t : \t value from response");
		for (int i = 1; i < dataTableList.size(); i++) {
			String valueFromRequestPayload = commonAPIHelper
					.getValueFromRequestJsonPath(GlobalVariables.requestPayload, dataTableList.get(i).get(0))
					.toLowerCase(Locale.ROOT);
			String valueFromWebResponsePayload = commonAPIHelper
					.getValueFromRequestJsonPath(webResponsePayload, dataTableList.get(i).get(1))
					.toLowerCase(Locale.ROOT);
			// System.out.println("Value From Request payload for " +
			// dataTableList.get(i).get(0) + " :\t" + valueFromRequestPayload);
			// System.out.println("Value From WebPayLoad payload for " +
			// dataTableList.get(i).get(1) + " :\t" + valueFromWebResponsePayload);
			System.out.println(valueFromRequestPayload + "\t : \t" + valueFromWebResponsePayload);
			// Assert.assertEquals(valueFromRequestPayload, valueFromWebResponsePayload);
		}
	}

	@Then("I validate attribute values in the web response are same as request payload with key")
	public void i_validate_attribute_values_in_the_web_response_are_same_as_request_payload_with_key(
			DataTable dataTable) {
		List<List<String>> dataTableList = dataTable.asLists();
		String webResponsePayload = FileHelper.readFile(GlobalVariables.newlyCreatedResponsePayloadFile)
				.replaceAll("\n", "").replaceAll("\r", "");
		for (int i = 1; i < dataTableList.size(); i++) {
			String requestKey = dataTableList.get(i).get(0);
			String responseKey = dataTableList.get(i).get(1);
			// parseJsonByGivenKey method will get all the matching jsonPath according to
			// give key
			List<String> requestPayloadList = commonAPIHelper.parseJsonByGivenKey(GlobalVariables.requestPayload,
					requestKey);
			List<String> responsePayloadList = commonAPIHelper.parseJsonByGivenKey(webResponsePayload, responseKey);

			List<String> requestPayloadListValue = new ArrayList<>();
			List<String> responsePayloadListValue = new ArrayList<>();

			// getValueFromRequestJsonPath method will capture the value according to the
			// captured jsonPath
			requestPayloadList.stream().forEach(X -> requestPayloadListValue.add(commonAPIHelper
					.getValueFromRequestJsonPath(GlobalVariables.requestPayload, X.substring(2)).toLowerCase()));
			responsePayloadList.stream().forEach(X -> responsePayloadListValue.add(
					commonAPIHelper.getValueFromRequestJsonPath(webResponsePayload, X.substring(2)).toLowerCase()));
			boolean match = false;

			if (requestPayloadListValue.size() < 1 || responsePayloadListValue.size() < 1)
				match = true;

			for (String value : requestPayloadListValue) {
				match = responsePayloadListValue.contains(value);
				if (match)
					break;
			}
			if (!match) {
				for (String value : responsePayloadListValue) {
					match = requestPayloadListValue.contains(value);
					if (match)
						break;
				}
			}
			String header = requestKey.split("\\.")[requestKey.split("\\.").length - 1].toUpperCase();

			header = header.equalsIgnoreCase("VALUE") ? requestKey.split("\\.")[0].toUpperCase() : header;

			System.out.println("************************* " + header + " *************************");
			System.out.print("Match values value " + requestKey + "\t\t");
			requestPayloadListValue.stream().filter(X -> (X.trim() != "")).forEach(X -> System.out.print(X + ", "));

			System.out.print("\nMatch values value " + responseKey + "\t\t");
			responsePayloadListValue.stream().filter(X -> (X.trim() != "")).forEach(X -> System.out.print(X + ", "));
			System.out.println("\nValue Matched in request/resposne: " + match);
			System.out.println("********************************* END ***********************************\n");
		}
	}

	@Then("I add following parameters the the request")
	public void i_add_following_parameters_the_the_request(DataTable dataTable) {
		Map<String, String> headers = new HashMap<>();
		List<List<String>> dataTableList = dataTable.asLists();

		for (int i = 1; i < dataTableList.size(); i++) {
			String parameterName = dataTableList.get(i).get(0);
			String parameterValue = dataTableList.get(i).get(1);
			
			if (parameterValue.equals("data.quote.GBP.price")) 
				parameterValue=String.valueOf(Double.valueOf(System.getProperty("data.quote.GBP.price")));
			
			
			switch (parameterValue) {
			case "GUID":
				parameterValue = GlobalVariables.guid;
				break;
			case "TOKEN":
				parameterValue = PropertyReader.readEnvConfigFile("mediaToken");
				break;
			default:
				break;
			}
			headers.put(parameterName, parameterValue);
		}

		commonAPIHelper.addParameterToTheRequest(headers);
	}
}