package com.android.academy.models

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieModel(
	val title: String,
	@DrawableRes val imageRes: Int,
	val overview: String,
	val trailerUrl: String?="",
	val releaseDate: String?=""
):Parcelable
