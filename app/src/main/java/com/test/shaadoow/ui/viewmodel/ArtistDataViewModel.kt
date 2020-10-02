package com.test.shaadoow.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.test.shaadoow.di.base.BaseViewModel
import com.test.shaadoow.model.ArtistData

class ArtistDataViewModel : BaseViewModel() {

    private val artistTitle = MutableLiveData<String>()
    private val artistImage = MutableLiveData<String>()
    private val artistFollowers = MutableLiveData<Long>()

    fun bind(artist: ArtistData, res: Int) {
        artistTitle.value = artist.description
        artistImage.value = artist.profile_img_url
        artistFollowers.value = artist.followers
    }

    fun getArtistTitle(): MutableLiveData<String> {
        return artistTitle
    }

    fun getArtistImage(): MutableLiveData<String> {
        return artistImage
    }

    fun getArtistFollowers(): MutableLiveData<Long> {
        return artistFollowers
    }

}