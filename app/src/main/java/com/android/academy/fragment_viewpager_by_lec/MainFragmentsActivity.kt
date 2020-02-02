package com.android.academy.fragment_viewpager_by_lec

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.android.academy.OnMovieClickListener
import com.android.academy.R
import com.android.academy.models.MovieModel

class MainFragmentsActivity : AppCompatActivity(), OnMovieClickListener {
	private var isTablet: Boolean = false
	
	private lateinit var fragmentMovies: List<MovieDetailsFragment>
	private var viewPager: ViewPager? = null
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_fragments_main)
		
		viewPager = findViewById(R.id.fragment_pager)
		isTablet = viewPager != null
		
		val moviesFragment: MoviesFragment =
			if(savedInstanceState == null) {
				MoviesFragment()
					.apply {
						supportFragmentManager
							.beginTransaction()
							.add(R.id.fragment_frame, this, MoviesFragment.Tag)
							.commit()
					}
			}
			else{
				supportFragmentManager
					.findFragmentByTag(MoviesFragment.Tag) as MoviesFragment
			}
		fragmentMovies = moviesFragment.loadMovies().map{
			MovieDetailsFragment.newInstance(it)
		}
		val adapter = SimplePagerAdapter(
			supportFragmentManager,
			fragmentMovies
		)
		
		viewPager?.adapter = adapter
	}
	
	override fun onMovieClicked(movieModel: MovieModel) {
		val movieDetailsFragment: MovieDetailsFragment = MovieDetailsFragment.newInstance(movieModel)

		supportFragmentManager
			.beginTransaction()
			.apply {
				if (!isTablet) {
					addToBackStack(null)
					replace(R.id.fragment_frame, movieDetailsFragment)
				}
				else {
					replace(R.id.fragment_pager,  movieDetailsFragment)
				}
			}.commit()
	}
	
	companion object {
		fun start(context: Context) {
			val openMainFragmentsActivity = Intent(context, MainFragmentsActivity::class.java)
			context.startActivity(openMainFragmentsActivity)
		}
	}
	
}