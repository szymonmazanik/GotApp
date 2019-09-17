package pl.szymonmazanik.gotapp

import org.junit.Test
import pl.szymonmazanik.gotapp.model.network.endpoints.ApiOfIceAndFire
import pl.szymonmazanik.gotapp.utils.extensions.assignIdFromUrl

class DataUnitTest {

    /**
     * From API docs we know that all characters must have URL.
     * We check if all received items' urls are not empty
     */
    @Test
    fun testApi() {
        ApiOfIceAndFire.getService().getCharacters(1, 20)
            .test()
            .assertValue { it.any() && it.all { character -> character.url.isNotEmpty() } }
            .dispose()
    }

    /**
     * Checks if Id is properly assigned from URL
     * Uses [Regex] under the hood
     */
    @Test
    fun testExtractingIdFromUrlByRegex() {
        ApiOfIceAndFire.getService().getCharacters(1, 20)
            .test()
            .assertValue {
                it.forEach { character -> character.assignIdFromUrl() }
                it.all { character -> character.id > 0 }
            }
    }
}