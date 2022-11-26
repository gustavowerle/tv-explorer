package com.tv.shows.api

import com.tv.shows.model.Show
import com.tv.shows.model.TvShow
import javax.inject.Inject

class ShowRest @Inject constructor(
    private val showService: ShowService
) {

    suspend fun fetchShows(page: Int): List<Show> {
        return showService.fetchShows(page)
    }

    suspend fun search(query: String): List<TvShow> {
        return showService.search(query)
    }
}
