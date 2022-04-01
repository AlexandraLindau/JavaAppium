package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;

public class ChangeAppConditionTests extends CoreTestCase {

    @Test
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
