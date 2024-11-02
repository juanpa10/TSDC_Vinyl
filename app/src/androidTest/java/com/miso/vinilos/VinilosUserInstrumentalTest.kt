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
import com.miso.vinilos.ui.adapters.AlbumsAdapter
import com.miso.vinilos.ui.adapters.BandsAdapter
import com.miso.vinilos.ui.listactivities.AlbumListActivity
import com.miso.vinilos.ui.listactivities.BandListActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class VinilosUserInstrumentalTest {

    @get:Rule
    var activityRule = ActivityScenarioRule(LandingActivity::class.java)

    @Before
    fun setup() {
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun testNavigationToBandListActivityUsuario() {
        onView(withId(R.id.imgArtistas)).perform(click())
        Intents.intended(IntentMatchers.hasComponent(BandListActivity::class.java.name))
    }

    @Test
    fun testAlbumDisplayUsuario(){
        onView(withId(R.id.imgAlbumes)).perform(click())
        Intents.intended(IntentMatchers.hasComponent(AlbumListActivity::class.java.name))
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))
        onView(withId(R.id.recyclerView)).perform(actionOnItemAtPosition<AlbumsAdapter.AlbumsViewHolder>(0, click()))
        onView(withId(R.id.albumFragment)).check(matches(isDisplayed()))
    }

    @Test
    fun testBandDetailDisplayUsuario() {
        onView(withId(R.id.imgArtistas)).perform(click())
        Intents.intended(IntentMatchers.hasComponent(BandListActivity::class.java.name))
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))
        onView(withId(R.id.recyclerView)).perform(actionOnItemAtPosition<BandsAdapter.BandsViewHolder>(0, click()))
        onView(withId(R.id.bandFragment)).check(matches(isDisplayed()))
    }

}