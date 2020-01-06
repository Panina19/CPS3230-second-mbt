import junit.framework.Assert;
import nz.ac.waikato.modeljunit.Action;
import nz.ac.waikato.modeljunit.FsmModel;
import nz.ac.waikato.modeljunit.GraphListener;
import nz.ac.waikato.modeljunit.GreedyTester;
import nz.ac.waikato.modeljunit.StopOnFailureListener;
import nz.ac.waikato.modeljunit.Tester;
import nz.ac.waikato.modeljunit.coverage.ActionCoverage;
import nz.ac.waikato.modeljunit.coverage.StateCoverage;
import nz.ac.waikato.modeljunit.coverage.TransitionPairCoverage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileNotFoundException;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class StoreModelTest implements FsmModel {
    private StoreStates modelState;
    private boolean isLoggedIn, isLoggedOut, isSearching, isAddingToCart, isRemovingFromCart, isCheckingOut;
    private ScanMaltaStateRels sut;
    private WebDriver browser;

    StoreModelTest(WebDriver browser) {
        this.browser = browser;
        sut = new ScanMaltaStateRels(browser);
    }
    public StoreStates getState() {
        return modelState;
    }

    public void reset(boolean b) {
        sut.setLoggedOut();
        modelState = StoreStates.LOGGED_OUT;
        isLoggedIn = false;
        isLoggedOut = true;
        isSearching = false;
        isAddingToCart = false;
        isRemovingFromCart = false;
        isCheckingOut = false;

        if (b) {
            sut = new ScanMaltaStateRels(browser);
        }
    }
    @Before
    public void setup() {
        String chromeDriverFilePath = "C:/Users/PANINA/Desktop/Software_Testing/web/webtesting1/chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", chromeDriverFilePath);
        browser = new ChromeDriver();
        browser.manage().window().maximize();
    }

    @After
    public void teardown() { browser.quit(); }

    public boolean loggingInGuard() {
        return (!getState().equals(StoreStates.LOGGED_IN) && !getState().equals(StoreStates.CHECKING_OUT) && isLoggedOut);
    }
    public @Action
    void loggedIn() {
        sut.setLoggedIn();
        isLoggedIn = true;
        isLoggedOut = false;
        isSearching = false;
        isAddingToCart = false;
        isRemovingFromCart = false;
        isCheckingOut = false;
        modelState = StoreStates.LOGGED_IN;

        assertEquals("", isLoggedIn, sut.isLoggedIn());
    }

    public boolean loggingOutGuard() {
        return (!getState().equals(StoreStates.LOGGED_OUT)) && !(getState().equals(StoreStates.CHECKING_OUT));
    }
    public @Action
    void loggingOut() {
        sut.setLoggedOut();
        isLoggedIn = false;
        isLoggedOut = true;
        isSearching = false;
        isAddingToCart = false;
        isRemovingFromCart = false;
        isCheckingOut = false;
        modelState = StoreStates.LOGGED_OUT;

        assertEquals("", isLoggedOut, sut.isLoggedOut());
    }

    public boolean searchingGuard() {
        return (!(getState().equals(StoreStates.LOGGED_OUT)) && !(getState().equals(StoreStates.CHECKING_OUT)));
    }
    public @Action
    void searching() {
        sut.setSearching();
        isLoggedIn = true;
        isSearching = true;
        isAddingToCart = false;
        isRemovingFromCart = false;
        isCheckingOut = false;
        modelState = StoreStates.SEARCHING;

        assertEquals("", isSearching, sut.isSearching());
    }

    public boolean addingToCartGuard() {
        return  (getState().equals(StoreStates.SEARCHING));
    }
    public @Action
    void addingToCart() {
        sut.setAddingToCart();
        isLoggedIn = true;
        isSearching = false;
        isAddingToCart = true;
        isRemovingFromCart = false;
        isCheckingOut = false;
        modelState = StoreStates.ADDING_TO_CART;

        assertEquals("", isAddingToCart, sut.isAddingToCart());
    }

    public boolean removingFromCartGuard() {
        return  (getState().equals(StoreStates.REMOVING_FROM_CART)) ||
                (getState().equals(StoreStates.ADDING_TO_CART)) ;
    }
    public @Action
    void removingFromCart() {
        sut.setRemovingFromCart();
        isLoggedIn=true;
        isSearching = false;
        isAddingToCart = false;
        isRemovingFromCart = true;
        isCheckingOut = false;
        modelState = StoreStates.REMOVING_FROM_CART;

        assertEquals("", isRemovingFromCart, sut.isRemovingFromCart());
    }

    public boolean checkingOutGuard() {
        return  ((getState().equals(StoreStates.LOGGED_IN) || getState().equals(StoreStates.ADDING_TO_CART)) ||
                (getState().equals(StoreStates.REMOVING_FROM_CART)) && isLoggedIn);
    }
    public @Action
    void checkingOut() {
        sut.setCheckedOut();
        isLoggedIn = true;
        isLoggedOut = false;
        isSearching = false;
        isAddingToCart = false;
        isRemovingFromCart = false;
        isCheckingOut = true;
        modelState = StoreStates.CHECKING_OUT;

        assertEquals("", isCheckingOut, sut.isCheckingOut());
    }

    @Test
    public void ScanStoreSystemModelRunner () throws FileNotFoundException {
        final Tester tester = new GreedyTester(new StoreModelTest(browser));
        tester.setRandom(new Random());
        final GraphListener graphListener = tester.buildGraph();
        tester.addListener(new StopOnFailureListener());
        tester.addListener("verbose");
        tester.addCoverageMetric(new TransitionPairCoverage());
        tester.addCoverageMetric(new StateCoverage());
        tester.addCoverageMetric(new ActionCoverage());
        tester.generate(250);
        tester.printCoverage();
    }
}