package pl.szymonmazanik.gotapp.injection.modules
//
//import dagger.Module
//import dagger.Provides
//import dagger.Reusable
//import io.reactivex.schedulers.Schedulers
//import pl.szymonmazanik.gotapp.model.network.endpoints.ApiOfIceAndFire
//import pl.szymonmazanik.gotapp.utils.BASE_URL
//import retrofit2.Retrofit
//import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
//import retrofit2.converter.moshi.MoshiConverterFactory
//import javax.inject.Singleton
//
//@Module
//object OfIceAncFireModule {
//    /**
//     * Provides the api service implementation.
//     * @param retrofit the Retrofit object used to instantiate the service
//     * @return the api service implementation.
//     */
//    @Provides
//    @Reusable
//    @JvmStatic
//    internal fun provideApiOfIceAndFire(retrofit: Retrofit): ApiOfIceAndFire {
//        return retrofit.create(ApiOfIceAndFire::class.java)
//    }
//
//    /**
//     * Provides the Retrofit object.
//     * @return the Retrofit object
//     */
//    @Provides
//    @Reusable
//    @JvmStatic
//    internal fun provideRetrofitInterface(): Retrofit {
//        return Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(MoshiConverterFactory.create())
//            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
//            .build()
//    }
//}