package org.example;


import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Point;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;


public class startEmulator {
    public static void main(String[] args) throws Exception {
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability("platformName", "Android");
        cap.setCapability("platformVersion", "13");
        cap.setCapability("automationName", "UiAutomator2");
        cap.setCapability("deviceName", "Pixel_3a");
        cap.setCapability("appPackage", "com.etsy.android");
        cap.setCapability("appActivity", "com.etsy.android.ui.BOEActivity");
        cap.setCapability("noReset", "true");

        AppiumDriver driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), cap);
         WebElement searchBox = waitForElementToBeClickable(driver, By.xpath("//android.widget.AutoCompleteTextView[@resource-id=\"com.etsy.android:id/search_src_text\"]"), 10);
        fullScreenshot(driver,searchBox);
        searchBox.click();
        WebElement searchBoxValue = waitForElementToBeClickable(driver, By.xpath("//android.widget.AutoCompleteTextView[@resource-id=\"com.etsy.android:id/search_src_text\"]"), 10);
        searchBoxValue.sendKeys("artwork");
        fullScreenshot(driver,searchBoxValue);
        WebElement artForWall =waitForElementToBeClickable(driver, By.xpath("//android.widget.TextView[@resource-id=\"com.etsy.android:id/query_text\" and @text=\"artwork for walls\"]"), 10);
        fullScreenshot(driver,artForWall);
        artForWall.click();
        WebElement artWork = waitForElementToBeClickable(driver, By.xpath("//android.widget.TextView[@content-desc=\"Edgar Allan Poe dark academia original design gift poster archival quality matte unique print gothic night garden with moon and lunar moth Button\"]"), 10);
        fullScreenshot(driver,artWork);
        artWork.click();
        WebElement portraitSize = waitForElementToBeClickable(driver, By.id("com.etsy.android:id/clg_text_input"), 10);
        fullScreenshot(driver,portraitSize);
        portraitSize.click();
        WebElement selectSize= waitForElementToBeClickable(driver, By.xpath("(//android.widget.TextView[@resource-id=\"com.etsy.android:id/clg_line_item_text\"])[1]"), 10);
        fullScreenshot(driver,selectSize);
        selectSize.click();
        WebElement scrollTillAddToCart = driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().text(\"Add to cart\"))"));
        fullScreenshot(driver,scrollTillAddToCart);
        scrollTillAddToCart.click();
        WebElement cartButton = waitForElementToBeClickable(driver, By.xpath("(//android.widget.ImageView[@resource-id=\"com.etsy.android:id/navigation_bar_item_icon_view\"])[5]"), 10);
        fullScreenshot(driver,cartButton);
        cartButton.click();

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


