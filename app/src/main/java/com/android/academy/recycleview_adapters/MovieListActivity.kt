package com.android.academy.recycleview_adapters

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.academy.OnMovieClickListener
import com.android.academy.R
import com.android.academy.models.MovieModel
import kotlinx.android.synthetic.main.activity_movie_list.*

class MovieListActivity : AppCompatActivity(), OnMovieClickListener {
    private val movies: MutableList<MovieModel> = mutableListOf()
    private lateinit var moviesAdapter: MoviesViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)
        movies.addAll(
            arrayOf(
	            MovieModel(
		            getString(R.string.jurassic_world_title),
		            R.drawable.jurassic_world_fallen_kingdom,
		            getString(R.string.jurassic_world_overview)
	            ),
	            MovieModel(
		            getString(R.string.the_meg_title),
		            R.drawable.the_meg,
		            getString(R.string.the_meg_overview)
	            ),
	            MovieModel(
		            getString(R.string.the_first_purge_title),
		            R.drawable.the_first_purge,
		            getString(R.string.the_first_purge_overview)
	            ),
	            MovieModel(
		            getString(R.string.deadpool2_titile),
		            R.drawable.deadpool_2,
		            getString(R.string.deadpool2_overview)
	            ),
	            MovieModel(
		            getString(R.string.black_panther_title),
		            R.drawable.black_panther,
		            getString(R.string.black_panther_overview)
	            ),
	            MovieModel(
		            getString(R.string.ocean_eight_title),
		            R.drawable.ocean_eight,
		            getString(R.string.ocean_eight_overview)
	            ),
	            MovieModel(
		            getString(R.string.interstellar_title),
		            R.drawable.interstellar,
		            getString(R.string.interstellar_overview)
	            ),
	            MovieModel(
		            getString(R.string.thor_ragnarok_title),
		            R.drawable.thor_ragnarok,
		            getString(R.string.thor_ragnarok_overview)
	            ),
	            MovieModel(
		            getString(R.string.guardians_of_the_galaxy_title),
		            R.drawable.guardians_of_the_galaxy,
		            getString(R.string.guardians_of_the_galaxy_overview)
	            )
            )
        )
        initRecyclerView()
    }
    private fun initRecyclerView() {
        moviesList.layoutManager = LinearLayoutManager(this)
        // Create Movies Adapter
        moviesAdapter =
	        MoviesViewAdapter(
		        context = this,
		        movieClickListener = this
	        )

        // Attach Adapter to RecyclerView
        moviesList.adapter = moviesAdapter

        // Populate Adapter with data
        moviesAdapter.setData(movies)

    }
    companion object {
        fun start(context: Context) {
            val openMovieListActivity = Intent(context, MovieListActivity::class.java)
            context.startActivity(openMovieListActivity)
        }
    }


    override fun onMovieClicked(movieModel: MovieModel) {
        Toast.makeText(this, movieModel.title, Toast.LENGTH_SHORT).show()
    }
}
