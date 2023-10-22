package com.stc.task.automation.pages;

import com.stc.task.automation.dataModels.PlanDm;
import com.stc.task.automation.utils.Log;
import com.paulhammant.ngwebdriver.NgWebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;


public class SubscriptionPage extends MainPage {

    //invoke parent's constructor
    public SubscriptionPage(WebDriver driver, NgWebDriver ngWebDriver) {
        super(driver, ngWebDriver);
    }

    public ArrayList<PlanDm> getPlansData() {
        List<WebElement> titles = driver.findElements(By.className("plan-title"));
        List<WebElement> prices = driver.findElements(By.className("price"));
        List<WebElement> currencies = driver.findElements(By.xpath("//div[@class='price']/i"));

        ArrayList<PlanDm> plansDm =new ArrayList<>();
        PlanDm planDm = new PlanDm();
        try {
            for (int i = 0 ; i<titles.size();i++) {
                planDm.setType(titles.get(i).getText());
                planDm.setPrice(prices.get(i).getText());
                planDm.setCurrency(currencies.get(i).getText());
                plansDm.add(planDm);
            }

        } catch (Exception e) {
            Log.error("Error occured in " + new Object() {
            }
                    .getClass().getName() + "." + new Object() {
            }
                    .getClass()
                    .getEnclosingMethod()
                    .getName(), e);
            return null;
        }
        return plansDm;
    }


}
