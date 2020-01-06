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
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import java.io.FileNotFoundException;
import java.util.Random;

public class StoreModelTest implements FsmModel {
    private StoreStates modelState;
    private boolean isLoggedIn, isLoggedOut, isSearching, isAddingToCart, isRemovingFromCart, isCheckingOut;
    private ScanMaltaStateRels sut;
    private WebDriver browser;

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
   /* public boolean ringGuard () {
        return getState().equals(TelephoneSystemStates.ON_HOOK);
    }
    public @Action void ring () {
        sut.incomingCall();

        ringing = true;
        modelState = TelephoneSystemStates.RINGING;

        Assert.assertEquals("The model's ringing state doesn't match the SUT's state.", ringing, sut.isRinging());
    }

    public boolean stopRingingGuard () {
        return getState().equals(TelephoneSystemStates.RINGING);
    }
    public @Action void stopRinging () {
        sut.stopRinging();

        ringing = false;
        modelState = TelephoneSystemStates.ON_HOOK;

        Assert.assertEquals("The model's ringing state doesn't match the SUT's state.", ringing, sut.isRinging());
    }

    public boolean answerCallGuard () {
        return getState().equals(TelephoneSystemStates.RINGING);
    }
    public @Action void answerCall () {
        sut.answerCall();

        ringing = false;
        onHook = false;
        inCall = true;
        modelState = TelephoneSystemStates.IN_CALL;

        Assert.assertEquals("The model's ringing state doesn't match the SUT's state.", ringing, sut.isRinging());
        Assert.assertEquals("The model's on hook state doesn't match the SUT's state.", onHook, sut.isOnHook());
        Assert.assertEquals("The model's in call state doesn't match the SUT's state.", inCall, sut.isInCall());
    }

    public boolean liftReceiverGuard () {
        return getState().equals(TelephoneSystemStates.ON_HOOK);
    }
    public @Action void liftReceiver () {
        sut.liftReceiver();

        onHook = false;
        dialTone = true;
        modelState = TelephoneSystemStates.DIAL_TONE;

        Assert.assertEquals("The model's on hook state doesn't match the SUT's state.", onHook, sut.isRinging());
        Assert.assertEquals("The model's dial tone state doesn't match the SUT's state.", dialTone, sut.isDialTone());
    }

    public boolean pressNumberSequenceGuard () {
        return getState().equals(TelephoneSystemStates.DIAL_TONE);
    }
    public @Action void pressNumberSequence () {
        sut.call(123);

        dialTone = false;
        inCall = true;
        modelState = TelephoneSystemStates.IN_CALL;

        Assert.assertEquals("The model's dial tone state doesn't match the SUT's state.", dialTone, sut.isDialTone());
        Assert.assertEquals("The model's in call state doesn't match the SUT's state.", inCall, sut.isInCall());
    }

    public boolean hangupReceiverGuard () {
        return getState().equals(TelephoneSystemStates.DIAL_TONE) || getState().equals(TelephoneSystemStates.IN_CALL);
    }
    public @Action void hangupReceiver () {
        sut.putDownReceiver();

        if (getState() == TelephoneSystemStates.DIAL_TONE) {
            dialTone = false;
        } else {
            inCall = false;
        }
        onHook = true;
        modelState = TelephoneSystemStates.ON_HOOK;

        Assert.assertEquals("The model's dial tone state doesn't match the SUT's state.", dialTone, sut.isDialTone());
        Assert.assertEquals("The model's in call state doesn't match the SUT's state.", inCall, sut.isInCall());
        Assert.assertEquals("The model's on hook state doesn't match the SUT's state.", onHook, sut.isOnHook());
    }*/
    @Test
    public void ScanStoreSystemModelRunner () throws FileNotFoundException {
        final Tester tester = new GreedyTester(new StoreModelTest());
        tester.setRandom(new Random());
        final GraphListener graphListener = tester.buildGraph();
        tester.addListener(new StopOnFailureListener());
        tester.addListener("verbose");
        tester.addCoverageMetric(new TransitionPairCoverage());
        tester.addCoverageMetric(new StateCoverage());
        tester.addCoverageMetric(new ActionCoverage());
        tester.generate(250);tester.printCoverage();
    }
}