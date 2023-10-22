package assertions;

import com.stc.task.automation.constants.GeneralConstants;
import com.stc.task.automation.dataModels.PlanDm;
import com.stc.task.automation.dataModels.PlansDm;
import com.stc.task.automation.utils.Log;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;

public class SubscriptionPlansAssertions {

    SoftAssert softAssert = new SoftAssert();

    public void assertPlansData(ArrayList<PlanDm> actual, PlansDm expected) {
        for (int i = 0; i < actual.size(); i++) {
            Log.info(String.format("Validating actual value %s with expected value %s",actual.get(i).getCurrency(),expected.getPlanDm().get(i).getCurrency()));
            softAssert.assertTrue(actual.get(i).getCurrency().toLowerCase().contains(expected.getPlanDm().get(i).getCurrency().trim().toLowerCase()),
                    expected.getPlanDm().get(i).getTestCaseTitle() + " Has " + GeneralConstants.FAILED);
            Log.info(String.format("Validating actual value %s with expected value %s",actual.get(i).getType(),expected.getPlanDm().get(i).getType()));
            softAssert.assertTrue(actual.get(i).getType().toLowerCase().contains(expected.getPlanDm().get(i).getType().trim().toLowerCase()),
                    expected.getPlanDm().get(i).getTestCaseTitle() + " Has " + GeneralConstants.FAILED);
            Log.info(String.format("Validating actual value %s with expected value %s",actual.get(i).getPrice(),expected.getPlanDm().get(i).getPrice()));
            softAssert.assertTrue(actual.get(i).getPrice().toLowerCase().contains(expected.getPlanDm().get(i).getPrice().trim().toLowerCase()),
                    expected.getPlanDm().get(i).getTestCaseTitle() + " Has " + GeneralConstants.FAILED);
        }
        softAssert.assertAll();
    }
}
