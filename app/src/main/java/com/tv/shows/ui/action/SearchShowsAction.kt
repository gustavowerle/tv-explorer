package com.tv.shows.ui.action

import android.util.Log
import com.tv.shows.api.ShowRest
import com.tv.shows.model.TvShow
import javax.inject.Inject

class SearchShowsAction @Inject constructor(
    private val showRest: ShowRest
) {

    suspend fun execute(query: String): List<TvShow>? = try {
        showRest.search(query)
    } catch (e: Exception) {
        Log.e(javaClass.name, e.message, e)
        null
    }
}
