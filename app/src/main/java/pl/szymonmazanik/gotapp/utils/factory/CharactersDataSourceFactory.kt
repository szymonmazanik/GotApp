package pl.szymonmazanik.gotapp.utils.factory

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import io.reactivex.disposables.CompositeDisposable
import pl.szymonmazanik.gotapp.model.entity.Character
import pl.szymonmazanik.gotapp.model.source.CharactersDataSource

/**
 * Factory class for [CharactersDataSource]
 */
class CharactersDataSourceFactory(private val compositeDisposable: CompositeDisposable) :
    DataSource.Factory<Long, Character>() {

    val usersDataSourceLiveData = MutableLiveData<CharactersDataSource>()

    override fun create(): DataSource<Long, Character> {
        val usersDataSource = CharactersDataSource(compositeDisposable)
        usersDataSourceLiveData.postValue(usersDataSource)
        return usersDataSource
    }
}