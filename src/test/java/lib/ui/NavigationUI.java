package lib.ui;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class NavigationUI extends MainPageObject {

    protected static String
            MY_LIST_LINK,
            OPEN_NAVIGATION;

    public NavigationUI(RemoteWebDriver driver) {
        super(driver);
    }

    public void clickMyLists() {
        if (Platform.getInstance().isWeb()) {
            this.tryClickElementWithFewAttempts(MY_LIST_LINK,
                    "Cannot navigation button to 'My list'",
                    30);
        } else {
            this.waitForElementAndClick(
                    MY_LIST_LINK,
                    "Cannot navigation button to 'My list'",
                    5
            );
        }
    }

    public void openNavigation() {
        if (Platform.getInstance().isWeb()) {
            this.waitForElementAndClick(OPEN_NAVIGATION,
                    "Cannot find and click 'Open navigation' button",
                    5);
        } else {
            System.out.println("Method openNavigation() does nothin on platform " + Platform.getInstance().getPlatformVar());
        }
    }
}
