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

@Severity(value = SeverityLevel.MINOR)

public class ChangeAppConditionTests extends CoreTestCase {

    @Test
    @Features(value = {@Feature(value = "Article"), @Feature(value = "Non-functional")})
    @DisplayName("Check that article title does not change after changing device orientation")
    @Description("We search an article, open it, rotate the device to landscape mode and then back to portrait mode")
    @Step("Starting testChangeScreenOrientationOnSearchResults")
    public void testChangeScreenOrientationOnSearchResults() throws Exception {

        if (lib.Platform.getInstance().isWeb()) {
            return;
        }

        String searchLine = "Java";
        String articleName = "Java (programming language)";
        String articleNameSubstring = "Object-oriented programming language";
        String titleBeforeRotation;
        String titleAfterRotation;
        String titleAfterSecondRotation;

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchLine);
        searchPageObject.clickByArticleWithSubstring(articleNameSubstring);

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        if (lib.Platform.getInstance().isAndroid()) {
            titleBeforeRotation = articlePageObject.getArticleTitle();
        } else {
            titleBeforeRotation = articlePageObject.getArticleTitleIOS(articleName);
        }

        this.rotateScreenLandscape();

        if (lib.Platform.getInstance().isAndroid()) {
            titleAfterRotation = articlePageObject.getArticleTitle();
        } else {
            titleAfterRotation = articlePageObject.getArticleTitleIOS(articleName);
        }

        Assert.assertEquals(
                "Article title have been changed after screen rotation",
                titleBeforeRotation,
                titleAfterRotation
        );

        this.rotateScreenPortrait();

        if (lib.Platform.getInstance().isAndroid()) {
            titleAfterSecondRotation = articlePageObject.getArticleTitle();
        } else {
            titleAfterSecondRotation = articlePageObject.getArticleTitleIOS(articleName);
        }

        Assert.assertEquals(
                "Article title have been changed after second screen rotation",
                titleBeforeRotation,
                titleAfterSecondRotation
        );
    }

    @Test
    @Features(value = {@Feature(value = "Search"), @Feature(value = "Non-functional")})
    @DisplayName("Check that article title does not change after running the app in the background")
    @Description("We search an article and run the app in the background")
    @Step("Starting testCheckSearchArticleInBackground")
    public void testCheckSearchArticleInBackground() {

        if (lib.Platform.getInstance().isWeb()) {
            return;
        }

        String searchLine = "Appium";

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchLine);
        searchPageObject.waitForSearchResult(searchLine);

        this.backgroundApp(2);

        searchPageObject.waitForSearchResult(searchLine);

    }
}
