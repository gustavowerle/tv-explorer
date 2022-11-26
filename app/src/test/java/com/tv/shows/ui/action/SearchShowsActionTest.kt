package com.tv.shows.ui.action

import android.util.Log
import com.tv.shows.api.ShowRest
import com.tv.shows.helper.ApiMock
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

private const val DEFAULT_SEARCH_QUERY = "test"

internal class SearchShowsActionTest {

    private lateinit var searchShowsAction: SearchShowsAction
    private val showRest: ShowRest = mockk()
    private val apiMock: ApiMock = ApiMock()

    @Before
    fun setUp() {
        mockkStatic(Log::class)
        every { Log.e(any(), any(), any()) } returns 0

        searchShowsAction = SearchShowsAction(showRest)
    }

    @After
    fun tearDown() {
        unmockkStatic(Log::class)
    }

    @Test
    fun `when execute expect filtered shows list`() = runBlocking {
        val shows = apiMock.getTvShows()
        coEvery { showRest.search(any()) } returns shows

        val obtained = searchShowsAction.execute(DEFAULT_SEARCH_QUERY)

        Assert.assertEquals(shows, obtained)
        coVerify { showRest.search(DEFAULT_SEARCH_QUERY) }
        confirmVerified(showRest)
    }

    @Test
    fun `when showRest throws an exception expect null`() = runBlocking {
        val exception = Exception()
        coEvery { showRest.search(any()) } throws exception

        val obtained = searchShowsAction.execute(DEFAULT_SEARCH_QUERY)

        Assert.assertEquals(null, obtained)
        coVerify {
            showRest.search(DEFAULT_SEARCH_QUERY)
            Log.e(searchShowsAction.javaClass.name, exception.message, exception)
        }
        confirmVerified(showRest)
    }
}
