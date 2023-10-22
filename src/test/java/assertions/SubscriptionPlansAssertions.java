package assertions;

import com.stc.task.automation.constants.GeneralConstants;
import com.stc.task.automation.dataModels.PlanDm;
import com.stc.task.automation.dataModels.PlansDm;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;

public class SubscriptionPlansAssertions {

    SoftAssert softAssert = new SoftAssert();

    public void assertPlansData(ArrayList<PlanDm> actual, PlansDm expected) {
        for (int i = 0; i < actual.size(); i++) {
            softAssert.assertTrue(actual.get(i).getCurrency().toLowerCase().contains(expected.getPlanDm().get(i).getCurrency().trim().toLowerCase()),
                    expected.getPlanDm().get(i).getTestCaseTitle() + " Has " + GeneralConstants.FAILED);
            softAssert.assertTrue(actual.get(i).getType().toLowerCase().contains(expected.getPlanDm().get(i).getType().trim().toLowerCase()),
                    expected.getPlanDm().get(i).getTestCaseTitle() + " Has " + GeneralConstants.FAILED);
            softAssert.assertTrue(actual.get(i).getPrice().toLowerCase().contains(expected.getPlanDm().get(i).getPrice().trim().toLowerCase()),
                    expected.getPlanDm().get(i).getTestCaseTitle() + " Has " + GeneralConstants.FAILED);
        }
        softAssert.assertAll();
    }
}
