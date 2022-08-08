package autotestsuite.actions;

import autotestsuite.pages.GoogleHomePage;
import autotestsuite.pages.GuardianNewsHomePage;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Open;

public class NavigateTo {
    public static Performable theGuardianNewsHomePage() {
        return Task.where("{0} open the Guardian News home page",
                Open.browserOn().the(GuardianNewsHomePage.class));
    }

    public static Performable theGoogleHomePage() {
        return Task.where("{0} open the Guardian News home page",
                Open.browserOn().the(GoogleHomePage.class));
    }
}
