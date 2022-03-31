package lib.ui;

import lib.Platform;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class ArticlePageObject extends MainPageObject {

    protected static String
            TITLE,
            TITLE_TPL,
            FOOTER_ELEMENT,
            OPTIONS_BUTTON,
            OPTIONS_ADD_TO_MY_LIST_BUTTON,
            OPTIONS_REMOVE_FROM_MY_LIST_BUTTON,
            ADD_TO_MY_EXISTING_LIST_OVERLAY,
            ADD_TO_MY_LIST_OVERLAY,
            MY_LIST_NAME_INPUT,
            MY_LIST_OK_BUTTON,
            CLOSE_ARTICLE_BUTTON,
            CLOSE_POPUP_IOS,
            TAP_TO_GO_HOME_NOTIFICATION;

    /* Template methods */
    private static String getFolderElement(String folderName) {
        return ADD_TO_MY_EXISTING_LIST_OVERLAY.replace("{FOLDER_NAME}", folderName);
    }

    private static String getTitleElement(String substring) {
        if (Platform.getInstance().isIOS()) {
            return TITLE_TPL.replace("{SUBSTRING}", substring);
        } else {
            return TITLE;
        }
    }
    /* Template methods */

    public ArticlePageObject(RemoteWebDriver driver) {
        super(driver);
    }

    public WebElement waitForTitleElement() {
        return this.waitForElementPresent(TITLE, "Cannot find article on the page", 15);
    }

    public WebElement waitForTitleElementIOS(String articleName) {
        String locator = getTitleElement(articleName);
        System.out.println(locator);
        return this.waitForElementPresent(locator, "Cannot find article on the page", 15);
    }

    public String getArticleTitle() throws Exception {
        WebElement titleElement = waitForTitleElement();
        return titleElement.getText();
    }

    public String getArticleTitleIOS(String article) throws Exception {
        WebElement titleElement = waitForTitleElementIOS(article);
        System.out.println(titleElement.toString());
        try {
            return getText(titleElement);
        } catch (StaleElementReferenceException e) {
            titleElement = waitForTitleElementIOS(article);
            return getText(titleElement);
        }
    }

    public void swipeToFooter() {

        if (Platform.getInstance().isAndroid()) {
            this.swipeUpToFindElement(FOOTER_ELEMENT,
                    "Cannot find the end of the article", 40);
        } else if (Platform.getInstance().isIOS()) {
            this.swipeUpTillElementAppear(FOOTER_ELEMENT,
                    "Cannot find the end of the article", 40);
        } else {
            this.scrollWebPageTillElementVisible(FOOTER_ELEMENT,
                    "Cannot find the end of the article", 40
            );
        }
    }

    public void addArticleToMyList(String folderName) {
        this.waitForElementAndClick(
                OPTIONS_BUTTON,
                "Cannot find button to open options menu",
                5
        );

        this.waitForElementAndClick(
                OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Cannot find option to add article to reading list",
                5
        );

        this.waitForElementAndClick(
                ADD_TO_MY_LIST_OVERLAY,
                "Cannot find 'Got it' button",
                5
        );

        this.waitForElementAndClear(
                MY_LIST_NAME_INPUT,
                "Cannot find input to set article folder name",
                5
        );

        this.waitForElementAndSendKeys(
                MY_LIST_NAME_INPUT,
                folderName,
                "Cannot enter name of article folder",
                5
        );

        this.waitForElementAndClick(
                MY_LIST_OK_BUTTON,
                "Cannot tap 'OK' button",
                5
        );
    }

    public void closeArticle() {
        if (Platform.getInstance().isAndroid() || Platform.getInstance().isIOS()) {
            this.waitForElementAndClick(
                    CLOSE_ARTICLE_BUTTON,
                    "Cannot find close article button",
                    5
            );
        } else {
            System.out.println("Method closeArticle() does nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }

    public void addArticleToMyExistingList(String folderName) {
        this.waitForElementAndClick(
                OPTIONS_BUTTON,
                "Cannot find button to open options menu",
                5
        );

        this.waitForElementAndClick(
                OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Cannot find option to add article to reading list",
                5
        );

        String xpath = getFolderElement(folderName);
        this.waitForElementAndClick(
                xpath,
                "Cannot find existing folder " + folderName,
                5
        );
    }

    public void removeArticleFromSavedIfAdded() {
        if (this.isElementPresent(OPTIONS_REMOVE_FROM_MY_LIST_BUTTON)) {
            this.waitForElementAndClick(OPTIONS_REMOVE_FROM_MY_LIST_BUTTON,
                    "Cannot click button to remove article from Saved",
                    5);
            this.waitForElementPresent(OPTIONS_REMOVE_FROM_MY_LIST_BUTTON,
                    "Cannot find button to add article to Saved after removing it from this list before",
                    5);
        }
    }

    public void addArticleToMySaved() {
        if (Platform.getInstance().isWeb()) {
            this.removeArticleFromSavedIfAdded();
        }
        this.waitForElementAndClick(OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Cannot find option to add article to my saved list", 5);
    }

    public void addFirstArticleToMySaved() {
        this.waitForElementNotPresent(TAP_TO_GO_HOME_NOTIFICATION,
                "'Tap to go home' notification is still present", 10);
        this.waitForElementAndClick(OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Cannot find option to add article to my saved list", 5);
        this.waitForElementAndClick(CLOSE_POPUP_IOS,
                "Cannot find option to close popup", 5);
    }

    public void assertArticleNameIsCorrect(String articleName) throws Exception {

        String text;
        WebElement element;

        if (Platform.getInstance().isIOS()) {
            this.waitForTitleElementIOS(articleName);
            element = driver.findElement(getLocatorByString(getTitleElement(articleName)));
        } else {
            this.waitForTitleElement();
            element = driver.findElement(getLocatorByString(TITLE));
            System.out.println(getTitleElement(articleName));
        }

        try {
            text = element.getText();
        } catch (StaleElementReferenceException e) {
            if (Platform.getInstance().isIOS()) {
                this.waitForTitleElementIOS(articleName);
                element = driver.findElement(getLocatorByString(getTitleElement(articleName)));
            } else {
                this.waitForTitleElement();
                element = driver.findElement(getLocatorByString(TITLE));
                System.out.println(getTitleElement(articleName));
            }
            text = element.getText();
        }

        if (!text.equals(articleName)) {
            String defaultMessage = "The article title '" + text + "' does not equal " + articleName;
            throw new AssertionError(defaultMessage);
        }
    }
}
