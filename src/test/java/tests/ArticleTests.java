package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class ArticleTests extends CoreTestCase {

    @Test
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

        assertEquals(
                "Unexpected title",
                "Appium",
                articleTitle);
    }

    @Test
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
