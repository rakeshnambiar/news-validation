package autotestsuite.pages;

import net.serenitybdd.core.Serenity;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.pages.PageObject;
import org.openqa.selenium.WebDriver;

public class NewsArticlePage extends PageObject {
    @FindBy(css = "h1")
    WebElementFacade article_heading;

    public String getNewHeading() throws Exception {
        if(article_heading.isCurrentlyVisible()) {
            return article_heading.getText();
        } else {
            String page_url = Serenity.getDriver().getCurrentUrl();
            String[] page_url_parts = page_url.split("/");
            return page_url_parts[page_url_parts.length-1].replace("-"," ");
        }

    }
}
