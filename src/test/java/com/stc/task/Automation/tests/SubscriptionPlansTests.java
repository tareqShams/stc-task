package com.stc.task.Automation.tests;


import assertions.SubscriptionPlansAssertions;
import com.stc.task.automation.controls.SubscriptionsControl;
import com.stc.task.automation.dataModels.PlanDm;
import com.stc.task.automation.dataModels.PlansDm;
import com.stc.task.automation.utils.Log;
import com.paypal.selion.platform.dataprovider.DataProviderFactory;
import com.paypal.selion.platform.dataprovider.DataResource;
import com.paypal.selion.platform.dataprovider.SeLionDataProvider;
import com.paypal.selion.platform.dataprovider.impl.InputStreamResource;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;


public class SubscriptionPlansTests extends BaseTest {

    @Test(description = "Validate Add user feature", dataProvider = "plansDataProvider")
    public void validatingPlansData(PlansDm plansDm) {
        //Create extent test to be logged in report using test case title
        test = extent.createTest(plansDm.getTestCaseTitle());
        Log.test = test;
        Log.startTestCase(plansDm.getTestCaseTitle());
        SubscriptionsControl subscriptionsControl = new SubscriptionsControl(driver,ngDriver);
        subscriptionsControl.selectCountry(plansDm.getCountry());
        ArrayList<PlanDm> actual= subscriptionsControl.getPlansData();

        SubscriptionPlansAssertions subscriptionPlansAssertions =new SubscriptionPlansAssertions();
        subscriptionPlansAssertions.assertPlansData(actual,plansDm);
    }

    @DataProvider(name = "plansDataProvider")
    public Object[][] getJsonDataProvider() throws IOException {
        DataResource resource =
                new InputStreamResource(Files.newInputStream(Paths.get(testdataConfigsProps.getProperty("plans"))),
                        PlansDm.class, "json");
        SeLionDataProvider dataProvider =
                DataProviderFactory.getDataProvider(resource);
        return dataProvider.getAllData();
    }

}
