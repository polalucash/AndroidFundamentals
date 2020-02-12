package com.android.academy.networking

import com.google.gson.annotations.SerializedName


data class MovieListResult (
	
	@SerializedName("page") val page : Int,
	@SerializedName("total_results") val total_results : Int,
	@SerializedName("total_pages") val total_pages : Int,
	@SerializedName("results") val results : List<MovieResult>
)

data class MovieResult (
	
	@SerializedName("popularity") val popularity : Double,
	@SerializedName("vote_count") val vote_count : Int,
	@SerializedName("video") val video : Boolean,
	@SerializedName("poster_path") val  poster_path : String,
	@SerializedName("id") val id : Int,
	@SerializedName("adult") val adult : Boolean,
	@SerializedName("backdrop_path") val backdrop_path : String,
	@SerializedName("original_language") val original_language : String,
	@SerializedName("original_title") val original_title : String,
	@SerializedName("genre_ids") val genre_ids : List<Int>,
	@SerializedName("title") val title : String,
	@SerializedName("vote_average") val vote_average : Double,
	@SerializedName("overview") val overview : String,
	@SerializedName("release_date") val release_date : String
)

data class MovieVideosList (
	
	@SerializedName("id") val id : Int,
	@SerializedName("results") val results : List<MovieVideoResult>
)


data class MovieVideoResult (
	
	@SerializedName("id") val id : String,
	@SerializedName("iso_639_1") val iso_639_1 : String,
	@SerializedName("iso_3166_1") val iso_3166_1 : String,
	@SerializedName("key") val key : String,
	@SerializedName("name") val name : String,
	@SerializedName("site") val site : String,
	@SerializedName("size") val size : Int,
	@SerializedName("type") val type : String
)