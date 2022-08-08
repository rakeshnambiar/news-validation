package autotestsuite.actions;

import autotestsuite.pages.GoogleHomePage;
import autotestsuite.pages.GuardianNewsHomePage;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Switch;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ManageCookies {
    private static WebDriver driver = Serenity.getWebdriverManager().getWebdriver();
    public static Performable accept(Actor actor) throws Exception {
        try {
            driver.manage().window().maximize();
            actor.attemptsTo(Switch.toFrame(1));
            return Task.where("Accepts the Cookies",
                    Click.on(GuardianNewsHomePage.COOKIE_YES).afterWaitingUntilPresent(),
                    Switch.toParentFrame());
        }catch (Exception ex) {
            throw new Exception("Exception: While Accepts the Cookie");
        }
    }

    public static void closeBanner() throws Exception {
        try {
            List<WebElement> buttons = driver.findElements(By.cssSelector("button[aria-label='Close']"));
            if(buttons.size() > 0) {
                JavascriptExecutor j = (JavascriptExecutor) driver;
                j.executeScript("arguments[0].click();", buttons.get(buttons.size() - 1));
            }
        }catch (Exception ex) {
            throw new Exception("Exception: While Closing the Banner in the page footer");
        }
    }

    public static Performable acceptGooglePolicyBy() throws Exception {
        try {
            return Task.where("Accepts the Cookies",
                    Click.on(GoogleHomePage.privacyAcceptButton).afterWaitingUntilPresent());
        }catch (Exception ex) {
            throw new Exception("Error: While accepts the Google privacy policy");
        }
    }
}
