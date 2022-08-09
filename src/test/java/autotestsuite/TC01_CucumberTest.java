package autotestsuite;

import autotestsuite.tags.Regression;
import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;


@RunWith(CucumberWithSerenity.class)
@CucumberOptions(tags = "@Regression",
        plugin = {"pretty"},
        features = "src/test/resources/features"
)
@Category({Regression.class})
public class TC01_CucumberTest {}
