package pl.szymonmazanik.gotapp.injection.component

import dagger.Component
import pl.szymonmazanik.gotapp.injection.modules.OfIceAncFireModule
import javax.inject.Singleton


///**
// * Component providing inject() methods for presenters.
// */
//@Singleton
//@Component(modules = [OfIceAncFireModule::class])
//interface NetworkLayerComponent {
//    /**
//     * Injects required dependencies into the specified SearchViewModel.
//     * @param networkLayer NetworkLayer in which to inject the dependencies
//     */
//    fun inject(networkLayer: NetworkLayer)
//
//    @Component.Builder
//    interface Builder {
//        fun build(): NetworkLayerComponent
//
//        fun ofIceAncFireModule(ofIceAndFireModule: OfIceAncFireModule): Builder
//    }
//}