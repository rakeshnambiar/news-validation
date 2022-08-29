package autotestsuite.actions;

import autotestsuite.pages.GuardianNewsHomePage;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Open;

import java.util.List;

public class News {
    public static Question<Integer> count() {
        return Question.about("the number of news articles")
                .answeredBy(actor -> GuardianNewsHomePage.NEWS_TITLES.resolveAllFor(actor).size());

    }
    public static Question<List<WebElementFacade>> title_list() {
        return Question.about("the number of news articles")
                .answeredBy(GuardianNewsHomePage.NEWS_TITLES::resolveAllFor);
    }

    public static Performable selectNewsTitle(int position) {
        return Task.where("{0} click on the new title",
                actor -> title_list().answeredBy(actor).get(position).click());
    }
}
