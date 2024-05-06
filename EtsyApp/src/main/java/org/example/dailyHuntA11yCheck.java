package org.example;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Point;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public class dailyHuntA11yCheck {
    public static void main(String[] args) throws Exception {
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability("platformName", "Android");
        cap.setCapability("platformVersion", "13");
        cap.setCapability("automationName", "UiAutomator2");
        cap.setCapability("deviceName", "Pixel_3a");
        cap.setCapability("appPackage", "com.eterno");
        cap.setCapability("appActivity", "com.newshunt.app.view.activity.Splash");
        cap.setCapability("noReset", "true");
        cap.setCapability("udid", "emulator-5554");

        AppiumDriver driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), cap);

        List cmdArgs = Arrays.asList("put", "secure", "enabled_accessibility_services", "com.example.android.demoservice/com.example.android.globalactionbarservice.GlobalActionBarService");
        Map enableServiceCmd = ImmutableMap
                .of("command", "settings", "args", cmdArgs);
        driver.executeScript("mobile: shell", enableServiceCmd);
        Thread.sleep(2000);
        WebElement searchBoxIcon = waitForElementToBeClickable(driver, By.xpath("(//android.widget.ImageView[@resource-id=\"com.eterno:id/icon_dummy\"])[2]"), 20);
        fullScreenshot(driver,searchBoxIcon);
        searchBoxIcon.click();
        WebElement searchBox = waitForElementToBeClickable(driver, By.id("com.eterno:id/global_search"), 10);
        searchBox.click();
        WebElement searchBoxValue = waitForElementToBeClickable(driver, By.id("com.eterno:id/search_box"), 10);
        searchBoxValue.sendKeys("Business Times");
        fullScreenshot(driver,searchBoxValue);
        WebElement businessTimes =waitForElementToBeClickable(driver, By.xpath("//android.widget.TextView[@resource-id=\"com.eterno:id/handle_display_name\" and @text=\"Business Times\"]"), 10);
        fullScreenshot(driver,businessTimes);
        businessTimes.click();
        WebElement mostRecent = waitForElementToBeClickable(driver, By.id("com.eterno:id/interaction_filter1"), 10);
        fullScreenshot(driver,mostRecent);
        mostRecent.click();
        WebElement mostCommented = waitForElementToBeClickable(driver, By.xpath("//android.widget.TextView[@resource-id=\"com.eterno:id/options_textView\" and @text=\"Most Commented\"]"), 10);
        fullScreenshot(driver,mostCommented);
        mostCommented.click();
        WebElement goBack= waitForElementToBeClickable(driver, By.id("com.eterno:id/actionbar_back_button_ner"), 10);
        fullScreenshot(driver,goBack);
        goBack.click();
        Thread.sleep(50);
        WebElement scrollTillTrendingPodcasts = driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().text(\"Trending Podcast\"))"));
        fullScreenshot(driver,scrollTillTrendingPodcasts);
        WebElement  mintMoney= waitForElementToBeClickable(driver, By.xpath("//android.widget.Image[@text=\"Why Not Mint Money\"]"), 10);
        fullScreenshot(driver,mintMoney);
        mintMoney.click();
        copyResultsFile(driver);
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
//    public static void copyResultsFile(AppiumDriver driver)
//    {
//        byte[] data = driver.pullFile("/sdcard/Documents/ResultsJson.txt");
//        try {
//            FileWriter myWriter = new FileWriter("filename.txt");
//            myWriter.write(new String(data));
//            myWriter.close();
//            System.out.println("Successfully wrote to the file.");
//        } catch (IOException e) {
//            System.out.println("An error occurred.");
//            e.printStackTrace();
//        }
//    }

    public static void copyResultsFile(AppiumDriver driver) throws IOException, InterruptedException {
        String deviceUDID = driver.getCapabilities().getCapability("udid").toString();

        // Define source and destination paths
        String remoteFilePath = "/sdcard/Documents/ResultsJson.txt";
        String localFilePath = "filename.txt";

        // Build the adb command
        String adbPullCommand = "adb -s " + deviceUDID + " pull " + remoteFilePath + " " + localFilePath;

        // Create a new ProcessBuilder instance
        ProcessBuilder processBuilder = new ProcessBuilder("adb", "-s", deviceUDID, "pull", remoteFilePath, localFilePath);

        // Start the process
        Process process = processBuilder.start();

        // Wait for the process to finish
        process.waitFor();

        // Check the process exit value for any errors (optional)
        int exitValue = process.exitValue();
        if (exitValue != 0) {
            // Handle error if process failed to execute
            throw new IOException("Failed to pull file using adb command");
        }
    }




}
