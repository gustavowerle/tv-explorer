package com.tv.shows.helper

import com.tv.shows.model.Show
import com.tv.shows.model.TvShow
import java.util.Random

class ApiMock {

    private val random = Random()

    fun getShows(): List<Show> = mutableListOf<Show>().apply {
        for (i in 0..3) {
            add(
                Show(
                    random.nextInt(),
                    "TV Show $i",
                    listOf("Drama"),
                    "Running",
                    null,
                    "TV show $i summary",
                    null
                )
            )
        }
    }

    fun getTvShows(): List<TvShow> = getShows().map {
        TvShow(
            null,
            it
        )
    }
}
