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
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

private const val DEFAULT_PAGE = 0

internal class FetchShowsActionTest {

    private lateinit var fetchShowsAction: FetchShowsAction
    private val showRest: ShowRest = mockk()
    private val apiMock: ApiMock = ApiMock()

    @Before
    fun setUp() {
        mockkStatic(Log::class)
        every { Log.e(any(), any(), any()) } returns 0

        fetchShowsAction = FetchShowsAction(showRest)
    }

    @After
    fun tearDown() {
        unmockkStatic(Log::class)
    }

    @Test
    fun `when execute expect shows list`() = runBlocking {
        val shows = apiMock.getShows()
        coEvery { showRest.fetchShows(any()) } returns shows

        val obtained = fetchShowsAction.execute(DEFAULT_PAGE)

        assertEquals(shows, obtained)
        coVerify { showRest.fetchShows(DEFAULT_PAGE) }
        confirmVerified(showRest)
    }

    @Test
    fun `when showRest throws an exception expect null`() = runBlocking {
        val exception = Exception()
        coEvery { showRest.fetchShows(any()) } throws exception

        val obtained = fetchShowsAction.execute(DEFAULT_PAGE)

        assertEquals(null, obtained)
        coVerify {
            showRest.fetchShows(DEFAULT_PAGE)
            Log.e(fetchShowsAction.javaClass.name, exception.message, exception)
        }
        confirmVerified(showRest)
    }
}
