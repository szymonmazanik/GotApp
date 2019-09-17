package pl.szymonmazanik.gotapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import io.reactivex.disposables.CompositeDisposable
import pl.szymonmazanik.gotapp.model.entity.Character
import pl.szymonmazanik.gotapp.model.source.CharactersDataSource
import pl.szymonmazanik.gotapp.utils.Event
import pl.szymonmazanik.gotapp.utils.factory.CharactersDataSourceFactory

class CharacterViewModel : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val sourceFactory by lazy {
        CharactersDataSourceFactory(compositeDisposable)
    }
    private val pagedListConfig by lazy {
        PagedList.Config.Builder()
            .setPageSize(CharactersDataSource.PAGE_SIZE)
            .setInitialLoadSizeHint(CharactersDataSource.PAGE_SIZE * 2)
            .setEnablePlaceholders(false)
            .build()
    }

    /**
     * Indicates error message resource id
     * Data is gathered from [CharactersDataSource] using [Transformations]
     * We take advantage of Kotlin's null safety and allow this value to be null.
     */
    val errorMessage by lazy {
        Transformations.switchMap(
            sourceFactory.usersDataSourceLiveData,
            CharactersDataSource::errorMessage
        )
    }

    /**
     * Opens details using [Event]
     */
    private val _openDetailsEvent = MutableLiveData<Event<Long>>()
    val openDetailsEvent: LiveData<Event<Long>> = _openDetailsEvent

    /**
     * Contains [PagedList] of [Character]s
     * Could have been done with with [RxPagedListBuilder] if we prefer consuming Observable in fragment
     */
    val characters = LivePagedListBuilder<Long, Character>(sourceFactory, pagedListConfig).build()

    /**
     * Clears [CompositeDisposable]
     */
    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    /**
     * Called by Data Binding.
     */
    fun openDetails(id: Long) {
        _openDetailsEvent.postValue(Event(id))
    }
}