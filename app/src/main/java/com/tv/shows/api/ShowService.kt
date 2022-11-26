package com.tv.shows.api

import com.tv.shows.model.Show
import com.tv.shows.model.TvShow
import retrofit2.http.GET
import retrofit2.http.Query

interface ShowService {

    @GET("shows")
    suspend fun fetchShows(@Query("page") page: Int): List<Show>

    @GET("search/shows")
    suspend fun search(@Query("q") query: String): List<TvShow>
}
