package com.miso.vinilos

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.miso.vinilos.landing.LandingActivity
import com.miso.vinilos.main.MainActivity
import com.miso.vinilos.ui.adapters.AlbumsAdapter
import com.miso.vinilos.ui.listactivities.AlbumListActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class VinilosProductInstrumentalTest {

    @get:Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup() {
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun testNavigationToLandingActivityUsuario() {
        onView(withId(R.id.btnUsuario)).perform(click())
        Intents.intended(IntentMatchers.hasComponent(LandingActivity::class.java.name))
    }

    @Test
    fun testNavigationToLandingActivityAdmin() {
        onView(withId(R.id.btnAdmin)).perform(click())
        Intents.intended(IntentMatchers.hasComponent(LandingActivity::class.java.name))
    }

    @Test
    fun testBtnAdminAddDisplay(){
        onView(withId(R.id.btnAdmin)).perform(click())
        Intents.intended(IntentMatchers.hasComponent(LandingActivity::class.java.name))
        onView(withId(R.id.act)).check(matches(isDisplayed()))
        onView(withId(R.id.act)).perform(click())
    }

    @Test
    fun testBtnBackToMain(){
        onView(withId(R.id.btnAdmin)).perform(click())
        Intents.intended(IntentMatchers.hasComponent(LandingActivity::class.java.name))
        onView(withId(R.id.btnBackToMain)).check(matches(isDisplayed()))
        onView(withId(R.id.btnBackToMain)).perform(click())
    }

    @Test
    fun end2EndUsuario(){
        onView(withId(R.id.btnUsuario)).perform(click())
        Intents.intended(IntentMatchers.hasComponent(LandingActivity::class.java.name))
        onView(withId(R.id.imgAlbumes)).perform(click())
        Intents.intended(IntentMatchers.hasComponent(AlbumListActivity::class.java.name))
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))
        Thread.sleep(500)
        onView(withId(R.id.recyclerView)).perform(actionOnItemAtPosition<AlbumsAdapter.AlbumsViewHolder>(0, click()))
    }
}