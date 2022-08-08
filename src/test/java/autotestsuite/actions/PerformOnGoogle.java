package autotestsuite.actions;

import autotestsuite.pages.GoogleHomePage;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.*;
import org.openqa.selenium.Keys;

public class PerformOnGoogle {
    public static Performable search(String query) throws Exception {
        return Task.where("Entering the Search Query",
                Enter.theValue(query).into(GoogleHomePage.SearchInput),
                Hit.the(Keys.ENTER).into(GoogleHomePage.SearchInput)
        );
    }

    public static Performable clearSearchField() throws Exception {
        return Task.where("Clearing the Search field",
                Clear.field(GoogleHomePage.SearchInput)
        );
    }

}
