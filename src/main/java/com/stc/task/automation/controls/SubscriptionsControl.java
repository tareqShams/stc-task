package com.stc.task.automation.controls;

import com.stc.task.automation.dataModels.PlanDm;
import com.stc.task.automation.navigation.Navigation;
import com.stc.task.automation.pages.SubscriptionPage;
import com.paulhammant.ngwebdriver.NgWebDriver;
import com.stc.task.automation.utils.Log;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;

public class SubscriptionsControl extends MainControl {

    public SubscriptionsControl(WebDriver driver, NgWebDriver ngWebDriver) {
        super(driver, ngWebDriver);
    }

    public void selectCountry(String country) {
        Log.info(String.format("Selecting Country %s",country));
        if (country.equalsIgnoreCase("KSA")) {
            Navigation.KSA.navigate(driver);
        } else if (country.equalsIgnoreCase("KW")) {
            Navigation.KW.navigate(driver);
        } else if (country.equalsIgnoreCase("BH")) {
            Navigation.BH.navigate(driver);
        }
    }

    public ArrayList<PlanDm> getPlansData() {
        SubscriptionPage subscriptionPage = new SubscriptionPage(driver, ngDriver);
        return subscriptionPage.getPlansData();
    }
}
