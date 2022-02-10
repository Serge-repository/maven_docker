package steps;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.GeneralPage;
import pages.HomePage;

public class HomePageSteps extends GeneralPage {
    HomePage homePage = new HomePage(driver, wait, actions);

    public HomePageSteps(WebDriver driver, WebDriverWait wait, Actions actions) {
        super(driver, wait, actions);
    }

    @Step("Test advertise scrolling left and right")
    public WebElement advertiseTest(){
        return homePage.scrollAdvertiseLeftAndRight();
    }

    @Step("Add some item to cart")
    public void addItemToCart(int productNumber){
        homePage.getItemsListFragment().addToCartUsingAction(productNumber);
    }

    @Step("Check if item quantity in cart is correct")
    public String itemsQuantityInCart(){
        return homePage.getPageTopFragment().checkItemsQuantityInCart();
    }
}
