package com.autodesk.ekaterinatemnogrudova.autodesktestapplication;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.autodesk.ekaterinatemnogrudova.autodesktestapplication.ui.ArticlesActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class EspressoUITest {
    @Rule
    public ActivityTestRule<ArticlesActivity> mActivityTestRule = new ActivityTestRule<>(ArticlesActivity.class);
    @Before
    public void setPreConditions() {
        EspressoUITestManager.getInstance().registerIdlingResource();
    }

    @After
    public void setAfterConditions() {
        EspressoUITestManager.getInstance().unregisterIdlingResource();
    }

    @Test
    public void validateArticleClickSuccess() {
        EspressoUITestManager.getInstance().validateArticleClickSuccess();
    }
}
