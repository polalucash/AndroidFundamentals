package com.android.academy.networking

import com.android.academy.networking.NetworkingConstants.BASE_API_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RestClient {
	val moviesService: TheMovieDBService by lazy {
		val retrofit = Retrofit
			.Builder()
			.baseUrl(BASE_API_URL)
			.addConverterFactory(GsonConverterFactory.create())
			.build()
		
		retrofit.create(TheMovieDBService::class.java)
	}
}