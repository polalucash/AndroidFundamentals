package com.android.academy.models

import android.os.Parcelable
import com.android.academy.networking.MovieResult
import com.android.academy.networking.NetworkingConstants
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieModel constructor (
	val movieId: Int,
	val title: String,
	val posterUrl: String,
	val overview: String,
	val coverUrl: String,
	val releaseDate: String,
	val trailerUrl:String?
):Parcelable {
	constructor(movieResult: MovieResult)
			: this(
		movieId = movieResult.id,
		title = movieResult.title,
		posterUrl = NetworkingConstants.POSTER_BASE_URL + movieResult.poster_path,
		coverUrl = NetworkingConstants.COVER_BASE_URL + movieResult.backdrop_path,
		overview = movieResult.overview,
		releaseDate = movieResult.release_date,
		trailerUrl = null
	)
}
