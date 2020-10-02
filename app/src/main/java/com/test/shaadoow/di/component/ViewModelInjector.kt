package com.test.shaadoow.di.component

import com.test.shaadoow.di.module.NetworkModule
import com.test.shaadoow.ui.viewmodel.ArtistDataViewModel
import com.test.shaadoow.ui.viewmodel.ArtistViewModel
import com.test.shaadoow.ui.viewmodel.NetworkStateViewModel
import dagger.Component
import javax.inject.Singleton

/**
 * Component providing inject() methods for presenters.
 */
@Singleton
@Component(modules = [(NetworkModule::class)])
interface ViewModelInjector {
    /**
     * Injects required dependencies into the specified ArtistViewModel.
     * @param artistViewModel In which to inject the dependencies
     */
    fun inject(artistViewModel: ArtistViewModel)

    /**
     * Injects required dependencies into the specified ArtistDataViewModel.
     * @param artistDataViewModel In which to inject the dependencies
     */
    fun inject(artistDataViewModel: ArtistDataViewModel)

    /**
     * Injects required dependencies into the specified CarViewModel.
     * @param networkStateViewModel In which to inject the dependencies
     */
    fun inject(networkStateViewModel: NetworkStateViewModel)
    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector
        fun networkModule(networkModule: NetworkModule): Builder
    }
}