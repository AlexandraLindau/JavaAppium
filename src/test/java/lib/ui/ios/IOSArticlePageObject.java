package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.ArticlePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class IOSArticlePageObject extends ArticlePageObject {

    static {
                TITLE_TPL = "xpath:(//XCUIElementTypeStaticText[@name='{SUBSTRING}'])[1]";
                FOOTER_ELEMENT = "id:View article in browser";
                OPTIONS_ADD_TO_MY_LIST_BUTTON = "xpath://XCUIElementTypeButton[@name='Save for later']";
                CLOSE_ARTICLE_BUTTON = "id:Back";
                CLOSE_POPUP_IOS = "xpath://XCUIElementTypeButton[@name='places auth close']";
                TAP_TO_GO_HOME_NOTIFICATION = "xpath://XCUIElementTypeStaticText[@name='Tap to go home']";
    }
   public IOSArticlePageObject(RemoteWebDriver driver) {
        super(driver);
   }
}
