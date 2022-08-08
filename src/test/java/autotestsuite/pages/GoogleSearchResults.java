package autotestsuite.pages;

import autotestsuite.utilities.StringHelper;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.pages.PageObject;

import java.util.*;
import java.util.stream.Collectors;

public class GoogleSearchResults extends PageObject {
    @FindBy(css = "title-with-lhs-icon > div > h3")
    List<WebElementFacade> sections;

    @FindBy(css = ".iRPxbe div[role='heading']")
    List<WebElementFacade> top_story_headings;

    @FindBy(css = "div[aria-label='More news']")
    WebElementFacade more_news;

    @FindBy(css = "div[role='heading']")
    List<WebElementFacade> news_titles;

    @FindBy(css = "h3")
    List<WebElementFacade> search_titles;

    @FindBy(css = ".hdtb-mitem a")
    List<WebElementFacade> search_category_link;

    @FindBy(id = "hdtb-tls")
    WebElementFacade tools_button;

    @FindBy(css = ".KTBKoe")
    List<WebElementFacade> time_drop_down;

    @FindBy(css = "a[role='menuitem']")
    List<WebElementFacade> time_drop_down_items;

    public List<String> getTopStoriesIfAvailable() throws Exception {
        List<String> top_stories_title = new ArrayList<>();
        boolean top_story_available = false;
        try {
            for (WebElementFacade section : sections) {
                if (section.getText().equalsIgnoreCase("Top stories")) {
                    top_story_available = true;
                    if (top_story_headings.size() < 3) {
                        return top_stories_title;
                    } else {
                        clickOn(more_news);
                        break;
                    }
                }
            }
            if (top_story_available) {
                news_titles.forEach(title -> top_stories_title.add(title.getText()));
            }
        } catch (Exception ex) {
            throw new Exception("Error: While verifying the top stories section");
        }
        return top_stories_title;
    }

    public String formatQueryString(String input) throws Exception {
        String formatted_query;
        try {
            formatted_query = StringHelper.removeSymbols(input);
            formatted_query = formatted_query.replace("live", "");
            formatted_query = StringHelper.removeMultipleBlankSpaces(formatted_query);
        } catch (Exception ex) {
            throw new Exception("Error: While formatting the Query String");
        }
        return formatted_query;
    }

    public HashMap<String, String> getKeySearchTerms(String input) throws Exception {
        HashMap<String, String> top_terms = new HashMap<>();
        List<String> keyTerms;
        try {
            String[] terms = input.split(" ");
            keyTerms = Arrays.stream(terms).map(String::toLowerCase).filter(term -> term.length() > 3).collect(Collectors.toList());
            if (keyTerms.size() > 4) {
                top_terms.put("Term1", keyTerms.get(0));
                top_terms.put("Term2", keyTerms.get(1));
                top_terms.put("Term3", keyTerms.get(2));
                top_terms.put("Term4", keyTerms.get(3));
                top_terms.put("Term5", keyTerms.get(4));
            } else {
                int count = 1;
                for (String keyTerm : keyTerms) {
                    top_terms.put("Term" + count, keyTerm);
                    count++;
                }
            }
            if (top_terms.size() < 5) {
                int counter = top_terms.size() + 1;
                for (int i = counter; counter < 6; ++counter) {
                    top_terms.put("Term" + i, "");
                }
            }
        } catch (Exception ex) {
            throw new Exception("Error: While getting the key terms from the search string");
        }
        return top_terms;
    }

    public List<String> checkExactMatchCount(List<String> input, String term) throws Exception {
        return input.stream().map(String::toLowerCase).filter(term.toLowerCase()::equals).collect(Collectors.toList());
    }

    public List<String> checkKeyTermMatch(List<String> input, HashMap<String, String> term) throws Exception {
        List<String> combined_results = new ArrayList<>();
        List<String> terms_found1 = input.stream().map(String::toLowerCase)
                .filter(word -> word.contains(term.get("Term1")))
                .filter(word -> word.contains(term.get("Term2")))
                .collect(Collectors.toList());
        List<String> terms_found2 = input.stream().map(String::toLowerCase)
                .filter(word -> word.contains(term.get("Term3")))
                .filter(word -> word.contains(term.get("Term4")))
                .collect(Collectors.toList());
        List<String> terms_found3 = input.stream().map(String::toLowerCase)
                .filter(word -> word.contains(term.get("Term1")))
                .filter(word -> word.contains(term.get("Term5")))
                .collect(Collectors.toList());
        combined_results.addAll(terms_found1);
        combined_results.addAll(terms_found2);
        combined_results.addAll(terms_found3);
        return combined_results;
    }

    public List<String> getSearchTitles() throws Exception {
        List<String> result_titles = new ArrayList<>();
        search_titles.forEach(title -> result_titles.add(title.getText()));
        return result_titles;
    }

    public boolean clickSearchCategoryLink(String category) throws Exception {
        waitABit(2000);
        for (WebElementFacade category_link : search_category_link) {
            if (category_link.getText().equalsIgnoreCase(category)) {
                clickOn(category_link);
                return true;
            }
        }
        return false;
    }

    public boolean filterSearchResults(String time_window) throws Exception {
        try {
            clickOn(tools_button);
            waitABit(500);
            clickOn(time_drop_down.get(1));
            waitABit(500);
            boolean selected = false;
            for (WebElementFacade menu_item : time_drop_down_items) {
                if (menu_item.getText().equalsIgnoreCase(time_window)) {
                    clickOn(menu_item);
                    waitABit(2000);
                    selected = true;
                    break;
                }
            }
            return selected;
        } catch (Exception ex) {
            throw new Exception("Error: While filtering the search results");
        }
    }
}
