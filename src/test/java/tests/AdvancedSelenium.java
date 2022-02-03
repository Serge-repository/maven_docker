package tests;

import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.TestBasis;
import utils.TestListener;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

@Listeners({TestListener.class})
@Epic("Advanced selenium training")
public class AdvancedSelenium extends TestBasis {

    @Severity(SeverityLevel.NORMAL)
    @Description("Test actions")
    @Story("Features")
    @Test
    public void actionsPage() {
        driver.findElement(By.xpath("//h1[contains(text(),'ACTIONS')]")).click();
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='draggable']/p/b")));
        actions.dragAndDrop(driver.findElement(By.xpath("//div[@id='draggable']/p/b")), driver.findElement(By.xpath("//div[@id='droppable']/p/b")))
                .build().perform();
        actions.doubleClick(driver.findElement(By.xpath("//div[@id='double-click']"))).build().perform();
        actions.moveToElement(driver.findElement(By.cssSelector("div.dropdown:nth-child(1)"))).build().perform();
        actions.moveToElement(driver.findElement(By.cssSelector("div.dropdown:nth-child(2)"))).build().perform();
        actions.moveToElement(driver.findElement(By.cssSelector("div.dropdown:nth-child(3)"))).build().perform();
        actions.clickAndHold(driver.findElement(By.cssSelector("div#click-box"))).build().perform();
    }

    @Severity(SeverityLevel.NORMAL)
    @Description("Test modal windows")
    @Story("Features")
    @Test
    public void modalsPage() {
        driver.findElement(By.xpath("//h1[contains(text(),'POPUP & ALERTS')]")).click();
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("span#button1>p")));
        driver.findElement(By.cssSelector("span#button1>p")).click();
        driver.switchTo().alert().accept();
        driver.findElement(By.cssSelector("span#button2>p")).click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//button[@class='btn btn-default']"))));
        driver.findElement(By.xpath("//button[@class='btn btn-default']")).click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//p[contains(text(),'CLICK ME!')]"))));
        driver.findElement(By.xpath("//a[contains(text(),'CLICK ME!')]")).click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//p[contains(text(),'CLICK ME!')]"))));
        driver.findElement(By.xpath("//p[contains(text(),'CLICK ME!')]")).click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//button[@class='btn btn-default']"))));
        driver.findElement(By.xpath("//button[@class='btn btn-default']")).click();
    }

    @Severity(SeverityLevel.NORMAL)
    @Description("Test iframes")
    @Story("Features")
    @Test
    public void framesPage() {
        driver.findElement(By.xpath("//h1[contains(text(),'IFRAME')]")).click();
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        waitForPageToBeFullyLoaded();
        driver.switchTo().frame(0);
        driver.findElement(By.xpath("//a[@class='left carousel-control']")).click();
    }

    @Severity(SeverityLevel.NORMAL)
    @Description("Test autocomplete fields")
    @Story("Features")
    @Test
    public void autocompletePage() {
        driver.findElement(By.xpath("//h1[contains(text(),'AUTOCOMPLETE TEXTFIELD')]")).click();
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        driver.findElement(By.cssSelector("input#myInput")).click();
        driver.findElement(By.cssSelector("input#myInput")).sendKeys("a");
        driver.findElement(By.xpath("//div[contains(text(),'pple')]")).click();
    }

    @Severity(SeverityLevel.NORMAL)
    @Description("Test file upload")
    @Story("Features")
    @Test
    public void fileUploadPage() throws AWTException {
        driver.findElement(By.xpath("//h1[contains(text(),'FILE UPLOAD')]")).click();
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        waitForPageToBeFullyLoaded();

        actions.moveToElement(driver.findElement(By.cssSelector("input#myFile")), -70, 0).click().build().perform();

        String fileSeparator = FileSystems.getDefault().getSeparator();
        Path path = Paths.get(System.getProperty("user.dir"));
        String pathAsString = path.toString();
        StringSelection filePath = new StringSelection(pathAsString + fileSeparator + "src" + fileSeparator + "test"
                + fileSeparator + "resources" + fileSeparator + "images" + fileSeparator + "orange.jpg");
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(filePath, null);
        Robot robot = new Robot();
        robot.setAutoDelay(1000);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_V);
        robot.setAutoDelay(1000);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);

        driver.findElement(By.id("submit-button")).click();
    }
}