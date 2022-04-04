package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.ui.*;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import lib.ui.factories.MyListPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;

@Severity(value = SeverityLevel.NORMAL)

public class MyListsTests extends CoreTestCase {

    private final static String folderName = "Learning programming";
    private final static String login = "Test2022-31";
    private final static String password = "Lindau7377!";

    @Test
    @Features(value = {@Feature(value = "Reading list"),@Feature(value = "Article")})
    @DisplayName("Save and delete one article form 'Saved'")
    @Description("We save one article to 'Saved' and then delete it.  Check that it has been deleted")
    @Step("Starting testSaveFirstArticleToMyList")
    public void testSaveFirstArticleToMyList() throws Exception {

        String article = "Appium";

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Appium");
        searchPageObject.clickByArticleWithSubstring(article);

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        if (Platform.getInstance().isIOS()) {
            articlePageObject.waitForTitleElementIOS(article);
        } else {
            articlePageObject.waitForTitleElement();
        }

        if (Platform.getInstance().isAndroid()) {
            articlePageObject.addArticleToMyList(folderName);
        } else if (lib.Platform.getInstance().isIOS()){
            articlePageObject.addFirstArticleToMySaved();
        } else {
            articlePageObject.addArticleToMySaved();
        }

        if (lib.Platform.getInstance().isWeb()) {
            AuthorizationPageObject authorizationPageObject = new AuthorizationPageObject(driver);
            authorizationPageObject.clickAuthButton();
            authorizationPageObject.enterLoginData(login, password);
            authorizationPageObject.submitForm();

            articlePageObject.waitForTitleElement();
            if (articlePageObject.getArticleTitle().equals("Central user log in")) {
                authorizationPageObject.returnAfterAuthError();
            }

            Assert.assertEquals("We are not on the same page after login",
                    article,
                    articlePageObject.getArticleTitle());

        }

        articlePageObject.closeArticle();

        NavigationUI navigationUI = NavigationUIFactory.get(driver);
        navigationUI.openNavigation();
        navigationUI.clickMyLists();

        MyListsPageObject myListsPageObject = MyListPageObjectFactory.get(driver);
        if (Platform.getInstance().isAndroid()) {
            myListsPageObject.openFolderByName(folderName);
        }
        myListsPageObject.swipeByArticleToDelete(article);
    }

    @Test
    @Features(value = {@Feature(value = "Reading list"),@Feature(value = "Article")})
    @DisplayName("Save two articles and delete one article form 'Saved'")
    @Description("We save two articles to 'Saved' and then delete one of them.  Check that the other article is still in 'Saved'")
    @Step("Starting testSaveTwoArticlesAndDeleteOne")
    public void testSaveTwoArticlesAndDeleteOne() throws Exception {

        String firstArticle = "Kotlin (programming language)";
        String secondArticle = "Appium";

        // Find and add the 1st article

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Kotlin");
        searchPageObject.clickByArticleWithSubstring(firstArticle);

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        if (Platform.getInstance().isIOS()) {
            articlePageObject.waitForTitleElementIOS(firstArticle);
        } else {
            articlePageObject.waitForTitleElement();
        }

        if (Platform.getInstance().isAndroid()) {
            articlePageObject.addArticleToMyList(folderName);
        } else if (lib.Platform.getInstance().isIOS()){
            articlePageObject.addFirstArticleToMySaved();
        } else {
            articlePageObject.addArticleToMySaved();
        }

        if (lib.Platform.getInstance().isWeb()) {
            AuthorizationPageObject authorizationPageObject = new AuthorizationPageObject(driver);
            authorizationPageObject.clickAuthButton();
            authorizationPageObject.enterLoginData(login, password);
            authorizationPageObject.submitForm();

            articlePageObject.waitForTitleElement();
            if (articlePageObject.getArticleTitle().equals("Central user log in")) {
                authorizationPageObject.returnAfterAuthError();
            }

            Assert.assertEquals("We are not on the same page after login",
                    firstArticle,
                    articlePageObject.getArticleTitle());

        }

        articlePageObject.closeArticle();

        // Find and add the 2nd article

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Appium");
        searchPageObject.clickByArticleWithSubstring(secondArticle);

        if (Platform.getInstance().isAndroid()) {
            articlePageObject.addArticleToMyExistingList(folderName);
        } else {
            articlePageObject.addArticleToMySaved();
        }

        // Return to the main page

        articlePageObject.closeArticle();

        // Open folder with articles and delete one

        NavigationUI navigationUI = NavigationUIFactory.get(driver);
        navigationUI.openNavigation();
        navigationUI.clickMyLists();

        MyListsPageObject myListsPageObject = MyListPageObjectFactory.get(driver);
        if (Platform.getInstance().isAndroid()) {
            myListsPageObject.openFolderByName(folderName);
        }
        myListsPageObject.swipeByArticleToDelete(firstArticle);

        // Check that the 2nd article is still in 'Saved'

        myListsPageObject.assertArticleIsPresent(secondArticle);

        // Open saved article and verify the name

        myListsPageObject.openSavedArticleByTitle(secondArticle);
        articlePageObject.assertArticleNameIsCorrect(secondArticle);
    }
}
