package com.tv.shows.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Rating(
    val average: Float?
) : Parcelable
