package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.ui.WelcomePageObject;
import org.junit.Test;

public class GetStartedTest extends CoreTestCase {

    @Test
    @Features(value = {@Feature(value = "Intro")})
    @DisplayName("Skip 'welcome' intro shown by first launch of iOS app")
    @Description("We skip 'welcome' intro shown by first launch of iOS app")
    @Step("Starting testPassThroughWelcome")
    @Severity(value = SeverityLevel.MINOR)
    public void testPassThroughWelcome() {

        if (Platform.getInstance().isAndroid() || lib.Platform.getInstance().isWeb()) {
            return;
        }

        WelcomePageObject welcomePageObject = new WelcomePageObject(driver);

        welcomePageObject.waitForLearnMoreLink();
        welcomePageObject.clickNextButton();

        welcomePageObject.waitForNewWaysToExplore();
        welcomePageObject.clickNextButton();

        welcomePageObject.waitForSearchInNearly300Languages();
        welcomePageObject.clickNextButton();

        welcomePageObject.waitForLearnMoreAboutDataCollected();
        welcomePageObject.clickGetStartedButton();

    }

    @Override
    protected void skipWelcomePageForIOSApp() {
        assert true;
    }
}
