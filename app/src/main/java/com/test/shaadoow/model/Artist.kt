package com.test.shaadoow.model

import com.google.gson.annotations.SerializedName

data class Artist(
    @SerializedName("success")
    val success : Boolean,
    @SerializedName("message")
    val message : List<String>,
    @SerializedName("data")
    val data : List<ArtistData>
)