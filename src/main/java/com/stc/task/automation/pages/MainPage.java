package com.stc.task.automation.pages;


import com.paulhammant.ngwebdriver.NgWebDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.temporal.ChronoUnit;


public class MainPage {

    // Initialize web drivers
    public static WebDriver driver;
    public NgWebDriver ngDriver;
    public static JavascriptExecutor jsDriver;

    public MainPage(WebDriver driver, NgWebDriver ngDriver) {
        this.driver = driver;
        this.ngDriver = ngDriver;
        this.jsDriver = (JavascriptExecutor) driver;

        //Set a delay of 30 secs to wait for elements' visibility
        AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(driver, 10);
        PageFactory.initElements(factory, this);
        ngDriver.waitForAngularRequestsToFinish();
    }




    // wait for the loading image to disappear before berforing any acion in the page

    public void scrollToTop() {
        jsDriver.executeScript("window.scrollTo(0, -document.body.scrollHeight)");
    }

    public static void scrollToElement(WebElement element) {
        jsDriver.executeScript("arguments[0].scrollIntoView();", element);
    }


    public static void jsClick(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.of(10, ChronoUnit.SECONDS));
        scrollToElement(element);
        jsDriver.executeScript("return document.readyState").toString().equals("complete");
        wait.until(ExpectedConditions.elementToBeClickable(element));
        jsDriver.executeScript("arguments[0].click();", element);
        jsDriver.executeScript("return document.readyState").toString().equals("complete");
    }

    public static String getLabelValue(WebDriver driver, By by){
        WebElement element = driver.findElement(by);
        return element.getText();

    }

}
