package com.test.shaadoow.ui.viewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.test.shaadoow.di.base.BaseViewModel
import com.test.shaadoow.util.NetworkState
import com.test.shaadoow.util.NetworkState.Status

class NetworkStateViewModel : BaseViewModel() {

    private val networkStateTitle : MutableLiveData<String> = MutableLiveData()
    private val networkStateVisibility: MutableLiveData<Int> = MutableLiveData()
    private val loadingVisibility: MutableLiveData<Int> = MutableLiveData()

    fun bindView(networkState: NetworkState?) {
        // when we are up up up and away. network is working good
        if (networkState != null && networkState.status === Status.RUNNING) {
            loadingVisibility.value = View.VISIBLE
        } else {
            loadingVisibility.value = View.GONE
        }
        // when we are offline or network down.
        if (networkState != null && networkState.status === Status.FAILED) {
            networkStateTitle.value = networkState.msg
            networkStateVisibility.value = View.VISIBLE
        } else {
            networkStateVisibility.value = View.GONE
        }
    }

    fun getNetworkStateTitle(): MutableLiveData<String> {
        return networkStateTitle
    }

    fun getLoadingVisibility(): MutableLiveData<Int> {
        return loadingVisibility
    }

    fun getNetworkStateVisibility(): MutableLiveData<Int> {
        return networkStateVisibility
    }

}