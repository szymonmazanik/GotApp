package pl.szymonmazanik.gotapp

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import pl.szymonmazanik.gotapp.ui.CharacterListActivity

class UiTest {

    @Rule
    @JvmField
    val rule: ActivityTestRule<CharacterListActivity> = ActivityTestRule(CharacterListActivity::class.java)

    @Test
    fun scrollToBottomOfRecyclerView() {
//        activity = rule.launchActivity()
//        onView(withId(R.id.character_list))
//            .perform(RecyclerViewActions.scrollToPosition(activity.recyclerView.getAdapter().getItemCount() - 1))
        Thread.sleep(3000)
        onView(withId(R.id.character_list)).perform(ScrollToBottomAction())
    }
}

class ScrollToBottomAction : ViewAction {
    override fun getDescription(): String {
        return "scroll RecyclerView to bottom"
    }

    override fun getConstraints(): Matcher<View> {
        return allOf<View>(isAssignableFrom(RecyclerView::class.java), isDisplayed())
    }

    override fun perform(uiController: UiController?, view: View?) {
        val recyclerView = view as RecyclerView
        val itemCount = recyclerView.adapter?.itemCount
        val position = itemCount?.minus(1) ?: 0
        recyclerView.scrollToPosition(position)
        uiController?.loopMainThreadUntilIdle()
    }
}