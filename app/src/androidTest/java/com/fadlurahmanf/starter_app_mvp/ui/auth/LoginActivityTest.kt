package com.fadlurahmanf.starter_app_mvp.ui.auth

import android.content.Context
import android.view.autofill.AutofillManager
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.fadlurahmanf.starter_app_mvp.R
import kotlinx.coroutines.delay
import org.hamcrest.CoreMatchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginActivityTest{

    @get:Rule
    var activityRule = ActivityScenarioRule(LoginActivity::class.java)

    private lateinit var email:String
    private lateinit var password:String

    @Before
    fun before(){
        email = "tffajari@gmail.com"
        password = "123456"
    }

    @Test
    fun `email_empty_password_empty`(){
        onView(withId(R.id.et_email)).perform(ViewActions.typeText("")).perform(closeSoftKeyboard())
        onView(withId(R.id.et_email)).check(matches(withText("")))
        onView(withId(R.id.et_password)).perform(typeText("")).perform(closeSoftKeyboard())
        onView(withId(R.id.et_password)).check(matches(withText("")))

        onView(withId(R.id.btn_login)).check(matches(isNotEnabled()))
    }

    @Test
    fun `email_not_empty_password_empty`(){
        onView(withId(R.id.et_email)).perform(ViewActions.typeText("tffajari@gmail.com")).perform(closeSoftKeyboard())
        onView(withId(R.id.et_password)).perform(typeText("")).perform(closeSoftKeyboard())

        Thread.sleep(1000)
        onView(withId(R.id.btn_login)).check(matches(isNotEnabled()))
    }

    @Test
    fun `email_empty_password_not_empty`(){
        onView(withId(R.id.et_email)).perform(ViewActions.typeText("")).perform(closeSoftKeyboard())
        onView(withId(R.id.et_password)).perform(typeText("123456")).perform(closeSoftKeyboard())

        Thread.sleep(1000)
        onView(withId(R.id.btn_login)).check(matches(isNotEnabled()))
    }

    @Test
    fun `email_does_not_match_password_less_than_6_character`(){
        onView(withId(R.id.et_email)).perform(ViewActions.typeText("tffajari")).perform(closeSoftKeyboard())
        onView(withId(R.id.et_password)).perform(typeText("1234")).perform(closeSoftKeyboard())

        Thread.sleep(1000)
        onView(withId(R.id.btn_login)).check(matches(isNotEnabled()))
    }
}