package pl.szymonmazanik.gotapp

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import org.hamcrest.core.IsNot.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import pl.szymonmazanik.gotapp.ui.CharacterListActivity
import pl.szymonmazanik.gotapp.ui.CharacterListAdapter
import timber.log.Timber

class UiTest {

    @get:Rule
    var rule: ActivityTestRule<CharacterListActivity> =
        ActivityTestRule(CharacterListActivity::class.java)

    private lateinit var instrumentationContext: Context

    private var isTablet = false

    @Before
    fun setup() {
        instrumentationContext = InstrumentationRegistry.getInstrumentation().targetContext
        isTablet = instrumentationContext.resources.getBoolean(R.bool.isTablet)
    }

    /**
     * Clicks through every Recycler Item
     * This test could have been done better but I was running out of time.
     * I decided to leave it here anyway because it serves it's purpose
     * and on stable internet connection I managed to test the app with it.
     */
    @Test
    fun openAllRecyclerItems() {
        //Wait for the data to load
        //Could have been done idling resources
        Thread.sleep(3000)

        val adapter = rule.activity.findViewById<RecyclerView>(R.id.character_list).adapter!!
        var itemsCount = adapter.itemCount
        var lastOpenedItemIndex = 0
        //Check if last opened item is the last item on list
        while (lastOpenedItemIndex < itemsCount) {
                onView(withId(R.id.character_list)).perform(
                    RecyclerViewActions.actionOnItemAtPosition<CharacterListAdapter.ViewHolder>(
                        lastOpenedItemIndex,
                        click()
                    )
                )
                lastOpenedItemIndex++

                //After each click we assign new value to itemsCount in case new data came in
                itemsCount = adapter.itemCount

                //Check if id has been set properly
                onView(withId(R.id.character_id)).check(matches(not(withText(""))))

                //Press back only on handset
                if (!isTablet) pressBack()

                Timber.d("Items: $itemsCount Item: $lastOpenedItemIndex")
        }
    }
}