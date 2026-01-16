package com.example.bookshelf

import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.Matchers.containsString
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainFlowTest {

    @Test
    fun addBook_thenOpenDetails_works() {
        ActivityScenario.launch(MainActivity::class.java)

        // Click Add Book
        onView(withId(R.id.btnAdd)).perform(click())

        // Fill form
        onView(withId(R.id.etTitle)).perform(typeText("Test Book"), closeSoftKeyboard())
        onView(withId(R.id.etAuthor)).perform(typeText("Test Author"), closeSoftKeyboard())
        onView(withId(R.id.etYear)).perform(typeText("2024"), closeSoftKeyboard())

        // Select genre
        onView(withId(R.id.spGenre)).perform(click())
        onData(allOf(`is`(instanceOf(String::class.java)), `is`("Fantasy"))).perform(click())

        // Save
        onView(withId(R.id.btnSave)).perform(click())

        // Verify it appears in list
        onView(withText(containsString("Test Book"))).check(matches(isDisplayed()))

        // Open details
        onView(withText(containsString("Test Book"))).perform(click())

        // Details screen should show Share button
        onView(withId(R.id.btnShare)).check(matches(isDisplayed()))
    }
}
