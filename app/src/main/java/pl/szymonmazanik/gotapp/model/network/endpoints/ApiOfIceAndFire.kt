package pl.szymonmazanik.gotapp.model.network.endpoints

import io.reactivex.Single
import pl.szymonmazanik.gotapp.model.entity.Character
import pl.szymonmazanik.gotapp.utils.BASE_URL
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiOfIceAndFire {

    /**
     * Get the Characters from the API
     */
    @GET("characters?")
    fun getCharacters(
        @Query("page") page: Long,
        @Query("pageSize") pageSize: Int
    ): Single<List<Character>>

    companion object {
        /**
         * Provides [ApiOfIceAndFire] instance
         */
        fun getService(): ApiOfIceAndFire {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
            return retrofit.create(ApiOfIceAndFire::class.java)
        }
    }
}