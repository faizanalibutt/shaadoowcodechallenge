package com.test.shaadoow.network

import com.test.shaadoow.model.Artist
import com.test.shaadoow.util.Constants
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * The interface which provides methods to get result of webservices
 */
interface ShaadoowApi {
    /**
     * Get the list of ARTISTS from API
     */
    @GET(Constants.ARTISTS_END_POINT)
    fun getArtists(@Query(Constants.LINK_PAGE) page: Long,
                   @Query(Constants.LINK_PAGE_LIMIT) pageSize: Long) : Observable<Artist>
}