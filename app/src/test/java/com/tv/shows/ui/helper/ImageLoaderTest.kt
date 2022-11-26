package com.tv.shows.ui.helper

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import io.mockk.verify
import org.junit.After
import org.junit.Before
import org.junit.Test

private const val URL = "http://www.test.com/image"

internal class ImageLoaderTest {

    private lateinit var imageLoader: ImageLoader
    private val imageView: ImageView = mockk(relaxed = true)
    private val requestManager: RequestManager = mockk()
    private val requestBuilder: RequestBuilder<Drawable> = mockk()

    @Before
    fun setUp() {
        mockkStatic(Glide::class)
        every { Glide.with(any<Context>()) } returns requestManager
        every { requestManager.load(any<String>()) } returns requestBuilder
        every { requestBuilder.centerCrop() } returns requestBuilder
        every { requestBuilder.into(any()) } returns mockk()

        imageLoader = ImageLoader()
    }

    @After
    fun tearDown() {
        unmockkStatic(Glide::class)
    }

    @Test
    @SuppressLint("CheckResult")
    fun `when load an image expect Glide was called with correct params`() {
        imageLoader.load(URL).into(imageView)

        verify {
            Glide.with(imageView.context)
            requestManager.load(URL)
            requestBuilder.centerCrop()
            requestBuilder.into(imageView)
        }
        confirmVerified(imageView, requestManager, requestBuilder)
    }
}
