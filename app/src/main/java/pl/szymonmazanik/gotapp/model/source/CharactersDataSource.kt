package pl.szymonmazanik.gotapp.model.source

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import pl.szymonmazanik.gotapp.R
import pl.szymonmazanik.gotapp.model.entity.Character
import pl.szymonmazanik.gotapp.model.network.endpoints.ApiOfIceAndFire
import pl.szymonmazanik.gotapp.utils.Event
import pl.szymonmazanik.gotapp.utils.extensions.assignIdFromUrl
import timber.log.Timber

/**
 * This [PageKeyedDataSource] is responsible for delivering data to view model
 */
class CharactersDataSource(private val compositeDisposable: CompositeDisposable) :
    PageKeyedDataSource<Long, Character>() {

    private val api = ApiOfIceAndFire.getService()

    /**
     * Error message resource Id
     */
    val errorMessage = MutableLiveData<Event<Int?>>()

    /**
     * Loads initial data
     */
    override fun loadInitial(
        params: LoadInitialParams<Long>,
        callback: LoadInitialCallback<Long, Character>
    ) {
        compositeDisposable += api.getCharacters(FIRST_PAGE, params.requestedLoadSize)
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onSuccess = {
                    // API does not provide item's id. We need to extract id from given URL
                    it.forEach { character -> character.assignIdFromUrl() }
                    callback.onResult(it, null, FIRST_PAGE + 2)
                    Timber.d("Loaded initial characters: $it")
                },
                onError = {
                    errorMessage.postValue(Event(R.string.error_initial))
                    Timber.e(it, "Initial load error")
                }
            )
    }

    /**
     * Loads data when requested using params.key as current page index
     */
    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Long, Character>) {
        compositeDisposable += api.getCharacters(
            params.key,
            params.requestedLoadSize
        ).subscribeOn(Schedulers.io())
            .doOnSubscribe { errorMessage.postValue(null) }
            .subscribeBy(
                onSuccess = {
                    // API does not provide item's id. We need to extract id from given URL
                    it.forEach { character -> character.assignIdFromUrl() }
                    val key = params.key + 1
                    callback.onResult(it, key)
                    Timber.d("Loaded after characters: $it")
                },
                onError = {
                    errorMessage.postValue(Event(R.string.error_after))
                    Timber.e(it, "After load error")
                }
            )
    }

    /**
     * This is not necessary in our case as our data doesn't change. It's useful in cases where
     * the data changes and we need to fetch our list starting from the middle.
     */
    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Long, Character>) {}

    companion object {
        const val FIRST_PAGE = 1L
        const val PAGE_SIZE = 20
    }
}