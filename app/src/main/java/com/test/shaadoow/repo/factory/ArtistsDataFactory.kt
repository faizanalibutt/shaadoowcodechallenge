package com.test.shaadoow.repo.factory

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.test.shaadoow.model.ArtistData
import com.test.shaadoow.network.ShaadoowApi
import com.test.shaadoow.repo.source.ArtistsDataResource
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.Executor


class ArtistsDataFactory(private val shaadoowApi: ShaadoowApi, private val retryExecutor: Executor,
                         private val subscription: CompositeDisposable) : DataSource.Factory<Long, ArtistData>() {
    val mutableLiveArtistsData = MutableLiveData<ArtistsDataResource>()
    override fun create(): DataSource<Long, ArtistData> {
        val artistsDataResource = ArtistsDataResource(shaadoowApi, retryExecutor, subscription)
        mutableLiveArtistsData.postValue(artistsDataResource)
        return artistsDataResource
    }
}