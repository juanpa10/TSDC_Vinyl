package com.miso.vinilos


import androidx.test.espresso.Espresso.onData
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
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.Matchers.allOf
import org.hamcrest.core.IsNot.not

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
        onView(withId(R.id.et_nombre)).perform(typeText("Valio la Pena"), closeSoftKeyboard())
        onView(withId(R.id.et_caratula)).perform(typeText("https://www.lamusica.com.co/cdn/shop/products/R-7014851-1431711913-3333_jpeg.jpg"), closeSoftKeyboard())
        onView(withId(R.id.et_fecha_lanzamiento)).perform(typeText("Marc Anthony"), closeSoftKeyboard())
        onView(withId(R.id.et_descripcion)).perform(typeText("Álbum de Salsa de Marc Anthony con 8 éxitos musicales, se encuentran canción como Ahora quien, valió la pena, entre otras"), closeSoftKeyboard())

        onView(withId(R.id.et_genero)).perform(click())
        onData(allOf(`is`(instanceOf(String::class.java)), `is`("Salsa"))).perform(click())

        onView(withId(R.id.et_etiqueta)).perform(click())
        onData(allOf(`is`(instanceOf(String::class.java)), `is`("Sony Music"))).perform(click())

        onView(withId(R.id.btnUsuario2)).perform(click())

        activityRule.scenario.onActivity { activity ->
            onView(withText("Álbum creado exitosamente")).inRoot(withDecorView(not(`is`(activity.window.decorView)))).check(matches(isDisplayed()))
        }
    }

    @Test
    fun testBtnAssociateTrack() {
        onView(withId(R.id.fab_right)).perform(click())
    }
}