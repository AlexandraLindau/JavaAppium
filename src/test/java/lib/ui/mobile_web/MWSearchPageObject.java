package lib.ui.mobile_web;

import lib.ui.SearchPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWSearchPageObject extends SearchPageObject {

    static {
        SEARCH_INIT_ELEMENT = "css:button#searchIcon";
        PLACEHOLDER_ELEMENT = "xpath://input[@class='search mw-ui-background-icon-search']";
        SEARCH_INPUT = "css:form>input[type='search']";
        SEARCH_CANCEL_BUTTON = "css:div.header-action > button";
//        SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://div[contains(text(),'{SUBSTRING}')]";
        SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://h3[contains(.,'{SUBSTRING}')]";
        SEARCH_RESULT_BY_INDEX_TPL = "xpath:(//*[@class='page-summary']/a/h3)[{SEARCH_RESULT_INDEX}]";
        SEARCH_RESULT_BY_SUBSTRING_IN_TITLE_AND_DESCRIPTION_TPL = "xpath://li[@class='page-summary' and @title='{SUBSTRING_1}']/a/div[@class='wikidata-description' and contains(text(),'{SUBSTRING_2}')]/../..";
        SEARCH_RESULT_ELEMENT = "xpath://li[@class='page-summary']";
        SEARCH_EMPTY_RESULT_ELEMENT = "xpath://p[@class='without-results']";
    }

    public MWSearchPageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
