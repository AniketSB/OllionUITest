import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/java/Features/OllionUITest.feature", glue = "Stepdefinations", plugin = "html:target/JSONReports/report.html",
monochrome = true)
public class Runner {

}