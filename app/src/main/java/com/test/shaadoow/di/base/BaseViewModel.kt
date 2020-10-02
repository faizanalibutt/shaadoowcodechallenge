package com.test.shaadoow.di.base

import androidx.lifecycle.ViewModel
import com.test.shaadoow.di.component.DaggerViewModelInjector
import com.test.shaadoow.di.component.ViewModelInjector
import com.test.shaadoow.di.module.NetworkModule
import com.test.shaadoow.ui.viewmodel.ArtistDataViewModel
import com.test.shaadoow.ui.viewmodel.ArtistViewModel
import com.test.shaadoow.ui.viewmodel.NetworkStateViewModel

abstract class BaseViewModel : ViewModel() {

    private val injector: ViewModelInjector = DaggerViewModelInjector
        .builder()
        .networkModule(NetworkModule)
        .build()

    init {
        inject()
    }

    /**
     * Injects the required dependencies
     */
    private fun inject() {
        when (this) {
            is ArtistViewModel -> injector.inject(this)
            is NetworkStateViewModel -> injector.inject(this)
            is ArtistDataViewModel -> injector.inject(this)
        }
    }
}