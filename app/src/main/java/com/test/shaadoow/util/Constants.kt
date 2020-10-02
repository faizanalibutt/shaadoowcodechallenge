package com.test.shaadoow.util

import androidx.recyclerview.widget.DiffUtil
import com.test.shaadoow.model.ArtistData

object Constants {

    /** The base URL of the APIs */
    const val BASE_URL = "https://dev.shaadoow.com"
    const val MEDIA_ROOT_UTL = "https://shaadoow.net/"
    const val ARTISTS_END_POINT = "/api/artists/auth-less/list"
    const val FEEDS_END_POINT = "/api/posts/recommended_for_you"

    const val LINK_PAGE = "page"
    const val LINK_PAGE_LIMIT = "limit"
    const val PAGE_HINT: Int = 1
    const val PAGE_SIZE: Int = 5
    const val KEY: String = "key"
    const val VALUE: String = "value"

    val DIFF_CALL: DiffUtil.ItemCallback<ArtistData> = object : DiffUtil.ItemCallback<ArtistData>() {
        override fun areItemsTheSame(oldItem: ArtistData, newItem: ArtistData): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: ArtistData, newItem: ArtistData): Boolean {
            return oldItem == newItem
        }
    }
}