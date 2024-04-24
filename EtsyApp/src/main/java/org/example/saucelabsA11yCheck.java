package org.example;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.Point;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public class saucelabsA11yCheck {
    public static void main(String[] args) throws Exception {
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability("platformName", "Android");
        cap.setCapability("platformVersion", "13");
        cap.setCapability("automationName", "UiAutomator2");
        cap.setCapability("deviceName", "Pixel_3a");
        cap.setCapability("appPackage", "com.saucelabs.mydemoapp.rn");
        cap.setCapability("appActivity", "com.saucelabs.mydemoapp.rn.MainActivity");
//        cap.setCapability("app","F:\\Appium_project\\A11Y-Android-IOS-\\EtsyApp\\src\\main\\com.etsy.android_6.69.0-66900046_minAPI28(arm64-v8a,armeabi-v7a,x86,x86_64)(nodpi)_apkmirror.com.apk");
        cap.setCapability("noReset", "true");

        AppiumDriver driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), cap);
        Thread.sleep(2000);
        WebElement selectItem = waitForElementToBeClickable(driver, By.xpath("(//android.view.ViewGroup[@content-desc=\"store item\"])[1]/android.view.ViewGroup[1]/android.widget.ImageView"),10);
        fullScreenshot(driver,selectItem);
        selectItem.click();
        WebElement selectQuantity = waitForElementToBeClickable(driver,By.xpath("//android.view.ViewGroup[@content-desc=\"counter plus button\"]/android.widget.ImageView"),10);
        fullScreenshot(driver,selectQuantity);
        selectQuantity.click();
        WebElement addToCart = waitForElementToBeClickable(driver,By.xpath("//android.widget.TextView[@text=\"Add To Cart\"]"),10);
        fullScreenshot(driver,addToCart);
        addToCart.click();
        WebElement cart = waitForElementToBeClickable(driver,By.xpath("//android.view.ViewGroup[@content-desc=\"cart badge\"]/android.widget.ImageView"),10);
        fullScreenshot(driver, cart);
        cart.click();
        WebElement proceedToCheckout = waitForElementToBeClickable(driver,By.xpath("//android.widget.TextView[@text=\"Proceed To Checkout\"]"),10);
        fullScreenshot(driver, proceedToCheckout);
        proceedToCheckout.click();
        WebElement selectUsername = waitForElementToBeClickable(driver, By.xpath("//android.widget.TextView[@text=\"bob@example.com\"]"),10);
        fullScreenshot(driver, selectUsername);
        selectUsername.click();
        WebElement login = waitForElementToBeClickable(driver, AppiumBy.accessibilityId("Login button"),10);
        fullScreenshot(driver, login);
        login.click();
    }

    public static WebElement waitForElementToBeClickable(AppiumDriver driver, By locator, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static String fullScreenshot(AppiumDriver driver, WebElement element) throws IOException {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        BufferedImage fullImg = ImageIO.read(screenshot);
        Point elementLocation = element.getLocation();
        int elementWidth = element.getSize().getWidth();
        int elementHeight = element.getSize().getHeight();
        Graphics2D g2d = fullImg.createGraphics();
        g2d.setColor(Color.RED);
        g2d.setStroke(new BasicStroke(5)); // Adjust stroke thickness as needed
        g2d.drawRect(elementLocation.getX(), elementLocation.getY(), elementWidth, elementHeight);
        g2d.dispose();
        ImageIO.write(fullImg, "png", screenshot);
        String filename = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".png";
        File screenshotLocation = new File(System.getProperty("user.dir") + filename);
        System.out.println( System.getProperty("user.dir"));
        FileUtils.copyFile(screenshot, screenshotLocation);

        return filename;
    }

}
