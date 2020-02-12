package com.android.academy.networking

import com.android.academy.BuildConfig

object NetworkingConstants {
	private const val BASE_URL = "https://api.themoviedb.org"
	
	const val COVER_BASE_URL = "https://image.tmdb.org/t/p/w780"
	const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w342"
	const val YOUTUBE_BASE_URL = "https://www.youtube.com/watch?v="
	
	const val BASE_API_URL = "$BASE_URL/3/"
	private const val POPULAR = "movie/popular"
	
	const val MOVIE_ID_KEY = "movie_id"
	private const val VIDEOS = "movie/{$MOVIE_ID_KEY}/videos"
	
	//Created a secret using the instructions in https://github.com/codepath/android_guides/wiki/Storing-Secret-Keys-in-Android
	private const val apiKey = BuildConfig.TMDB_API_KEY
	private const val keyQuery = "?api_key=$apiKey"
	const val POPULAR_QUERY_PATH = POPULAR + keyQuery
	const val VIDEOS_QUERY_PATH = VIDEOS + keyQuery
	
}