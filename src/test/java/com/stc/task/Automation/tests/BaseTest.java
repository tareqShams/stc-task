package com.stc.task.Automation.tests;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import com.paulhammant.ngwebdriver.NgWebDriver;
import com.stc.task.automation.utils.Log;
import com.stc.task.automation.utils.PropertiesFilesHandler;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import com.stc.task.automation.constants.GeneralConstants;
import io.github.bonigarcia.wdm.WebDriverManager;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;


public class BaseTest {

    public static ExtentReports extent;
    //Selenium and Angular webDrivers
    public WebDriver driver;
    public NgWebDriver ngDriver;
    //Extent report objects
    public ExtentHtmlReporter htmlReporter;
    public ExtentTest test;

    public int success_tests = 0;
    public int fail_tests = 0;
    public int skip_tests = 0;
    JavascriptExecutor jsDriver;
    //Initialize instances of properties files to be used in all tests
    PropertiesFilesHandler propHandler = new PropertiesFilesHandler();
    Properties generalConfigsProps = propHandler.loadPropertiesFile(GeneralConstants.GENERAL_CONFIG_FILE_NAME);
    public String serverUrl = generalConfigsProps.getProperty(GeneralConstants.SERVER_URL);
    // Browser's default download path config from properties file
    Properties testdataConfigsProps = propHandler.loadPropertiesFile(GeneralConstants.TEST_DATA_CONFIG_FILE_NAME);
    public String downloadPath = System.getProperty("user.dir") + generalConfigsProps.getProperty(GeneralConstants.DEFAULT_DOWNLOAD_PATH);
    String dateTime = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
    String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
    // get report file path
    String extentReportFilePath = generalConfigsProps.getProperty(GeneralConstants.EXTENT_REPORT_FILE_PATH);

