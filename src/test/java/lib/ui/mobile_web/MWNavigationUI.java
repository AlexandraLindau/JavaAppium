package lib.ui.mobile_web;

import lib.ui.NavigationUI;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWNavigationUI extends NavigationUI {

    static {
        MY_LIST_LINK = "xpath://a[@class='menu__item--watchlist']";
        OPEN_NAVIGATION = "xpath://label[@id='mw-mf-main-menu-button']";
    }
   public MWNavigationUI(RemoteWebDriver driver) {
        super(driver);
   }
}
