package com.tv.shows.ui.helper

import android.widget.ImageView
import com.bumptech.glide.Glide

/*  This class was created to wrap Glide and make it easy to switch to another
 *  framework in the future if needed.
 */
class ImageLoader {

    private lateinit var url: String

    fun load(url: String): ImageLoader {
        this.url = url
        return this
    }

    fun into(imageView: ImageView) {
        Glide.with(imageView.context)
            .load(url)
            .centerCrop()
            .into(imageView)
    }
}
