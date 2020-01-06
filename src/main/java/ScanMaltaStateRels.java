import org.openqa.selenium.WebDriver;

public class ScanMaltaStateRels {

    WebDriver browser;
    ScanMaltaPageObject pageObject;

    String email = "aabela007@gmail.com";
    String password = "qwerty123";
    String productItem = "mouse";

    public ScanMaltaStateRels(WebDriver browser) {
        this.browser = browser;
        pageObject = new ScanMaltaPageObject(browser);
    }

    private boolean isLoggedIn = false, isLoggedOut = true, isSearching = false, isAddingToCart = false,
            isRemovingFromCart = false, isCheckingOut = false;

    boolean isLoggedIn(){ return isLoggedIn; }
    boolean isLoggedOut(){ return isLoggedOut; }
    boolean isSearching(){ return isSearching; }
    boolean isAddingToCart(){ return isAddingToCart; }
    boolean isRemovingFromCart(){ return isRemovingFromCart; }
    boolean isCheckingOut(){ return isCheckingOut; }

    public void setLoggedIn() {
        if(!isLoggedIn && isLoggedOut && !isSearching && !isAddingToCart && !isRemovingFromCart && !isCheckingOut){
            isLoggedIn = true;
            isLoggedOut = false;
            isSearching = false;
            isAddingToCart = false;
            isRemovingFromCart = false;
            isCheckingOut = false;

            pageObject.getWebPage();
            pageObject.login(email, password);
        }
    }
    public void setLoggedOut() {
        if(isLoggedIn && !isCheckingOut) {
            isLoggedIn = false;
            isLoggedOut = true;
            isSearching = false;
            isAddingToCart = false;
            isRemovingFromCart = false;
            isCheckingOut = false;

            pageObject.logout();
        }
    }

    public void setSearching() {
        if(!isLoggedOut && !isCheckingOut) {
            isLoggedIn = true;
            isLoggedOut = false;
            isSearching = true;
            isAddingToCart = false;
            isRemovingFromCart = false;
            isCheckingOut = false;

            pageObject.search(productItem);
        }
    }

    public void setAddingToCart() {
        if(isSearching && isLoggedIn && !isLoggedOut && !isCheckingOut) {
            isLoggedIn = true;
            isLoggedOut = false;
            isSearching = false;
            isAddingToCart = true;
            isRemovingFromCart = false;
            isCheckingOut = false;

            pageObject.selectFirstProduct();
            pageObject.buyProduct();
        }
    }

    public void setRemovingFromCart() {
        if(isLoggedIn && !isLoggedOut && !isCheckingOut) {
            isLoggedIn = true;
            isLoggedOut = false;
            isSearching = false;
            isAddingToCart = false;
            isRemovingFromCart = true;
            isCheckingOut = false;

            pageObject.removeFirstProductInCart();
        }
    }
    public void setCheckedOut() {
        if(!isLoggedOut && isLoggedIn  && !isCheckingOut && !isSearching) {
            isLoggedIn = true;
            isLoggedOut = false;
            isSearching = false;
            isAddingToCart = false;
            isRemovingFromCart = true;
            isCheckingOut = true;

            pageObject.checkout();
        }
    }
}
