package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import io.qameta.allure.Attachment;
import lib.Platform;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;

public class MainPageObject {

    protected RemoteWebDriver driver;

    public MainPageObject(RemoteWebDriver driver) {
        this.driver = driver;
    }

    public WebElement waitForElementPresent(String locator, String errorMessage, long timeoutInSeconds) {

        By by = this.getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public WebElement waitForElementPresent(String locator, String errorMessage) {

        return waitForElementPresent(locator, errorMessage, 5);
    }

    public WebElement waitForElementAndClick(String locator, String errorMessage, long timeoutInSeconds) {

        WebElement element = waitForElementPresent(locator, errorMessage, timeoutInSeconds);
        try {
            element.click();
        } catch (ElementNotInteractableException e) {
            System.out.println("ElementNotInteractableException: " + locator);
        }
        System.out.println("Clicked: " + locator);
        return element;
    }

    public WebElement waitForElementAndSendKeys(String locator, String value, String errorMessage, long timeoutInSeconds) {

        WebElement element = waitForElementPresent(locator, errorMessage, timeoutInSeconds);
        element.clear();
        element.sendKeys(value);
        return element;
    }

    public boolean waitForElementNotPresent(String locator, String errorMessage, long timeoutInSeconds) {
        By by = this.getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    public WebElement waitForElementAndClear(String locator, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator, errorMessage, timeoutInSeconds);
        element.clear();
        return element;
    }

    public void tryClickElementWithFewAttempts(String locator, String errorMessage, int attempts) {
        int current_attempts = 0;
        boolean need_more_attempts = true;

        while (need_more_attempts) {
            try {
                this.waitForElementAndClick(locator, errorMessage, 5);
                need_more_attempts = false;
            } catch (Exception e) {
                if (current_attempts > attempts) {
                    this.waitForElementAndClick(locator, errorMessage, 5);
                }
            }
            ++current_attempts;
        }
    }

    public void assertElementHasText(String locator, String expectedText, String errorMessage) {
        By by = this.getLocatorByString(locator);
        waitForElementPresent(locator, "Cannot find the element");
        WebElement element = driver.findElement(by);
        if (!element.getText().equals(expectedText)) {
            String defaultMessage = "The element " + locator + " is supposed to have text " + expectedText + " but it has: "
                    + element.getText() + "\n";
            throw new AssertionError(defaultMessage + " " + errorMessage);
        }
    }

    public void assertWebElementHasPlaceholder(String locator, String expectedText, String errorMessage) {
        By by = this.getLocatorByString(locator);
        waitForElementPresent(locator, "Cannot find the element");
        WebElement element = driver.findElement(by);
        if (!element.getAttribute("placeholder").equals(expectedText)) {
            String defaultMessage = "The web element " + locator + " is supposed to have placeholder " + expectedText + " but it has: "
                    + element.getText() + "\n";
            throw new AssertionError(defaultMessage + " " + errorMessage);
        }
    }

    public void assertElementContainsText(String locator, String expectedText, String errorMessage) {
        By by = this.getLocatorByString(locator);
        waitForElementPresent(locator, "Cannot find the element " + locator);
        WebElement element = driver.findElement(by);
        System.out.println(locator);
        System.out.println(by.toString());
        if (!element.getText().contains(expectedText)) {
            String defaultMessage = "The element " + locator + " is supposed to contain text " + expectedText;
            throw new AssertionError(defaultMessage + " " + errorMessage);
        }
    }

    public void swipeUp(int durationOfSwipeInSeconds) {
        if (driver instanceof AppiumDriver) {
            TouchAction action = new TouchAction((AppiumDriver) driver);
            Dimension size = driver.manage().window().getSize();
            int x = size.width / 2;
            int startY = (int) (size.height * 0.8);
            int endY = (int) (size.height * 0.2);

            action.press(PointOption.point(x, startY)).waitAction(WaitOptions.waitOptions(Duration
                    .ofSeconds(durationOfSwipeInSeconds))).moveTo(PointOption.point(x, endY)).release().perform();
        } else {
            System.out.println("Method swipeUp() does nothing on platform "
                    + lib.Platform.getInstance().getPlatformVar());
        }

    }

    public void swipeUpQuick() {
        swipeUp(2);
    }

    public void swipeUpToFindElement(String locator, String errorMessage, int maxSwipes) {

        By by = this.getLocatorByString(locator);
        int alreadySwiped = 0;
        while (driver.findElements(by).size() == 0) {

            if (alreadySwiped > maxSwipes) {
                waitForElementPresent(locator, "Cannot find element by swiping up \n" + errorMessage, 0);
                return;
            }
            swipeUpQuick();
            alreadySwiped++;
        }
    }

    public boolean isElementLocatedOnScreen(String locator) {
        int element_location_by_y = this.waitForElementPresent(locator,
                "Cannot find element by locator: " + locator, 5).getLocation().getY();

        if (Platform.getInstance().isWeb()) {
            JavascriptExecutor JSExecutor = (JavascriptExecutor) driver;
            Object js_result = JSExecutor.executeScript("return window.pageYOffset");
            element_location_by_y = Integer.parseInt(js_result.toString());
        }
        int screen_size_by_y = driver.manage().window().getSize().getHeight();
        return element_location_by_y < screen_size_by_y;

    }

    public void swipeUpTillElementAppear(String locator, String errorMessage, int maxSwipes) {
        int alreadySwiped = 0;
        while (!this.isElementLocatedOnScreen(locator)) {

            if (alreadySwiped > maxSwipes) {
                Assert.assertTrue(errorMessage, this.isElementLocatedOnScreen(locator));
                return;
            }
            swipeUpQuick();
            alreadySwiped++;
        }
    }

    public void swipeElementToLeft(String locator, String errorMessage) {
        WebElement element = waitForElementPresent(locator, errorMessage, 10);

        int leftX = element.getLocation().getX();
        int rightX = leftX + element.getSize().getWidth();

        int upperY = element.getLocation().getY();
        int lowerY = upperY + element.getSize().getHeight();

        int middleY = (upperY + lowerY) / 2;

        if (driver instanceof AppiumDriver) {
            TouchAction action = new TouchAction((AppiumDriver) driver);

            action
                    .press(PointOption.point(rightX, middleY))
                    .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
                    .moveTo(PointOption.point(leftX, middleY))
                    .release()
                    .perform();
        } else {
            System.out.println("Method swipeElementToLeft() does nothing on platform "
                    + lib.Platform.getInstance().getPlatformVar());
        }

    }

    public int getAmountOfElements(String locator) {
        By by = this.getLocatorByString(locator);
        List elements = driver.findElements(by);
        return elements.size();
    }

    public boolean isElementPresent(String locator) {
        return getAmountOfElements(locator) > 0;
    }

    public String waitForElementAndGetAttribute(String locator, String attribute, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator, errorMessage, timeoutInSeconds);
        return element.getAttribute(attribute);
    }

//    public boolean isElementPresent(String locator) {
//        By by = this.getLocatorByString(locator);
//        try {
//            driver.findElement(by);
//            return true;
//        } catch (NoSuchElementException e) {
//            return false;
//        }
//    }

