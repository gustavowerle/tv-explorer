package com.tv.shows.ui.action

import android.util.Log
import com.tv.shows.api.ShowRest
import com.tv.shows.model.Show
import javax.inject.Inject

class FetchShowsAction @Inject constructor(
    private val showRest: ShowRest
) {

    suspend fun execute(page: Int): List<Show>? = try {
        showRest.fetchShows(page)
    } catch (e: Exception) {
        Log.e(javaClass.name, e.message, e)
        null
    }
}
