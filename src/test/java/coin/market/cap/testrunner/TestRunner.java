package coin.market.cap.testrunner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = { "src/test/resources/features" }, glue = {
		"coin.market.cap.step_definitions" }, tags = "", dryRun = false, publish = false)

public class TestRunner {
}
