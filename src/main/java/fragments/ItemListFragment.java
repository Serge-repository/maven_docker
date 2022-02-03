package fragments;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.GeneralPage;

import java.util.List;

public class ItemListFragment extends GeneralPage {
    @FindBy(xpath = "//span[contains(text(), 'Add to cart')]")
    private List<WebElement> addToCartButtons;
    @FindBy(xpath = "(//a[@class='product-name'][@title='Blouse'])[1]")
    private WebElement blouse;
    @FindBy(xpath = "//span[contains(text(),'Proceed to checkout')]")
    private WebElement checkoutButton;

    public ItemListFragment(WebDriver driver, WebDriverWait wait, Actions actions) {
        super(driver, wait, actions);
    }

    public void addToCartUsingAction(int productNumber) {
        scrollMethod(blouse);
        moveToElement(blouse);
        getElementList(addToCartButtons).get(productNumber).click();
        waitingForPageElement(checkoutButton);
        checkoutButton.click();
    }
}