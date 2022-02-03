package tests;

import io.qameta.allure.*;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.TestBasis;
import utils.TestListener;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@Listeners({TestListener.class})
@Epic("Home page functionality")
public class HomePageTests extends TestBasis {

    @Severity(SeverityLevel.NORMAL)
    @Description("Check that advertise bars are scrolled left and right on home page")
    @Story("Advertising")
    @Test
    public void advertiseRound() {
//        assertTrue(homePage.scrollAdvertiseLeftAndRight().isDisplayed(), "checking that image was scrolled right");
        assertTrue(homePageSteps.advertiseTest().isDisplayed(), "checking that image was scrolled right");
    }

    @Severity(SeverityLevel.BLOCKER)
    @Description("Add 1 item to cart and check if quantity as result is correct")
    @Story("Cart")
    @Test
    public void useActions() {
//        homePage.getItemsListFragment().addToCartUsingAction(1);
//        assertEquals(homePage.getPageTopFragment().checkItemsQuantityInCart(), "1");
        homePageSteps.addItemToCart(1);
        assertEquals(homePageSteps.itemsQuantityInCart(), "1", "check if quantity in cart is correct");
    }
}
