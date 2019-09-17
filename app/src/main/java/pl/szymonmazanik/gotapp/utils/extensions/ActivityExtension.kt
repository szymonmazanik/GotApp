package pl.szymonmazanik.gotapp.utils.extensions

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import pl.szymonmazanik.gotapp.utils.factory.ViewModelFactory

/**
 * Gets view model within activity easily
 */
inline fun <reified T : ViewModel> FragmentActivity.getViewModel(noinline creator: (() -> T)? = null): T {
    return if (creator == null)
        ViewModelProviders.of(this).get(T::class.java)
    else
        ViewModelProviders.of(this, ViewModelFactory(creator)).get(T::class.java)
}