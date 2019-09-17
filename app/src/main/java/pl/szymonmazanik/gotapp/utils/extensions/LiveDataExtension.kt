package pl.szymonmazanik.gotapp.utils.extensions

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import pl.szymonmazanik.gotapp.model.entity.Character

/**
 * Finds [Character] in [LiveData]
 * If we can't access [LiveData]'s value or id is not found we return null and show error on view
 * @param id of [Character]
 * @return Nullable [Character].
 */
fun LiveData<PagedList<Character>>.getCharacterById(id: Long) =
    value?.find { it.id == id }