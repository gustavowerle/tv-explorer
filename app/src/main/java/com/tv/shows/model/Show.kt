package com.tv.shows.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Show(
    val id: Int,
    val name: String,
    val genres: List<String>?,
    val status: String?,
    val rating: Rating?,
    val summary: String?,
    val image: Image?
) : Parcelable
