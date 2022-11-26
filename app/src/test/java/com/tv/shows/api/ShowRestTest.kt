package com.tv.shows.api

import com.tv.shows.helper.ApiMock
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

private const val DEFAULT_PAGE = 0
private const val DEFAULT_SEARCH_QUERY = "test"

internal class ShowRestTest {

    private lateinit var showRest: ShowRest
    private val showService: ShowService = mockk()
    private val apiMock: ApiMock = ApiMock()

    @Before
    fun setUp() {
        showRest = ShowRest(showService)
    }

    @Test
    fun `when fetchShows expect shows list`() = runBlocking {
        val shows = apiMock.getShows()
        coEvery { showService.fetchShows(any()) } returns shows

        val obtained = showRest.fetchShows(DEFAULT_PAGE)

        assertEquals(shows, obtained)
        coVerify { showService.fetchShows(DEFAULT_PAGE) }
        confirmVerified(showService)
    }

    @Test
    fun `when search expect filtered show list`() = runBlocking {
        val shows = apiMock.getTvShows()
        coEvery { showService.search(any()) } returns shows

        val obtained = showRest.search(DEFAULT_SEARCH_QUERY)

        assertEquals(shows, obtained)
        coVerify { showService.search(DEFAULT_SEARCH_QUERY) }
        confirmVerified(showService)
    }
}
