import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.junit.Assert.*;

public class ScanMaltaPageObject {
    WebDriver browser = null;
    public void sleep(int seconds) {
        try {
            Thread.sleep(seconds*1000);
        } catch (Exception e) {}
    }

    public ScanMaltaPageObject(WebDriver browser) {
        this.browser = browser;
    }

    public void getWebPage() {
        browser.get("https://www.scanmalta.com/newstore/customer/account/login/");
    }

    public void login(String emailAddress, String password) {
        browser.findElement(By.name("login[username]")).sendKeys(emailAddress);
        browser.findElement(By.name("login[password]")).sendKeys(password);
        browser.findElement(By.name("send")).submit();
        sleep(1);
    }

    public void testLoginSuccess() {
        assertTrue(browser.findElement(By.tagName("h1")).getText().contains("My Dashboard"));
        assertTrue(browser.findElement(By.className("hello")).getText().contains("Hello"));
        assertTrue(browser.findElement(By.className("hello")).getText().contains("Aaron Abela"));
        assertTrue(browser.findElement(By.className("box-content")).getText().contains("Aaron Abela"));
        assertTrue(browser.findElement(By.className("box-content")).getText().contains("aabela007@gmail.com"));
        sleep(1);
    }

    public void testLoginFailure() {
        assertTrue(browser.findElement(By.className("error-msg")).getText().contains("Invalid login or password"));
        sleep(1);
    }

    public void search(String searchTerm) {
        browser.findElement(By.id("search")).sendKeys(searchTerm);
        browser.findElement(By.className("search-box")).submit();
        sleep(1);
    }

    public void selectFirstProduct() {
        List<WebElement> productsList = browser.findElements(By.className("product-image"));
        productsList.get(0).click();
        sleep(1);
    }

    public void seeProductDetails() {
        assertTrue(browser.findElement(By.className("product-description")).getText().contains("Product Description"));
    }

    public void cartIsEmpty(){
        browser.get("https://www.scanmalta.com/newstore/checkout/cart/");
        if(!isCartEmpty()) {
            browser.findElement(By.id("empty_cart_button")).sendKeys("\n");
        }
        sleep(1);
    }

    public void buyProduct() {
        browser.findElement(By.id("product-addtocart-button")).submit();
        sleep(1);
    }
    public boolean isCartEmpty(){
        browser.get("https://www.scanmalta.com/newstore/checkout/cart/");
        return browser.findElement(By.tagName("h1")).getText().contains("Shopping Cart is Empty");
    }

    public int getCartItemNo() {
        browser.get("https://www.scanmalta.com/newstore/checkout/cart/");
        String amount = browser.findElement(By.xpath("//span[contains(text(), ' items')]")).getText();
        return Integer.parseInt(amount.split(" ")[0]);
    }

    public void testCartHasOnlyXItems(int itemNo) {
        int cartItemNo = getCartItemNo();
        assertEquals(cartItemNo, itemNo);
    }

    public void selectXProductsAndAddToCart(int itemNo) {
        List<WebElement> productsList;
        WebElement product;
        cartIsEmpty();
        for (int itemIndex=0; itemIndex<itemNo; itemIndex++){
            search("mouse");
            productsList = browser.findElements(By.className("product-image"));
            product = productsList.get(itemIndex);
            product.click();
            buyProduct();
            sleep(1);
        }
    }

    public void addFirstFoundProductToCart(String productTerm) {
        search(productTerm);
        selectFirstProduct();
        buyProduct();
        sleep(1);
    }

    public void removeFirstProductInCart() {
        WebElement cartObject = browser.findElement(By.className("cart-table"));
        WebElement tableBody = cartObject.findElement(By.xpath("//table/tbody"));
        WebElement product1 = tableBody.findElement(By.className("first"));
        product1.findElement(By.className("btn-remove")).click();
        sleep(1);
    }

    public void logout() {
        browser.get("https://www.scanmalta.com/newstore/customer/account/logoutSuccess/");
        sleep(1);
    }
    public void checkout() {
        browser.get("    https://www.scanmalta.com/newstore/checkout/");
        sleep(1);
    }
}
