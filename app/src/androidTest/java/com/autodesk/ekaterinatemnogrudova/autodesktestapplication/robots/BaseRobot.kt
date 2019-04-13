package com.autodesk.ekaterinatemnogrudova.autodesktestapplication.robots

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.ViewInteraction
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.clearText
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.RootMatchers.isDialog
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.v7.widget.RecyclerView
import android.view.View
import com.autodesk.ekaterinatemnogrudova.autodesktestapplication.EspressoUITestConstants
import com.autodesk.ekaterinatemnogrudova.autodesktestapplication.EspressoUITestManager
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Matchers.allOf


open class BaseRobot {

    fun fillEditText(viewInteraction: ViewInteraction, text: String): ViewInteraction =
            viewInteraction.perform(ViewActions.replaceText(text), ViewActions.closeSoftKeyboard())

    fun clickButton(viewInteraction: ViewInteraction): ViewInteraction = viewInteraction.perform(ViewActions.click())

    fun view(resId: Int): ViewInteraction = onView(withId(resId))

    fun view(resId: Int, position: String): ViewInteraction = onView(allOf(withText(position), isDescendantOfA(withId(resId))))

    fun viewWithText(resId: Int): ViewInteraction = onView(withText(resId))

    fun matchText(viewInteraction: ViewInteraction, text: String): ViewInteraction = viewInteraction
            .check(ViewAssertions.matches(ViewMatchers.withText(text)))

    fun matchTextInputLayout(viewInteraction: ViewInteraction, text: String): ViewInteraction = viewInteraction
            .check(matches(EspressoUITestManager.getInstance().hasText(EspressoUITestConstants.ViewType.INPUT_LAYOUT, text)))

    fun matchTextInSpinner(viewInteraction: ViewInteraction, text: String): ViewInteraction = viewInteraction
            .check(matches(EspressoUITestManager.getInstance().hasText(EspressoUITestConstants.ViewType.SPINNER, text)))

    fun matchTextButton(viewInteraction: ViewInteraction, text: String): ViewInteraction = viewInteraction
            .check(matches(EspressoUITestManager.getInstance().hasText(EspressoUITestConstants.ViewType.BUTTON, text)))

    fun matchTextToolBar(viewInteraction: ViewInteraction, text: String): ViewInteraction = viewInteraction
            .check(matches(EspressoUITestManager.getInstance().hasText(EspressoUITestConstants.ViewType.TOOLBAR, text)))

    fun matchTextDialog(viewInteraction: ViewInteraction): ViewInteraction = viewInteraction
            .inRoot(isDialog()).check(matches(isDisplayed()))

    fun matchTextTabBar(viewInteraction: ViewInteraction, text: String): ViewInteraction = viewInteraction
            .check(matches(EspressoUITestManager.getInstance().hasText(EspressoUITestConstants.ViewType.TAB_LAYOUT, text)))

    fun matchTextRelativeLayout(viewInteraction: ViewInteraction, text: String): ViewInteraction = viewInteraction
            .check(matches(EspressoUITestManager.getInstance().hasText(EspressoUITestConstants.ViewType.RELATIVE_LAYOUT, text)))

    fun matchTextLinearLayout(viewInteraction: ViewInteraction, text: String): ViewInteraction = viewInteraction
            .check(matches(EspressoUITestManager.getInstance().hasText(EspressoUITestConstants.ViewType.LINEAR_LAYOUT, text)))

    fun matchTextAppCompactTextView(viewInteraction: ViewInteraction, text: String): ViewInteraction = viewInteraction
            .check(matches(EspressoUITestManager.getInstance().hasText(EspressoUITestConstants.ViewType.APP_CompatTextView, text)))

    fun clickListItem(viewInteraction: ViewInteraction, position: Int) {
        viewInteraction.perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(position, click()))
    }

    fun clickTab(viewInteraction: ViewInteraction) {
        viewInteraction.perform(click())
    }

    fun clearEditText(iewInteraction: ViewInteraction): ViewInteraction =
            iewInteraction.perform(clearText())

    fun isButtonDisabled(viewInteraction: ViewInteraction): ViewInteraction =
            viewInteraction.check(matches(not<View>(isEnabled())))

}