package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class DriverInitializer {
    public WebDriver driver;
    public WebDriverWait wait;
    public Actions actions;

    public void startDriver(Browser browser, String env, String host) throws MalformedURLException {
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
            this.driver = new RemoteWebDriver(new URL(host), capabilities);
// FOR FILE UPLOADS
            ((RemoteWebDriver) driver).setFileDetector(new LocalFileDetector());
        } else {
            capabilities = initCaps(browser);
            switch (browser) {
                case CHROME:
                    WebDriverManager.chromedriver().setup();
                    this.driver = new ChromeDriver((ChromeOptions) capabilities);
                    break;
                case FIREFOX:
                    WebDriverManager.firefoxdriver().setup();
                    this.driver = new FirefoxDriver((FirefoxOptions) capabilities);
                    break;
            }
        }

        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 5);
        actions = new Actions(driver);

        if (host != null){
            driver.get(env);
        } else {
            driver.get("http://webdriveruniversity.com/index.html");
        }
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

    private MutableCapabilities initCaps(Browser browser) {
        switch (browser) {
            case CHROME:
                return getChromeCapabilities();
            case FIREFOX:
                return getFirefoxCapabilities();
            default:
                throw new RuntimeException(browser + " browser is not expected");
        }
    }
}
