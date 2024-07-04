package com.example.calendertaskapp

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.example.calendertaskapp.adapter.MonthViewAdapter
import com.example.calendertaskapp.fragment.TaskListFragment
import com.example.calendertaskapp.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
@AndroidEntryPoint
class FragmentNavigationTest {

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testNavigateToMonthViewFragment() {
        // Launch TaskListFragment
        launchFragmentInContainer<TaskListFragment>()

        // Click on add task button
        onView(withId(R.id.btnAddTask)).perform(click())

        // Verify that MonthViewFragment is displayed
        onView(withId(R.id.monthViewFragment)).check(matches(isDisplayed()))

        // Click on a date in the MonthViewFragment
        onView(withId(R.id.recyclerViewCalendar))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<MonthViewAdapter.DayViewHolder>(
                    15, click()
                )
            )

        // Verify the add task dialog is displayed
        onView(withId(R.id.etTaskTitle)).check(matches(isDisplayed()))
    }

    @Test
    fun testShowAddTaskDialog() {
        // Assuming you have a button to open the dialog, with ID btnOpenDialog
        onView(withId(R.id.btnOpenDialog)).perform(click())

        // Set the task title and description
        onView(withId(R.id.etTaskTitle)).perform(typeText("Test Task Title"))
        onView(withId(R.id.etTaskDescription)).perform(typeText("Test Task Description"))

        // Close the soft keyboard
        closeSoftKeyboard()

        // Click the add task button
        onView(withId(R.id.btnAddTask)).perform(click())

    }

}