    public void assertElementNotPresent(String locator, String errorMessage) {
        int amountOfElements = getAmountOfElements(locator);
        if (amountOfElements > 0) {
            String defaultMessage = "An element " + locator + " supposed not to be present";
            throw new AssertionError(defaultMessage + " " + errorMessage);
        }
    }

    public void assertElementPresent(String locator, String errorMessage) {
        int amountOfElements = getAmountOfElements(locator);
        if (amountOfElements == 0) {
            String defaultMessage = "An element " + locator + " supposed to be present";
            throw new AssertionError(defaultMessage + " " + errorMessage);
        }
    }

    protected By getLocatorByString(String locator_with_type) {
        String[] exploaded_locator = locator_with_type.split(Pattern.quote(":"), 2);
        String by_type = exploaded_locator[0];
        String locator = exploaded_locator[1];

        if (by_type.equals("xpath")) {
            return By.xpath(locator);
        } else if (by_type.equals("id")) {
            return By.id(locator);
        } else if (by_type.equals("css")) {
            return By.cssSelector(locator);
        } else {
            throw new IllegalArgumentException("Cannot get type of locator. Locator: " + locator_with_type);
        }
    }

    String getText(String locator) throws Exception {
        WebElement element = driver.findElement(getLocatorByString(locator));
        waitForElementPresent(locator, "Cannot find element with locator: " + locator, 10);
        boolean isIOS = Platform.getInstance().isIOS();
        boolean isAndroid = Platform.getInstance().isAndroid();

        if (!isAndroid && !isIOS) {
            throw new Exception("Invalid platform");
        }

        String textAttrName = isAndroid ? "text" : "value";
        String text;
        try {
            text = element.getAttribute(textAttrName);
        } catch (StaleElementReferenceException e) {
            element = driver.findElement(getLocatorByString(locator));
            text = element.getAttribute(textAttrName);
        }
        return text;
    }


    String getText(WebElement element) throws Exception {
        String text;
        if (Platform.getInstance().isAndroid()) {
            text = element.getAttribute("text");
        } else if (Platform.getInstance().isIOS()) {
            text = element.getAttribute("value");
        } else {
            throw new Exception("Invalid platform");
        }
        return text;
    }

    public void scrollWebPageUp() {
        if (Platform.getInstance().isWeb()) {
            JavascriptExecutor JSExecutor = (JavascriptExecutor) driver;
            JSExecutor.executeScript("window.scrollBy(0,250");
        } else {
            System.out.println("Method scrollWebPageUp() does nothing on platform "
                    + lib.Platform.getInstance().getPlatformVar());

        }
    }

    public void scrollWebPageTillElementVisible(String locator, String errorMessage, int maxSwipes) {
        int already_swiped = 0;

        WebElement element = this.waitForElementPresent(locator, errorMessage);

        while (!this.isElementLocatedOnScreen(locator)){
            scrollWebPageUp();
            ++already_swiped;
            if (already_swiped > maxSwipes) {
                Assert.assertTrue(errorMessage, element.isDisplayed());
            }
        }
    }

    public String takeScreenshot (String name) {
        TakesScreenshot ts = (TakesScreenshot)this.driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String path = System.getProperty("user.dir") + "/" + name + "_screenshot.png";
        try {
            FileUtils.copyFile(source, new File(path));
            System.out.println("The screensjot was taken: " + path);
        } catch (Exception e) {
            System.out.println("Cannot take screenshot. Error: " + e.getMessage());
        } return path;
    }

    @Attachment
    public static byte[] screenshot(String path) {
        byte[] bytes = new byte[0];
        try {
            bytes = Files.readAllBytes(Paths.get(path));
        } catch (IOException e) {
            System.out.println("Cannot get bytes from screenshot. Error: " + e.getMessage());
        } return bytes;
    }
}
