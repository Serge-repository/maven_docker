package fragments;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.GeneralPage;

public class PageTopFragment extends GeneralPage {
    @FindBy(css = "span.quantity")
    private WebElement goodsInCartQuantity;
    @FindBy(xpath = "//b[contains(text(),'Cart')]")
    private WebElement cartDropdown;

    public PageTopFragment(WebDriver driver, WebDriverWait wait, Actions actions) {
        super(driver, wait, actions);
    }

    public String checkItemsQuantityInCart(){
        moveToElement(cartDropdown);
        return getElement(goodsInCartQuantity).getText();
    }
}
