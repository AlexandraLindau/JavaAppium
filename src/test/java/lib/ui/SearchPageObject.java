package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;
import lib.Platform;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class SearchPageObject extends MainPageObject {

    protected static String
            PLACEHOLDER_ELEMENT,
            SEARCH_INIT_ELEMENT,
            SEARCH_INPUT,
            SEARCH_CANCEL_BUTTON,
            SEARCH_RESULT_BY_SUBSTRING_TPL,
            SEARCH_RESULT_BY_INDEX_TPL,
            SEARCH_RESULT_BY_SUBSTRING_IN_TITLE_AND_DESCRIPTION_TPL,
            SEARCH_RESULT_ELEMENT,
            SEARCH_EMPTY_RESULT_ELEMENT;

    public SearchPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    /* Template methods */
    private static String getResultSearchElement(String substring) {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }

    private static String getResultSearchElementByIndex(int id) {
        return SEARCH_RESULT_BY_INDEX_TPL.replace("{SEARCH_RESULT_INDEX}", String.valueOf(id));
    }

    private static String getResultSearchElementByTextInTitleAndDescription(String title, String description) {
        return SEARCH_RESULT_BY_SUBSTRING_IN_TITLE_AND_DESCRIPTION_TPL
                .replace("{SUBSTRING_1}", title)
                .replace("{SUBSTRING_2}", description);
    }
    /* Template methods */

    @Step("Initializing the search field")
    public void initSearchInput() {
        this.waitForElementPresent(SEARCH_INIT_ELEMENT, "Cannot find search input element", 5);
        this.waitForElementAndClick(SEARCH_INIT_ELEMENT, "Cannot find and click search init element", 5);
    }

    @Step("Waiting for cancel button to appear")
    public void waitForCancelButtonToAppear() {
        this.waitForElementPresent(SEARCH_CANCEL_BUTTON, "Cannot find search cancel button", 5);
    }

    @Step("Waiting for cancel button to disappear")
    public void waitForCancelButtonToDisappear() {
        this.waitForElementNotPresent(SEARCH_CANCEL_BUTTON, "Search cancel button is still present", 5);
    }

    @Step("Click 'Cancel search'")
    public void clickCancelSearch() {
        this.waitForElementAndClick(SEARCH_CANCEL_BUTTON, "Cannot find and click cancel search button", 5);
    }

    @Step("Enter text in the search field")
    public void typeSearchLine(String searchLine) {
        this.waitForElementAndSendKeys(SEARCH_INPUT, searchLine, "Cannot find and type into search input", 5);
    }

    @Step("Wait for search results to appear")
    public void waitForSearchResult(String substring) {
        String searchResultXpath = getResultSearchElement(substring);
        this.waitForElementPresent(searchResultXpath, "Cannot find search result with substring " + substring);
    }

    @Step("Open article by its name")
    public void clickByArticleWithSubstring(String substring) {
        String searchResultXpath = getResultSearchElement(substring);
        this.waitForElementAndClick(searchResultXpath, "Cannot find and click search result with substring " + substring, 10);
    }

    @Step("Get amount of articled in the search results")
    public int getAmountOfFoundArticles() {
        this.waitForElementPresent(
                SEARCH_RESULT_ELEMENT,
                "Cannot find anything by the request",
                15
        );
        return this.getAmountOfElements(SEARCH_RESULT_ELEMENT);
    }

    @Step("Wait for search results to be empty")
    public void waitForEmptyResultLabel() {
        this.waitForElementPresent(SEARCH_EMPTY_RESULT_ELEMENT, "Cannot find empty result element", 15);
    }

    @Step("Assert that search result is empty")
    public void assertThereIsNoSearchResult() {
        this.assertElementNotPresent(SEARCH_RESULT_ELEMENT, "We supposed not to find any results");
    }

    @Step("Assert that search field placeholder corresponds to the expected result")
    public void assertSearchFieldPlaceholder() throws Exception {
        if (Platform.getInstance().isWeb()) {
            this.assertWebElementHasPlaceholder(PLACEHOLDER_ELEMENT, "Search Wikipedia", "Search field placeholder is not 'Search Wikipedia'");
        } else {
            this.assertElementHasText(SEARCH_INIT_ELEMENT, "Search Wikipedia", "Search field placeholder is not 'Search Wikipedia'");
        }
    }

    @Step("Assert that article is not present in the search result")
    public void assertArticleNotPresentInSearchResults(String articleSubstring) {
        String searchResultXpath = getResultSearchElement(articleSubstring);
        this.assertElementNotPresent(searchResultXpath, "Article with the name " + articleSubstring + " is still shown in the search results");
    }

    @Step("Assert that search result contains text")
    public void assertSearchResultContainsText(String searchLine, int index) throws Exception {
        String searchResultXpath = getResultSearchElementByIndex(index);
        this.assertElementContainsText(searchResultXpath, searchLine, "Search result does not contain string " + searchLine);
    }

    @Step("Wait for search results to appear with a title and description")
    public void waitForElementByTitleAndDescription(String title, String description) {
        String searchResultXpath = getResultSearchElementByTextInTitleAndDescription(title, description);
        this.waitForElementPresent(searchResultXpath, "Cannot find results with title: " + title +
                " and description: " + description + "\nby xpath: " + searchResultXpath);
    }
}
