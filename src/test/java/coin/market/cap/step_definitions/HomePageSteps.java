package coin.market.cap.step_definitions;

import static coin.market.cap.step_definitions.Hooks.commonWEBHelper;

import coin.market.cap.po.HomePage;
import coin.market.cap.utils.PropertyReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class HomePageSteps {
	HomePage homePage = new HomePage(commonWEBHelper.getBrowser());
	
	@Given("I open the url")
    public void i_open_the_url() {
		homePage.openURL(PropertyReader.readEnvConfigFile("url"));
    }
	
	@Given("Select Show rows dropdown value to {string}")
	public void select_show_rows_dropdown_value_to(String rows) {
		homePage.selectRowsDropdownvalueTo(rows);
	}
	
	@Then("Capture the page content to the excel file {string} and sheet {string}")
	public void capture_table_content_to_excel_file_and_sheet(String file,String sheet) {
		homePage.selectRowsDropdownvalueTo(file,sheet);
	}
	
	@Given("I click on filters")
    public void i_click_on_filters() {
		homePage.clickOnFilters();
    }
	
	@Given("Select {string} from algorithm")
	public void select_algorithm_option(String algorithm) {
		homePage.selectAlgorithm(algorithm);
	}
	
	@Given("Click 1 more Filter button")
    public void click_one_more_filter_button() {
		homePage.clickOneMoreFilterBtn();
    }
	
	@Given("I select Mineable")
    public void i_select_mineable() {
		homePage.selectMineable();
    }
	
	@Given("I select All cryptocurrencies")
    public void i_select_all_cryptocurrencies() {
		homePage.selectAllCryptocurrencies();
    }
	
	@Given("I select coins")
    public void i_select_coins() {
		homePage.selectCoins();
    }
	
	@Given("I click Price")
    public void i_click_price() {
		homePage.clickPrice();
    }
	
	@Given("I set min and max value to {string} and {string}")
    public void i_set_min_max_price(String min,String max) {
		homePage.setMinMaxPrice(min, max);
    }
	
	@Given("I click Apply Filter")
    public void i_click_apply_filter() {
		homePage.clickApplyFilter();
    }
	
	@Given("I click Show results")
    public void i_click_show_results() {
		homePage.clickShowResults();
    }
}
