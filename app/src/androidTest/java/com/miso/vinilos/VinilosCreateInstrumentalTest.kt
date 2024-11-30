package com.miso.vinilos


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.miso.vinilos.landing.LandingActivity
import com.miso.vinilos.main.MainActivity
import com.miso.vinilos.ui.adapters.AlbumsAdapter
import com.miso.vinilos.ui.adapters.CollectorsAdapter
import com.miso.vinilos.ui.listactivities.AlbumListActivity
import com.miso.vinilos.ui.listactivities.CollectorListActivity
import com.miso.vinilos.ui.listactivities.CreateAlbumActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class VinilosCreateInstrumentalTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup() {
        Intents.init()
        onView(withId(R.id.btnAdmin)).perform(click())
        Intents.intended(IntentMatchers.hasComponent(LandingActivity::class.java.name))
        onView(withId(R.id.act)).perform(click())
    }

    @After
    fun tearDown() {
        Intents.release()
    }


    @Test
    fun testBtnCreateAlbum() {
        onView(withId(R.id.fab_left)).perform(click())
        Intents.intended(IntentMatchers.hasComponent(CreateAlbumActivity::class.java.name))
        onView(withId(R.id.et_nombre)).perform(typeText("Muevense"), closeSoftKeyboard())
        onView(withId(R.id.et_caratula)).perform(typeText("https://akamai.sscdn.co/uploadfile/letras/albuns/e/c/7/e/2206181714392266.jpg"), closeSoftKeyboard())
        onView(withId(R.id.et_fecha_lanzamiento)).perform(typeText("Marc Anthony"), closeSoftKeyboard())
        onView(withId(R.id.et_descripcion)).perform(typeText("Marc Anthony"), closeSoftKeyboard())
        onView(withId(R.id.et_genero)).perform(typeText("Marc Anthony"), closeSoftKeyboard())
        onView(withId(R.id.et_etiqueta)).perform(typeText("Marc Anthony"), closeSoftKeyboard())
        //onView(withText("Álbum creado con éxito")).inRoot(withDecorView(not('is'(activityRule.activity.window.decorView)))).check(matches(isDisplayed()))
    }

    @Test
    fun testBtnAssociateTrack() {
        onView(withId(R.id.fab_right)).perform(click())
    }
}