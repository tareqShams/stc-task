package com.stc.task.automation.controls;

import com.paulhammant.ngwebdriver.NgWebDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;


public class MainControl {
    public WebDriver driver;
    public NgWebDriver ngDriver;
    public JavascriptExecutor jsDriver;
    public MainControl(WebDriver driver, NgWebDriver ngDriver) {
        this.driver = driver;
        this.ngDriver = ngDriver;
        this.jsDriver = (JavascriptExecutor) driver;
    }
}
