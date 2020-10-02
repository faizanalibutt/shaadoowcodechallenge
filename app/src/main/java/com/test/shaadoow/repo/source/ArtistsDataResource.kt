package com.test.shaadoow.repo.source

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.test.shaadoow.model.Artist
import com.test.shaadoow.model.ArtistData
import com.test.shaadoow.network.ShaadoowApi
import com.test.shaadoow.util.NetworkState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executor
import javax.inject.Singleton

@Singleton
class ArtistsDataResource(
    private val shaadoowApi: ShaadoowApi,
    private val retryExecutor: Executor,
    private val subscription: CompositeDisposable
) : PageKeyedDataSource<Long, ArtistData>() {

    // keep a function reference for the retry event
    private var retry: (() -> Any)? = null
    val networkState = MutableLiveData<NetworkState>()
    private var nextKey: Long = 0

    fun retryAllFailed() {
        val prevRetry = retry
        retry = null
        prevRetry?.let {
            retryExecutor.execute{
                it.invoke()
            }
        }
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Long, ArtistData>) {}

    override fun loadInitial(params: LoadInitialParams<Long>, callback: LoadInitialCallback<Long, ArtistData>) {
        subscription.add(shaadoowApi.getArtists(1, params.requestedLoadSize.toLong())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                networkState.postValue(NetworkState.LOADING)
            }
            .doOnTerminate {
                retry = null
                networkState.postValue(NetworkState.LOADED)
            }
            .subscribe(
                {
                        artists -> onRetrieveArtistsListSuccess(artists, callback)
                },
                {
                    retry = {
                        loadInitial(params, callback)
                    }
                    val errors = NetworkState.error(it.message ?: "unknown error")
                    networkState.postValue(errors)
                }
            ))
    }

    private fun onRetrieveArtistsListSuccess(
        artist: Artist?,
        callback: LoadInitialCallback<Long, ArtistData>
    ) {
        try {
            val artistsList = artist!!.data
            callback.onResult(artistsList, null, 1)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Long, ArtistData>) {

        subscription.add(
            shaadoowApi.getArtists(params.key, params.requestedLoadSize.toLong())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { networkState.postValue(NetworkState.LOADING) }
                .doOnTerminate { networkState.postValue(NetworkState.LOADED) }
                .subscribe(
                    // Add result
                    { result -> onRetrievePostListSuccessAfter(result, callback) },
                    {
                        retry = {
                            loadAfter(params, callback)
                        }
                        networkState.postValue(
                            NetworkState.error(it.message ?: "unknown err")
                        )
                    }
                )
        )

    }

    private fun onRetrievePostListSuccessAfter(
        artist: Artist?,
        callback: LoadCallback<Long, ArtistData>
    ) {
        val artistsList = artist?.data
        artistsList?.size?.let {
            if (it > 0 && artist.success) {
                retry = null
                try {
                callback.onResult(artistsList, nextKey++)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

}