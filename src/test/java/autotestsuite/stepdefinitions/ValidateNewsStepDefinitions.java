package autotestsuite.stepdefinitions;

import autotestsuite.actions.PerformOnGoogle;
import autotestsuite.actions.ManageCookies;
import autotestsuite.actions.NavigateTo;
import autotestsuite.pages.GoogleHomePage;
import autotestsuite.pages.GoogleSearchResults;
import autotestsuite.pages.GuardianNewsHomePage;
import autotestsuite.pages.NewsArticlePage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.matchers.WebElementStateMatchers;
import net.serenitybdd.screenplay.questions.WebElementQuestion;
import net.serenitybdd.screenplay.waits.Wait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ValidateNewsStepDefinitions {
    private static final Logger logger = LoggerFactory.getLogger(ValidateNewsStepDefinitions.class);
    GuardianNewsHomePage guardianNewsHomePage;
    NewsArticlePage newsArticlePage;
    GoogleSearchResults googleSearchResults;
    private String main_news_title;
    private String formatted_search_title;

    private int exact_count;
    private List<String> news_titles;
    private HashMap<String, String> key_terms;

    @Given("{actor} is navigated to the Guardian news home page")
    public void navigateToNewsHomePage(Actor actor) throws Exception {
        logger.info("Opening application home page");
        actor.wasAbleTo(NavigateTo.theGuardianNewsHomePage());
        logger.info("Accepting Cookies if exists");
        actor.wasAbleTo(ManageCookies.accept(actor));
    }

    @When("{actor} selected the one of the top {int} articles")
    public void goToTheLatestNews(Actor actor, int limit) throws Exception {
        logger.info("Going to verify the news headings are displayed or not");
        assertThat(guardianNewsHomePage.getHeadlinesCount()).isGreaterThan(0);
        logger.info("Clicking on the very first news headline");
        Random rand = new Random();
        int index = rand.nextInt(limit);
        if(index > 0){
            index -= 1;
        }
        guardianNewsHomePage.clickOnNewHeading(index);
        logger.info("Closing ad banner in the page footer if exists");
        ManageCookies.closeBanner();
    }

    @And("{actor} noted down news headlines of the selected item")
    public void noteTheArticleTitle(Actor actor) throws Exception {
        main_news_title = newsArticlePage.getNewHeading();
        actor.remember("Main_News_Title", main_news_title);
        logger.info("Main headline selected: " + main_news_title);
        Serenity.reportThat("Main headline selected: " + main_news_title, () -> {});
    }

    @And("{actor} decided to check the authenticity of these top news on {string}")
    public void confirmAuthenticity(Actor actor, String url) throws Exception {
        logger.info("Going to the Google website");
        actor.attemptsTo(Open.url(url));
        actor.attemptsTo(Wait.until(
                WebElementQuestion.the(GoogleHomePage.privacyAcceptButton) , WebElementStateMatchers.isEnabled()
        ).forNoMoreThan(30).seconds());
        logger.info("Accepting google privacy policy");
        actor.wasAbleTo(ManageCookies.acceptGooglePolicyBy());
    }

    @And("with some formatting {actor} tried some searches on google website")
    public void searchAndCompareResults(Actor actor) throws Exception {
        formatted_search_title = googleSearchResults.formatQueryString(main_news_title);
        logger.info("Searching with the formatted query string: " + formatted_search_title);
        Serenity.reportThat("Searching with the formatted query string: " + formatted_search_title, () -> {});
        actor.attemptsTo(PerformOnGoogle.search(formatted_search_title));
        logger.info("Getting the top stories if available");
        news_titles = googleSearchResults.getTopStoriesIfAvailable();
        if(news_titles.size() == 0) {
            logger.info("Top story not found. Getting all titles");
            news_titles = googleSearchResults.getSearchTitles();
        }
        logger.info("Finding the key terms in the query string for verification");
        key_terms = googleSearchResults.getKeySearchTerms(formatted_search_title);
        logger.info("Key terms for verification: " + key_terms);
        Serenity.reportThat("Key terms for verification: " + key_terms, () -> {});
        List<String> exact_terms = googleSearchResults.checkExactMatchCount(news_titles, main_news_title);
        logger.info("INFO: Search URL: " + Serenity.getDriver().getCurrentUrl());
        exact_count = exact_terms.size();
        logger.info("Exact match available is: " + exact_count);
        if(news_titles.size() > 0 && exact_count > 0) {
            assertThat(exact_count).isGreaterThan(0);
        }
    }

    @And("without doing any formatting {actor} tried to do a search on google website")
    public void makeExactSearch(Actor actor) throws Exception {
        logger.info("Searching without any formatting: " + main_news_title);
        Serenity.reportThat("Searching without any formatting: " + main_news_title, () -> {});
        actor.attemptsTo(PerformOnGoogle.search(main_news_title));
        news_titles = googleSearchResults.getTopStoriesIfAvailable();
        if(news_titles.size() == 0) {
            logger.info("Top story not found. Getting all titles");
            news_titles = googleSearchResults.getSearchTitles();
        }
    }

    @Then("{actor} should see similar news reported from at least {int} others sources on the Google search results")
    public void verifyMatchesOnGoogle(Actor actor, int min_match_expected) throws Exception {
        if(exact_count > min_match_expected) {
            logger.info("INFO: Minimum matches found. Match count: " + exact_count);
            Serenity.reportThat("INFO: Minimum matches found. Match count: " + exact_count, () -> {});
        } else if (googleSearchResults.checkKeyTermMatch(news_titles, key_terms).size() > min_match_expected) {
            List<String> partial_matched_titles = googleSearchResults.checkKeyTermMatch(news_titles, key_terms);
            logger.info("Partially matching titles are: " + partial_matched_titles);
            logger.info("INFO: Search URL: " + Serenity.getDriver().getCurrentUrl());
            Serenity.reportThat("Partially matching titles are: " + partial_matched_titles, () -> {});
        } else {
            logger.info("Making a keyword search: " + key_terms);
            Serenity.reportThat("Making a keyword search: " + key_terms, () -> {});
            actor.attemptsTo(
                    PerformOnGoogle.clearSearchField(),
                    //PerformOnGoogle.search("allintitle:" + " " + key_terms.get("Term1") + " " + key_terms.get("Term2") + " " + key_terms.get("Term3"))
                    PerformOnGoogle.search(key_terms.get("Term1") + " " + key_terms.get("Term2") + " " + key_terms.get("Term3") + " + news")
            );
            googleSearchResults.clickSearchCategoryLink("All");
            googleSearchResults.filterSearchResults("Past 24 hours");
            List<String> results_titles = googleSearchResults.getSearchTitles();
            if(results_titles.size() == 0) {
                googleSearchResults.clickSearchCategoryLink("All");
                googleSearchResults.filterSearchResults("Past 24 hours");
                results_titles = googleSearchResults.getSearchTitles();
            }
            List<String> matches = googleSearchResults.checkKeyTermMatch(results_titles, key_terms);
            logger.info("INFO: Matches found: " + matches);
            Serenity.reportThat("INFO: Matches found: " + matches, () -> {});
            logger.info("INFO: Search URL: " + Serenity.getDriver().getCurrentUrl());
            assertThat(matches.size()).isGreaterThanOrEqualTo(min_match_expected);
        }
    }

    @Then("{actor} should able to see at least {int} exact match on the Google search results")
    public void verifyExactMatchOnGoogle(Actor actor, int min_match_expected) throws Exception {
        List<String> exact_terms = googleSearchResults.checkExactMatchCount(news_titles, main_news_title);
        logger.info("INFO: Search URL: " + Serenity.getDriver().getCurrentUrl());
        exact_count = exact_terms.size();
        logger.info("Exact match available is: " + exact_count);
        assertThat(exact_count).isGreaterThanOrEqualTo(min_match_expected);
    }
}
