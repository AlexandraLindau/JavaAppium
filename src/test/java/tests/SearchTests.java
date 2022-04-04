package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.ui.SearchPageObject;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;

public class SearchTests extends CoreTestCase {

    @Test
    @Features(value = {@Feature(value = "Search")})
    @DisplayName("Check the placeholder of the search filed")
    @Description("We check that the placeholder of the search field corresponds to the expected one")
    @Step("Starting testSearchFieldPlaceholder")
    @Severity(value = SeverityLevel.MINOR)
    public void testSearchFieldPlaceholder() throws Exception {

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        if (lib.Platform.getInstance().isWeb()) {
            searchPageObject.initSearchInput();
        }
        searchPageObject.assertSearchFieldPlaceholder();
    }

    @Test
    @Features(value = {@Feature(value = "Search")})
    @DisplayName("Find article in the search results")
    @Description("Search the article and check it is presents in the search results")
    @Step("Starting testSearch")
    @Severity(value = SeverityLevel.BLOCKER)
    public void testSearch() {

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("JAVA");
        searchPageObject.waitForElementByTitleAndDescription("Java (programming language)", "Object-oriented programming language");

    }

    @Test
    @Features(value = {@Feature(value = "Search")})
    @DisplayName("Cancel search")
    @Description("Initialize searching and then cancel it")
    @Step("Starting testCancelSearch")
    @Severity(value = SeverityLevel.MINOR)
    public void testCancelSearch() {

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.waitForCancelButtonToAppear();
        searchPageObject.clickCancelSearch();
        searchPageObject.waitForCancelButtonToDisappear();
    }

    @Test
    @Features(value = {@Feature(value = "Search")})
    @DisplayName("Valid search request")
    @Description("Make a valid search request and check that the article is present in the search results")
    @Step("Starting testAmountOfNotEmptySearch")
    @Severity(value = SeverityLevel.BLOCKER)
    public void testAmountOfNotEmptySearch() {

        String searchLine = "Linkin Park Diskography";

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchLine);
        int amountOfSearchResults = searchPageObject.getAmountOfFoundArticles();

        Assert.assertTrue("Not enough searchLine result items was found", amountOfSearchResults > 0);

    }

    @Test
    @Features(value = {@Feature(value = "Search")})
    @DisplayName("Invalid search request")
    @Description("Make an invalid search request and check that the search result is empty")
    @Step("Starting testAmountOfEmptySearch")
    @Severity(value = SeverityLevel.MINOR)
    public void testAmountOfEmptySearch() {

        String searchLine = "werververv3r4ervre24";

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchLine);
        searchPageObject.waitForEmptyResultLabel();
        searchPageObject.assertThereIsNoSearchResult();

    }

    @Test
    @Features(value = {@Feature(value = "Search")})
    @DisplayName("Init and cancel search")
    @Description("Start searching an article and then cancel it. Verify the matching articled disappear from the search results")
    @Step("Starting testSearchResultsAndCancel")
    @Severity(value = SeverityLevel.MINOR)
    public void testSearchResultsAndCancel() {

        String searchLine = "Kotlin";
        String firstArticleNameSubstring = "Kotlin (programming language)";
        String secondArticleNameSubstring = "Kotlin-class destroyer";

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchLine);
        searchPageObject.waitForSearchResult(firstArticleNameSubstring);
        searchPageObject.waitForSearchResult(secondArticleNameSubstring);
        searchPageObject.clickCancelSearch();
        searchPageObject.assertArticleNotPresentInSearchResults("Kotlin");
        searchPageObject.assertArticleNotPresentInSearchResults("Kotlin (programming language)");
        searchPageObject.assertArticleNotPresentInSearchResults("Kotlin-class destroyer");
    }

    @Test
    @Features(value = {@Feature(value = "Search")})
    @DisplayName("Search articles by a valid value")
    @Description("Start searching articles. Check that the results contain the searched text")
    @Step("Starting testSearchResultsContainSearchInput")
    @Severity(value = SeverityLevel.BLOCKER)
    public void testSearchResultsContainSearchInput() throws Exception {

        String searchLine = "Java";

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchLine);

        searchPageObject.assertSearchResultContainsText(searchLine, 1);
        searchPageObject.assertSearchResultContainsText(searchLine, 2);
        searchPageObject.assertSearchResultContainsText(searchLine, 3);
    }

    @Test
    @Features(value = {@Feature(value = "Search")})
    @DisplayName("Search articles by a valid value")
    @Description("Start searching articles. Check that the results contain the expected articles with expected description")
    @Step("Starting testSearchResultsContainTitleAndDescription")
    @Severity(value = SeverityLevel.CRITICAL)
    public void testSearchResultsContainTitleAndDescription() {

        String searchLine = "Java";

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchLine);
        searchPageObject.waitForElementByTitleAndDescription("Java", "Island");
        searchPageObject.waitForElementByTitleAndDescription("JavaScript", "rogramming language");
        searchPageObject.waitForElementByTitleAndDescription("Java (programming language)", "Object-oriented programming language");
    }
}
