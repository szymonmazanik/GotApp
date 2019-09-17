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


class CharactersDataSource(private val compositeDisposable: CompositeDisposable) :
    PageKeyedDataSource<Long, Character>() {

    val errorMessage = MutableLiveData<Event<Int?>>()
    private val api = ApiOfIceAndFire.getService()
    override fun loadInitial(
        params: LoadInitialParams<Long>,
        callback: LoadInitialCallback<Long, Character>
    ) {
        compositeDisposable += api.getCharacters(FIRST_PAGE, params.requestedLoadSize)
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onSuccess = {
                    it.forEach { character -> character.assignIdFromUrl() }
                    callback.onResult(it, null, FIRST_PAGE + 2)
                },
                onError = { errorMessage.postValue(Event(R.string.error_initial)) }
            )
    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Long, Character>) {
        compositeDisposable += api.getCharacters(
            params.key,
            params.requestedLoadSize
        ).subscribeOn(Schedulers.io())
            .doOnSubscribe { errorMessage.postValue(null) }
            .subscribeBy(
                onSuccess = {
                    it.forEach { character -> character.assignIdFromUrl() }
                    val key = params.key + 1
                    callback.onResult(it, key)
                }
//                ,
//                onError = { errorMessage.postValue(R.string.error_after) }
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