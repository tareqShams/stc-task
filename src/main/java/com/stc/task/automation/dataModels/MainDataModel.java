package com.stc.task.automation.dataModels;

public class MainDataModel {


    private String expectedMessage;
    private String testCaseTitle;

    public String getTestCaseTitle() {
        return testCaseTitle;
    }

    public void setTestCaseTitle(String testCaseTitle) {
        this.testCaseTitle = testCaseTitle;
    }

    public String getExpectedMessage() {
        return expectedMessage;
    }
    public void setExpectedMessage(String expectedMessage) {

        this.expectedMessage = expectedMessage;
    }

}
