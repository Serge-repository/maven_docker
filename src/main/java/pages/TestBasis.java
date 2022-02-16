package pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import steps.HomePageSteps;
import utils.Browser;
import utils.DriverInitializer;

import java.net.MalformedURLException;

public class TestBasis extends DriverInitializer {

    public HomePageSteps homePageSteps;

    private final Browser browser = Browser.getBrowser();

    String host = "http://localhost:4444/wd/hub";  // if null -tests would be started locally
//    String host = null;

    @Parameters({"env"})
    @BeforeMethod
    protected void actionsBeforeMethod(String env) throws MalformedURLException {
        startDriver(browser, env, host);
        homePageSteps = new HomePageSteps(driver, wait, actions);
    }

// Uncomment for local run
//    @BeforeMethod
//    protected void actionsBeforeMethod() throws MalformedURLException {
//        startDriver(browser, null, host);
//        homePageSteps = new HomePageSteps(driver, wait, actions);
//    }

    @AfterMethod
    protected void actionsAfterMethod() {
        driver.quit();
    }

    public WebDriver getDriver() {
        return this.driver;
    }

    protected void waitForPageToBeFullyLoaded() {
        new WebDriverWait(getDriver(), 10).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
    }
}