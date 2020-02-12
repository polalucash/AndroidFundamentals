package com.android.academy.networking

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface TheMovieDBService {
	
	@GET(NetworkingConstants.POPULAR_QUERY_PATH)
	abstract fun getPopularMovies(): Call<MovieListResult>
	@GET(NetworkingConstants.VIDEOS_QUERY_PATH)
	abstract fun getMovieTrailer(@Path("movie_id")  movieId:Int  ): Call<MovieVideosList>
}