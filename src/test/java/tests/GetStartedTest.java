package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.WelcomePageObject;
import org.junit.Test;

public class GetStartedTest extends CoreTestCase {

    @Test
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