    @BeforeTest(description = "Setting up extent report", alwaysRun = true)
    @Parameters({"browserType", "buildNumber", "userType"})
    public void setExtent(String browserType, String buildNumber, String userType) {
        try {
            Log.info("Setting up extent report before test on browser: " + browserType);

            // specify location of the report
            htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + extentReportFilePath +
                    GeneralConstants.STRING_DELIMETER +
                    buildNumber + GeneralConstants.STRING_DELIMETER +
                    userType + GeneralConstants.STRING_DELIMETER +
                    dateTime + ".html");


            htmlReporter.config().setDocumentTitle(generalConfigsProps.getProperty(GeneralConstants.EXTENT_REPORT_TITLE)); // Tile of report
            htmlReporter.config().setReportName(generalConfigsProps.getProperty(GeneralConstants.EXTENT_REPORT_NAME)); // Name of the report
            htmlReporter.config().setTheme(Theme.DARK);

            extent = new ExtentReports();
            extent.attachReporter(htmlReporter);

            // Passing General information
            extent.setSystemInfo("Environment", "QA");
            extent.setSystemInfo("Browser", browserType);
            extent.setSystemInfo("Url", serverUrl);
            extent.setSystemInfo("Build number ", buildNumber);
            extent.setSystemInfo("Date", date);
        } catch (Exception e) {
            Log.error("Error occurred while setting up extent reports on " + browserType, e);
        }

    }


    @Parameters({"browserType"})
    @BeforeClass(description = "Setting up selenium webDriver before each class run", alwaysRun = true)
    public void loadConfiguration(String browserType) {
        try {
            Log.info("Initialize Selenium webDriver before tests' Class");

            // initialize selenium driver that is set as a config in testng.xml
            switch (browserType) {
                case ("Chrome"):
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver(setChromeOption());
                    break;
                case ("Firefox"):
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver(setFireFoxOption());
                    break;
                case ("IE"):
                    WebDriverManager.iedriver().setup();
                    driver = new InternetExplorerDriver();
                    break;
                case ("Edge"):
                    WebDriverManager.edgedriver().setup();
                    driver = new EdgeDriver();
                    break;

            }

            // initialize angular webDriver
            jsDriver = (JavascriptExecutor) driver;
            ngDriver = new NgWebDriver(jsDriver).withRootSelector("\"app-root\"");
            //ngDriver.waitForAngularRequestsToFinish();


            driver.manage().window().maximize();
            driver.get(serverUrl);
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);


            Log.info("Selenium webDriver was initialized successfully");


        } catch (Exception e) {
            Log.error("Error occurred while initializing selenium web driver", e);
        }

    }
    private ChromeOptions setChromeOption() {
        ChromeOptions options = new ChromeOptions();
        HashMap<String, Object> ChromePrefs = new HashMap<>();
        ChromePrefs.put("profile.default.content_settings.popups", 0);
        ChromePrefs.put("download.default_directory", downloadPath);
        options.setExperimentalOption("prefs", ChromePrefs);
        options.setAcceptInsecureCerts(true);
        return options;
    }

    private FirefoxOptions setFireFoxOption() {
        FirefoxOptions option = new FirefoxOptions();
        option.addPreference("browser.download.folderlist", 2);
        option.addPreference("browser.download.dir", downloadPath);
        option.addPreference("browser.helperApps.neverAsk.saveToDisk", "application/octet-stream");
        option.addPreference("browser.download.manager.showWhenStarting", false);
        return option;
    }


    @AfterMethod(description = "Logging test status to log file and extent report", alwaysRun = true)
    public void logTestStatusForReport(ITestResult result) {
        try {
            if (result.getStatus() == ITestResult.FAILURE) {
                Log.info("logging Testcase FAILED " + result.getName() + " in Extent Report");
                test.log(Status.FAIL, "Test Case Name FAILED is " + result.getName()); // to add name in extent report
                test.log(Status.FAIL, "EXCEPTION Thrown is " + result.getThrowable()); // to add error/exception in extent report
                fail_tests++;
                // adding screenshot.
                String screenshotPath = getScreenshot(driver, result.getName());
                test.addScreenCaptureFromPath(screenshotPath);

            } else if (result.getStatus() == ITestResult.SKIP) {
                Log.info("logging Testcase SKIPPED " + result.getName() + " in Extent Report");
                test.log(Status.SKIP, "Test Case SKIPPED is " + result.getName());
                skip_tests++;
            } else if (result.getStatus() == ITestResult.SUCCESS) {
                Log.info("logging Testcase SUCCESS " + result.getName() + " in Extent Report");
                test.log(Status.PASS, "Test Case PASSED is " + result.getName());
                success_tests++;
            }
            // log that test case has ended
            Log.endTestCase(result.getName());
        } catch (Exception e) {
            Log.warn("Error occurred while logging testcase " + result.getName() + " result to extent report", e);
        }
    }

    private String getScreenshot(WebDriver driver, String screenshotName) throws IOException {

        dateTime = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        Log.info("Taking Screenshot for the FAILED Testcase");

        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);

        //get the path of failed tests screenshots
        String screenShotsPath = generalConfigsProps.getProperty(GeneralConstants.SCREENSHOT_FAILED_TESTS_PATH);

        // after execution, you could see a folder "FailedTestsScreenshots" under src folder
        String destination = System.getProperty("user.dir") + screenShotsPath + date + GeneralConstants.FILE_DELIMETER + screenshotName + dateTime + ".png";
        File finalDestination = new File(destination);
        FileUtils.copyFile(source, finalDestination);
        return destination;
    }

    @AfterClass(description = "Quitting selenium driver after each class run", alwaysRun = true)
    public void closeDriver() {
        Log.info("Closing selenium webDriver after Class");
        if (driver != null)
            driver.quit();
    }

    @Parameters({"browserType", "buildNumber", "userType"})
    @AfterTest(description = "Closing extent report after running Test", alwaysRun = true)
    public void endReport(String browserType, String buildNumber, String userType) {
        Log.info("Closing Extent report after Test");
        if (extent != null)
            extent.flush();

        Log.info("Update Extent report name with Test Cases Status");
        File extentReportNameBeforeAddTestCaseStatus = new File(htmlReporter.getFilePath());
        File extentReportNameAfterAddTestCaseStatus = new File(System.getProperty("user.dir") + extentReportFilePath +
                GeneralConstants.STRING_DELIMETER +
                buildNumber + GeneralConstants.STRING_DELIMETER +
                userType + GeneralConstants.STRING_DELIMETER +
                dateTime + GeneralConstants.STRING_DELIMETER + "SuccessTCs" + success_tests + GeneralConstants.STRING_DELIMETER + "FailedTCs" + fail_tests + GeneralConstants.STRING_DELIMETER + "SkippedTCs" + skip_tests + ".html");
        if (extentReportNameBeforeAddTestCaseStatus.renameTo(extentReportNameAfterAddTestCaseStatus)) {
            Log.info("File renamed");
        } else {
            Log.info("Sorry! the file can't be renamed");
        }
    }
}
