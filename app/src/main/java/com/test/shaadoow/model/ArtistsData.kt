package com.test.shaadoow.model
import com.google.gson.annotations.SerializedName

data class ArtistData (
    @SerializedName("artist_id")
    val artist_id : Int,
    @SerializedName("name")
    val name : String,
    @SerializedName("names")
    val names : String,
    @SerializedName("category")
    val category : Int,
    @SerializedName("description")
    val description : String,
    @SerializedName("region_id")
    val region_id : Int,
    @SerializedName("cover_img_url")
    val cover_img_url : String,
    @SerializedName("profile_img_url")
    val profile_img_url : String,
    @SerializedName("published")
    val published : Int,
    @SerializedName("rank")
    val rank : Int,
    @SerializedName("created_at")
    val created_at : String,
    @SerializedName("updated_at")
    val updated_at : String,
    @SerializedName("deleted_at")
    val deleted_at : String,
    @SerializedName("profile_id")
    val profile_id : Int,
    @SerializedName("slug")
    val slug : String,
    @SerializedName("followers")
    val followers : Long
)