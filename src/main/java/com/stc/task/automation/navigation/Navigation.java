package com.stc.task.automation.navigation;


import com.stc.task.automation.pages.MainPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Navigation {
    KSA("sa-contry-flag", "bh-contry-lable"),
    BH("bh-contry-flag", "sa-contry-lable"),
    KW("kw-contry-flag" , "kw-contry-lable");


    private final By navigationElement;
    private final String checkLabel;

    Navigation(String navigationElement, String checkLabel) {
        this.navigationElement = By.id(navigationElement);
        this.checkLabel = checkLabel;

    }

    public void navigate(WebDriver driver) {
        String label = MainPage.getLabelValue(driver, By.id(this.checkLabel));
        if (label.equalsIgnoreCase(this.checkLabel))
            return;
        driver.findElement(By.id("arrow")).click();
        driver.findElement(this.navigationElement).click();
    }
}