package pages;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import steps.HomePageSteps;
import utils.Browser;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class TestBasis {
    public WebDriver driver;
    public WebDriverWait wait;
    public Actions actions;

    public HomePage homePage;
    public HomePageSteps homePageSteps;

    private final Browser browser = Browser.getBrowser();

    String host = "http://localhost:4444/wd/hub";  // if null -tests would be started locally
//    String host = null;

    @Parameters({"env"})
    @BeforeMethod
    protected void actionsBeforeMethod(String env, ITestContext testContext) throws MalformedURLException {
//    protected void actionsBeforeMethod(ITestContext testContext) throws MalformedURLException {
        MutableCapabilities capabilities;
        String hostTemplate = "http://%s:4444/wd/hub";
        if (host != null) {
            if (System.getProperty("browser") != null && System.getProperty("browser").equals("firefox")){
                capabilities = getFirefoxCapabilities();
            } else {
                capabilities = getChromeCapabilities();
            }
            if (System.getProperty("HUB_HOST") != null){
                host = String.format(hostTemplate, System.getProperty("HUB_HOST"));
            }
            String testName = testContext.getCurrentXmlTest().getName();
            capabilities.setCapability("name", testName);
            this.driver = new RemoteWebDriver(new URL(host), capabilities);

        } else {
            capabilities = initCaps();
            switch (browser) {
                case CHROME:
                    WebDriverManager.chromedriver().setup();
                    this.driver = new ChromeDriver((ChromeOptions) capabilities);
                    break;
                case FIREFOX:
                    WebDriverManager.firefoxdriver().setup();
                    this.driver = new FirefoxDriver((FirefoxOptions) capabilities);
            }
        }

        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 5);
        actions = new Actions(driver);
//        driver.get("http://webdriveruniversity.com/index.html");
        driver.get(env);
        homePage = new HomePage(driver, wait, actions);
        homePageSteps = new HomePageSteps(driver, wait, actions);
    }

    @AfterMethod
    protected void actionsAfterMethod() {
        driver.quit();
    }

    private MutableCapabilities initCaps() {
        switch (browser) {
            case CHROME:
                return getChromeCapabilities();
            case FIREFOX:
                return getFirefoxCapabilities();
            default:
                throw new RuntimeException(browser + " browser is not expected");
        }
    }

    public WebDriver getDriver() {
        return this.driver;
    }

    protected void waitForPageToBeFullyLoaded() {
        new WebDriverWait(getDriver(), 10).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
    }

    private MutableCapabilities getChromeCapabilities() {
        ChromeOptions chromeOptions = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_settings.popups", 0);
        prefs.put("safebrowsing.enabled", "true");
        prefs.put("profile.default_content_settings.multiple-automatic-downloads", 1);
        chromeOptions.setExperimentalOption("prefs", prefs);
        chromeOptions.addArguments("disable-infobars");
        chromeOptions.addArguments(String.format("--lang=%s", "en"));
        return chromeOptions;
    }

    private MutableCapabilities getFirefoxCapabilities() {
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setCapability("marionette", true);
        FirefoxProfile firefoxProfile = new FirefoxProfile();
        firefoxProfile.setPreference("intl.accept_languages", "en");
        firefoxOptions.setProfile(firefoxProfile);
        return firefoxOptions;
    }
}