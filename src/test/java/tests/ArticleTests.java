package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;

@Epic("Tests for articles")

public class ArticleTests extends CoreTestCase {

    @Test
    @Features(value = {@Feature(value = "Search"), @Feature(value = "Article")})
    @DisplayName("Compare article title with the expected one")
    @Description ("We search article and find it in the search results")
    @Step("Starting testCompareArticleTitle")
    @Severity(value = SeverityLevel.BLOCKER)
    public void testCompareArticleTitle() throws Exception {

        String articleTitle;

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Appium");
        searchPageObject.clickByArticleWithSubstring("Appium");

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        if (lib.Platform.getInstance().isIOS()) {
            articleTitle = articlePageObject.getArticleTitleIOS("Appium");
        } else {
            articleTitle = articlePageObject.getArticleTitle();
        }

        Assert.assertEquals(
                "Unexpected title",
                "Appium",
                articleTitle);
    }

    @Test
    @Features(value = {@Feature(value = "Search"), @Feature(value = "Article")})
    @DisplayName("Scroll article page till the bottom of the page")
    @Description("We search an article, open it and scroll down till the footer is visible")
    @Step("Starting testSwipeArticle")
    @Severity(value = SeverityLevel.MINOR)
    public void testSwipeArticle() {

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Appium");
        searchPageObject.clickByArticleWithSubstring("Appium");

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        if (lib.Platform.getInstance().isAndroid()) {
            articlePageObject.waitForTitleElement();
        } else {
            articlePageObject.waitForTitleElementIOS("Appium");
        }
        articlePageObject.swipeToFooter();
    }

    @Test
    @Features(value = {@Feature(value = "Search"), @Feature(value = "Article")})
    @DisplayName("Check that article has the expected title")
    @Description("We search an article, open it and compare its title with the expected one")
    @Step("Starting testElementTitleIsPresent")
    @Severity(value = SeverityLevel.NORMAL)
    public void testElementTitleIsPresent() throws Exception {

        String search = "Appium";
        String article = "Appium";

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(search);
        searchPageObject.clickByArticleWithSubstring(article);

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        if (lib.Platform.getInstance().isAndroid()) {
            articlePageObject.waitForTitleElement();
        } else {
            articlePageObject.waitForTitleElementIOS(search);
        }
    }
}
