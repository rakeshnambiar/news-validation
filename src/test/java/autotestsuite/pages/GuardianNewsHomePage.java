package autotestsuite.pages;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.pages.PageObject;
import org.openqa.selenium.WebElement;

import java.util.List;

@DefaultUrl("https://www.theguardian.com/tone/news")
public class GuardianNewsHomePage extends PageObject {

    @FindBy(css = "a.u-faux-block-link__overlay.js-headline-text")
    List<WebElement> news_headings;
    public static final Target COOKIE_YES = Target.the("Cookies Yes, I am happy").locatedBy("button[title^='Yes']");

    public int getHeadlinesCount() throws Exception{
        return news_headings.size();
    }

    public void clickOnNewHeading(int position) throws Exception {
        if (position <  this.getHeadlinesCount()){
            clickOn(news_headings.get(position));
        } else {
            throw new Exception("Error: Invalid position");
        }
    }
}
