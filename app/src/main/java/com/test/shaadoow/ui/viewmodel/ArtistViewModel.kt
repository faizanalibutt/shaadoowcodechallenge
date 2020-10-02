package com.test.shaadoow.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.test.shaadoow.di.base.BaseViewModel
import com.test.shaadoow.model.ArtistData
import com.test.shaadoow.network.ShaadoowApi
import com.test.shaadoow.repo.Listing
import com.test.shaadoow.repo.factory.ArtistsDataFactory
import com.test.shaadoow.util.Constants
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Inject

class ArtistViewModel : BaseViewModel()  {

    @Inject
    lateinit var shaadoowApi: ShaadoowApi

    var networkExecutor: Executor? = null
    val subscription = CompositeDisposable()
    val repoResult = MutableLiveData<Listing<ArtistData>>()
    val artists = Transformations.switchMap(repoResult) { it.pagedList }
    val networkState = Transformations.switchMap(repoResult) { it.networkState }

    init {
        repoResult.postValue(artistsData())
    }

    private fun artistsData(): Listing<ArtistData> {

        networkExecutor = Executors.newFixedThreadPool(5)

        val artistsDataFactory = ArtistsDataFactory(shaadoowApi, networkExecutor!!, subscription)

        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(1)
            .setPrefetchDistance(1)
            .setPageSize(Constants.PAGE_SIZE)
            .build()

        val livePagedList = LivePagedListBuilder(artistsDataFactory, pagedListConfig)
            .setFetchExecutor(networkExecutor!!)
            .build()

        return Listing(
            pagedList = livePagedList,
            networkState = Transformations.
            switchMap(artistsDataFactory.mutableLiveArtistsData) {
                it.networkState
            },
            retry = {
                artistsDataFactory.mutableLiveArtistsData.value?.retryAllFailed()
            }
        )

    }

    fun retry() {
        val listing = repoResult.value
        listing?.retry?.invoke()
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

}