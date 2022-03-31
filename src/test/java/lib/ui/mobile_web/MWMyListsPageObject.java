package lib.ui.mobile_web;

import lib.ui.MyListsPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWMyListsPageObject extends MyListsPageObject {

    static {
                ARTICLE_BY_TITLE_TPL = "xpath://h3[text()='{TITLE}']";
                REMOVE_FROM_SAVED_BUTTON = "xpath://h3[text()='{TITLE}']/../../a[@aria-controls='mw-watchlink-notification']";
    }

   public MWMyListsPageObject(RemoteWebDriver driver) {
        super(driver);
   }
}
