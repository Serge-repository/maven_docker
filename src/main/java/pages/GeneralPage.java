package pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

public class GeneralPage {
    public WebDriver driver;
    public WebDriverWait wait;
    public Actions actions;

    public GeneralPage(WebDriver driver, WebDriverWait wait, Actions actions) {
        this.driver = driver;
        this.wait = wait;
        this.actions = actions;
        PageFactory.initElements(driver, this);
    }

    protected void waitingForPageElement(WebElement pageElement) {
        wait.until(visibilityOf(pageElement));
    }

    protected void moveToElement(WebElement pageElement) {
        actions.moveToElement(pageElement).build().perform();
    }

    protected List<WebElement> getElementList(List<WebElement> elementsList) {
        return elementsList;
    }

    protected WebElement getElement(WebElement pageElement) {
        return pageElement;
    }

    protected void scrollMethod(WebElement requiredElement) {
        waitingForPageElement(requiredElement);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", getElement(requiredElement));
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
    }
}
