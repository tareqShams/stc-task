package com.stc.task.automation.controls;

import com.stc.task.automation.constants.GeneralConstants;
import com.stc.task.automation.dataModels.PlanDm;
import com.stc.task.automation.pages.SubscriptionPage;
import com.paulhammant.ngwebdriver.NgWebDriver;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;

public class SubscriptionsControl extends MainControl {

    public SubscriptionsControl(WebDriver driver, NgWebDriver ngWebDriver) {
        super(driver, ngWebDriver);
    }

    public String selectCountry(String country) {
        if (country.equalsIgnoreCase("KSA")) {
            driver.get("https://subscribe.stctv.com/sa-ar");
        } else if (country.equalsIgnoreCase("KW")) {
            driver.get("https://subscribe.stctv.com/kw-ar");
        } else if (country.equalsIgnoreCase("BH")) {
            driver.get("https://subscribe.stctv.com/bh-ar");
        }
        return GeneralConstants.SUCCESS;
    }

    public ArrayList<PlanDm> getPlansData() {
        SubscriptionPage subscriptionPage = new SubscriptionPage(driver, ngDriver);
        return subscriptionPage.getPlansData();
    }
}
