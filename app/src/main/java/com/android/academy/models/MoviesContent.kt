package com.android.academy.models

object MoviesContent {
	
	val movies = mutableListOf<MovieModel>()
	
	fun clear() {
		movies.clear()
	}
	
	fun addMovie(movie: MovieModel) {
		movies.add(movie)
	}
}