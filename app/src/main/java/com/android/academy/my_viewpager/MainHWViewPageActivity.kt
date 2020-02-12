package com.android.academy.my_viewpager

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.academy.OnMovieClickListener
import com.android.academy.R
import com.android.academy.models.MovieModel
import com.android.academy.models.MoviesContent.movies
import com.android.academy.networking.MovieListResult
import com.android.academy.networking.RestClient
import com.android.academy.recyclerview_adapters_by_lec.MoviesViewAdapter
import kotlinx.android.synthetic.main.activity_movie_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainHWViewPageActivity : AppCompatActivity(), OnMovieClickListener {
	
	private lateinit var moviesAdapter: MoviesViewAdapter
	private lateinit var recyclerView: RecyclerView
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_movie_list)
		main_progress.visibility = View.GONE
		initRecyclerView()
		loadMovies()
	}
	
	private fun initRecyclerView() {
		recyclerView = moviesList
		recyclerView.layoutManager = LinearLayoutManager(this)
		// Create Movies Adapter and  Attach Adapter to RecyclerView
		moviesAdapter = MoviesViewAdapter(this, this)
		recyclerView.adapter = moviesAdapter
		// Populate Adapter with data
		moviesAdapter.setData(movies)
		
	}
	
	private fun loadMovies() {
		RestClient.moviesService.getPopularMovies().enqueue(object : Callback<MovieListResult> {
			override fun onFailure(call: Call<MovieListResult>, t: Throwable) {
				main_progress.visibility = View.GONE
				Toast.makeText(
					this@MainHWViewPageActivity,
					"something went wrong",
					Toast.LENGTH_SHORT
				).show()
			}
			
			override fun onResponse(
				call: Call<MovieListResult>,
				response: Response<MovieListResult>
			) {
				main_progress.visibility = View.GONE
				movies.clear()
				movies.addAll(response.body()?.results?.map { MovieModel(it) }!!)
			}
		})
	}
	
	override fun onMovieClicked(movieModel: MovieModel) {
		ScreenSlidePagerActivity.start(this@MainHWViewPageActivity,  movieModel)
	}
	
	companion object {
		fun start(context: Context) {
			val intent = Intent(context, MainHWViewPageActivity::class.java)
			context.startActivity(intent)
		}
	}
}
