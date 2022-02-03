package pages;

import fragments.ItemListFragment;
import fragments.PageTopFragment;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage extends GeneralPage {
    ItemListFragment itemListFragment = new ItemListFragment(driver, wait, actions);
    PageTopFragment pageTopFragment = new PageTopFragment(driver, wait, actions);

    @FindBy(css = "a.bx-prev")
    private WebElement scrollAdvertiseLeft;
    @FindBy(css = "a.bx-next")
    private WebElement scrollAdvertiseRight;
    @FindBy(xpath = "(//img[@src='http://automationpractice.com/modules/homeslider/images/sample-1.jpg'])[1]")
    private WebElement firstAdvertiseImage;
    @FindBy(xpath = "(//img[@src='http://automationpractice.com/modules/homeslider/images/sample-3.jpg'])[2]")
    private WebElement secondAdvertiseImage;

    public HomePage(WebDriver driver, WebDriverWait wait, Actions actions) {
        super(driver, wait, actions);
    }

    public ItemListFragment getItemsListFragment() {
        return itemListFragment;
    }

    public PageTopFragment getPageTopFragment() {
        return pageTopFragment;
    }

    public WebElement scrollAdvertiseLeftAndRight() {
        scrollMethod(scrollAdvertiseLeft);
        scrollAdvertiseLeft.click();
        waitingForPageElement(secondAdvertiseImage);
        scrollAdvertiseRight.click();
        return getElement(secondAdvertiseImage);
    }

}