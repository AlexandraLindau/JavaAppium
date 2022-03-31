package lib.ui.mobile_web;

import lib.ui.ArticlePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWArticlePageObject extends ArticlePageObject {

    static {
                TITLE = "xpath://h1[@id='firstHeading']";
                FOOTER_ELEMENT = "xpath://footer";
                OPTIONS_ADD_TO_MY_LIST_BUTTON = "xpath://a[@title='Watch']";
                OPTIONS_REMOVE_FROM_MY_LIST_BUTTON = "xpath://a[@title='Unwatch']";

                ADD_TO_MY_EXISTING_LIST_OVERLAY = "xpath://*[@text='{FOLDER_NAME}']";
                ADD_TO_MY_LIST_OVERLAY = "id:org.wikipedia:id/onboarding_button";
                MY_LIST_NAME_INPUT = "id:org.wikipedia:id/text_input";
                MY_LIST_OK_BUTTON = "id:android:id/button1";
    }
   public MWArticlePageObject(RemoteWebDriver driver) {
        super(driver);
   }
}
