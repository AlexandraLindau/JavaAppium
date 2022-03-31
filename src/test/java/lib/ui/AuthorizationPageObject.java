package lib.ui;

import com.sun.org.apache.regexp.internal.RE;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AuthorizationPageObject extends MainPageObject {

    private static final String
    LOGIN_BUTTON = "xpath://a[text()='Log in']",
    LOGIN_INPUT = "css:input[name=wpName]",
    PASSWORD_INPUT = "css:input[name=wpPassword]",
    SUBMIT_BUTTON = "css:button[name=wploginattempt]",
    RETURN_BUTTON = "xpath://a[text()='Return to the previous page.']";

    public AuthorizationPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    public void clickAuthButton() throws InterruptedException {
        Thread.sleep(1000);
        this.waitForElementPresent(LOGIN_BUTTON, "Cannot find auth button", 5);
        this.waitForElementAndClick(LOGIN_BUTTON, "Cannot find and click auth button", 5);
    }

    public void enterLoginData(String login, String password) {
        this.waitForElementAndSendKeys(LOGIN_INPUT, login, "Cannot find and enter login", 5);
        this.waitForElementAndSendKeys(PASSWORD_INPUT, password, "Cannot find and password login", 5);
    }

    public void submitForm() {
        this.waitForElementAndClick(SUBMIT_BUTTON, "Cannot find and click submit button", 5);
    }

    public void returnAfterAuthError() {
        this.waitForElementPresent(RETURN_BUTTON, "Cannot find 'Return' link", 5);
        this.waitForElementAndClick(RETURN_BUTTON, "Cannot click 'Return' link", 5);
    }
}
