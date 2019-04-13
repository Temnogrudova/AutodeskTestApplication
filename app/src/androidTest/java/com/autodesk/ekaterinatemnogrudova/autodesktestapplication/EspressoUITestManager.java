package com.autodesk.ekaterinatemnogrudova.autodesktestapplication;

import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputLayout;
import android.support.test.espresso.IdlingPolicies;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import com.autodesk.ekaterinatemnogrudova.autodesktestapplication.robots.ArticlesRobot;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.concurrent.TimeUnit;
import static com.autodesk.ekaterinatemnogrudova.autodesktestapplication.EspressoUITestConstants.WAITING_TIME;

public class EspressoUITestManager {
    private static EspressoUITestManager sINSTANCE = null;
    private static final Object sInstanceLock = new Object();
    private IdlingResource mIdlingResource;

    public static EspressoUITestManager getInstance() {
        if (sINSTANCE == null) {
            synchronized (sInstanceLock) {
                if (sINSTANCE == null)
                    sINSTANCE = new EspressoUITestManager();
            }
        }
        return sINSTANCE;
    }

    public void registerIdlingResource() {
        // IDLE: Make sure Espresso does not time out
        IdlingPolicies.setMasterPolicyTimeout(WAITING_TIME * 2, TimeUnit.MINUTES);
        IdlingPolicies.setIdlingResourceTimeout(WAITING_TIME * 2, TimeUnit.MINUTES);
        unregisterIdlingResource();
        // IDLE: Now we wait
        mIdlingResource = new SanityTestIdlingResource(WAITING_TIME);
        IdlingRegistry.getInstance().register(mIdlingResource);
    }

    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(mIdlingResource);
        }
    }

    public void validateArticleClickSuccess() {
        ArticlesRobot listArticlesRobot = new ArticlesRobot();
        listArticlesRobot
                .matchToolbarDisplayedForText("NewsApp Candidate Assignment")
                .clickOnArticle(0);
    }

    public Matcher<View> hasText(final int viewType, final String expectedText) {
        return new TypeSafeMatcher<View>() {

            @Override
            public boolean matchesSafely(View view) {
                CharSequence text = null;
                switch (viewType) {
                    case EspressoUITestConstants.ViewType.INPUT_LAYOUT:
                        if (!(view instanceof TextInputLayout)) {
                            return false;
                        }
                        text = ((TextInputLayout) view).getError();
                        break;

                    case EspressoUITestConstants.ViewType.BUTTON:
                        if (!(view instanceof Button)) {
                            return false;
                        }
                        text = ((Button) view).getText();
                        break;

                    case EspressoUITestConstants.ViewType.APP_CompatTextView:
                        if (!(view instanceof AppCompatTextView)) {
                            return false;
                        }
                        text = ((AppCompatTextView) view).getText();
                        break;

                    case EspressoUITestConstants.ViewType.TOOLBAR:
                        if (!(view instanceof Toolbar)) {
                            return false;
                        }
                        Toolbar toolbar = (Toolbar) view;
                        for (int i = 0; i < toolbar.getChildCount(); ++i) {
                            View child = toolbar.getChildAt(i);
                            if (child instanceof TextView) {
                                text = ((TextView) child).getText();
                                break;
                            }
                        }
                        break;

                    case EspressoUITestConstants.ViewType.TAB_LAYOUT:
                        if (!(view instanceof TabLayout)) {
                            return false;
                        }
                        TabLayout tabLayout = (TabLayout) view;
                        for (int i = 0; i < tabLayout.getChildCount(); ++i) {
                            View child = tabLayout.getChildAt(i);
                            if (child instanceof TextView) {
                                text = ((TextView) child).getText();
                                break;
                            }
                        }
                        break;

                    case EspressoUITestConstants.ViewType.RELATIVE_LAYOUT:
                        if (!(view instanceof RelativeLayout)) {
                            return false;
                        }
                        RelativeLayout relativeLayout = (RelativeLayout) view;
                        for (int i = 0; i < relativeLayout.getChildCount(); ++i) {
                            View child = relativeLayout.getChildAt(i);
                            if (child instanceof TextView) {
                                text = ((TextView) child).getText();
                                break;
                            }
                        }
                        break;

                    case EspressoUITestConstants.ViewType.LINEAR_LAYOUT:
                        if (!(view instanceof LinearLayout)) {
                            return false;
                        }
                        LinearLayout linearLayout = (LinearLayout) view;
                        for (int i = 0; i < linearLayout.getChildCount(); ++i) {
                            View child = linearLayout.getChildAt(i);
                            if (child instanceof TextView) {
                                text = ((TextView) child).getText();
                                break;
                            }
                        }
                        break;

                    case EspressoUITestConstants.ViewType.SPINNER:
                        if (!(view instanceof Spinner)) {
                            return false;
                        }
                        text = ((TextView) ((Spinner) view).getChildAt(0)).getText();
                        break;

                }

                if (text == null) {
                    return false;
                }

                String hint = text.toString();

                return expectedText.equals(hint);
            }

            @Override
            public void describeTo(Description description) {
            }
        };
    }
}

class SanityTestIdlingResource implements IdlingResource {
    private final long startTime;
    private final long waitingTime;
    private ResourceCallback resourceCallback;

    public SanityTestIdlingResource(long waitingTime) {
        this.startTime = System.currentTimeMillis();
        this.waitingTime = waitingTime;
    }

    @Override
    public String getName() {
        return SanityTestIdlingResource.class.getName() + ":" + waitingTime;
    }

    @Override
    public boolean isIdleNow() {
        long elapsed = System.currentTimeMillis() - startTime;
        boolean idle = (elapsed >= waitingTime);
        if (idle) {
            resourceCallback.onTransitionToIdle();
        }
        return idle;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback resourceCallback) {
        this.resourceCallback = resourceCallback;
    }
}
