package autotestsuite.pages;

import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.pages.PageObject;
import org.openqa.selenium.By;

@DefaultUrl("http://www.google.co.uk")
public class GoogleHomePage extends PageObject {
    public static final Target privacyAcceptButton =  Target.the("Privacy policy accept identifier").locatedBy("#L2AGLb");
    public static final Target SearchInput =  Target.the("Privacy policy accept identifier").located(By.name("q"));

}
