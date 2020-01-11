package com.android.academy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.academy.avengers_movie.MovieActivity
import com.android.academy.fragment_viewpager.MainFragmentsActivity
import com.android.academy.h_w_viewpager.MainHWViewPageActivity
import com.android.academy.h_w_viewpager.ScreenSlidePagerActivity
import com.android.academy.models.MovieModel
import com.android.academy.recycleview_adapters.MovieListActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
	    avengers_btn.setOnClickListener {
            navigateToMovieActivity()
        }
        movie_list_btn.setOnClickListener {
            navigateToMovieListActivity()
        }
	    fragment_holder_btn.setOnClickListener {
		    navigateFragmentsActivity()
	    }
	    fragment_hw_view_page_btn.setOnClickListener {
		    navigateHWViewPageActivity()
	    }
    }

    private fun navigateToMovieActivity() {
        MovieActivity.start(this)
    }
	
	private fun navigateToMovieListActivity() {
		MovieListActivity.start(this)
	}
	private fun navigateFragmentsActivity() {
		MainFragmentsActivity.start(this)
	}
	private fun navigateHWViewPageActivity() {
		MainHWViewPageActivity.start(this)
	}
}
