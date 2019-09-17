package pl.szymonmazanik.gotapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jraska.livedata.test
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import pl.szymonmazanik.gotapp.ui.CharacterViewModel

class CharacterViewModelUnitTest {

    @get:Rule val testRule = InstantTaskExecutorRule()

    private lateinit var viewModel: CharacterViewModel

    @Before
    fun setUp() {
        viewModel = CharacterViewModel()
    }

    @Test
    fun testCharacters() {
        viewModel.characters.test()
            .awaitValue()
            .assertHasValue()
            .assertValue {
                it.all { character -> character.id > 0 }
            }
    }
}