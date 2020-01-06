import org.openqa.selenium.WebDriver;

public class ScanMaltaStateRels {

    WebDriver browser;
    ScanMaltaPageObject pageObject;

    String email = "aabela007@gmail.com";
    String password = "qwerty123";
    String productItem = "mouse";

    public ScanMaltaStateRels(WebDriver browser) {
        this.browser = browser;
        browser.get("https://www.scanmalta.com/newstore/");
        pageObject = new ScanMaltaPageObject(browser);
    }

    boolean isLoggedIn = false,
            isLoggedOut = true,
            isSearching = false,
            isAddingToCart = false,
            isRemovingFromCart = false,
            isCheckingOut = false;

    boolean isLoggedIn(){ return isLoggedIn; }
    boolean isLoggedOut(){ return isLoggedOut; }
    boolean isSearching(){ return isSearching; }
    boolean isAddingToCart(){ return isAddingToCart; }
    boolean isRemovingFromCart(){ return isRemovingFromCart; }
    boolean isCheckingOut(){ return isCheckingOut; }

    public void setLoggedIn() {
        isLoggedIn = true;
        isLoggedOut = false;
        isSearching = true;
        isAddingToCart = true;
        isRemovingFromCart = true;
        isCheckingOut = false;

        pageObject.getWebPage();
        pageObject.login(email, password);
    }

    public void setSearching() {
        isLoggedIn = true;
        isLoggedOut = false;
        isSearching = true;
        isAddingToCart = true;
        isRemovingFromCart = true;
        isCheckingOut = false;

        pageObject.search(productItem);
    }

    public void setAddingToCart() {
        isLoggedIn = true;
        isLoggedOut = false;
        isSearching = true;
        isAddingToCart = true;
        isRemovingFromCart = false;
        isCheckingOut = false;

        pageObject.selectFirstProduct();
        pageObject.buyProduct();
    }

    public void removingFromCart() {
        isLoggedIn = true;
        isLoggedOut = false;
        isSearching = true;
        isAddingToCart = true;
        isRemovingFromCart = true;
        isCheckingOut = false;

        pageObject.removeFirstProductInCart();
    }
}
