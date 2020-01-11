package com.android.academy.h_w_viewpager

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.android.academy.R
import com.android.academy.models.MovieModel


class ScreenSlidePagerActivity: AppCompatActivity() {
	
	/**
	 * The pager widget, which handles animation and allows swiping horizontally to access previous
	 * and next wizard steps.
	 */
	companion object{
		const val MovieModelTAG: String = "MovieModel"
		
		fun start(context: Context, movieModel: MovieModel?){
			val intent = Intent(context, ScreenSlidePagerActivity::class.java)
			
			if (movieModel != null){
				intent.putExtra(MovieModelTAG, movieModel)
			}
			context.startActivity(intent)
		}
	}
	private lateinit var pager: ViewPager
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_screen_slide)
		pager = findViewById(R.id.pager)
		val movieList:ArrayList<MovieModel> = loadMovies()
		val fragments = movieList.associateWith { movieModel -> ScreenSlidePageFragment.newInstance(movieModel) }
		// The pager adapter, which provides the pages to the view pager widget.
		val pagerAdapter = ScreenSlidePagerAdapter(supportFragmentManager, fragments)
		pager.adapter = pagerAdapter
		val item = intent.getParcelableExtra<MovieModel?>(MovieModelTAG )
		item?.let { setPagerCurrentItem(it) }
	}
	
	private fun loadMovies(): ArrayList<MovieModel> = arrayListOf(
		MovieModel(
			getString(R.string.jurassic_world_title),
			R.drawable.jurassic_world_fallen_kingdom,
			getString(R.string.jurassic_world_overview),
			"https://www.youtube.com/watch?v=vn9mMeWcgoM",
			releaseDate = "2018-06-06"
			
		),
		MovieModel(
			getString(R.string.the_meg_title),
			R.drawable.the_meg,
			getString(R.string.the_meg_overview),
			"https://www.youtube.com/watch?v=bsLk0NPRFAc",
			releaseDate = "2018-08-09"
		),
		MovieModel(
			getString(R.string.the_first_purge_title),
			R.drawable.the_first_purge,
			getString(R.string.the_first_purge_overview),
			trailerUrl = "https://www.youtube.com/watch?v=UL29y0ah92w",
			releaseDate = "2018-07-04"
		),
		MovieModel(
			getString(R.string.deadpool2_titile),
			R.drawable.deadpool_2,
			getString(R.string.deadpool2_overview),
			trailerUrl = "https://www.youtube.com/watch?v=20bpjtCbCz0",
			releaseDate = "2018-05-15"
		),
		MovieModel(
			getString(R.string.black_panther_title),
			R.drawable.black_panther,
			getString(R.string.black_panther_overview),
			trailerUrl = "https://www.youtube.com/watch?v=xjDjIWPwcPU",
			releaseDate = "2018-02-13"
		),
		MovieModel(
			getString(R.string.ocean_eight_title),
			R.drawable.ocean_eight,
			getString(R.string.ocean_eight_overview),
			trailerUrl = "https://www.youtube.com/watch?v=n5LoVcVsiSQ",
			releaseDate = "2018-06-07"
		),
		MovieModel(
			getString(R.string.interstellar_title),
			R.drawable.interstellar,
			getString(R.string.interstellar_overview),
			trailerUrl = "https://www.youtube.com/watch?v=zSWdZVtXT7E",
			releaseDate = "2014-11-05"
		),
		MovieModel(
			getString(R.string.thor_ragnarok_title),
			R.drawable.thor_ragnarok,
			getString(R.string.thor_ragnarok_overview),
			trailerUrl = "https://www.youtube.com/watch?v=ue80QwXMRHg",
			releaseDate = "2017-10-25"
		),
		MovieModel(
			getString(R.string.guardians_of_the_galaxy_title),
			R.drawable.guardians_of_the_galaxy,
			getString(R.string.guardians_of_the_galaxy_overview),
			trailerUrl = "https://www.youtube.com/watch?v=2LIQ2-PZBC8",
			releaseDate = "2014-07-30"
		)
	)
//
//	override fun onBackPressed() {
//		if (pager.currentItem == 0) {
//			// If the user is currently looking at the first step, allow the system to handle the
//			// Back button. This calls finish() on this activity and pops the back stack.
//			super.onBackPressed()
//		} else {
//			// Otherwise, select the previous step.
//			pager.currentItem = pager.currentItem - 1
//		}
//	}
	
	private fun setPagerCurrentItem(movieModel: MovieModel) {
		pager.currentItem =
			(pager.adapter as ScreenSlidePagerAdapter).fragments.keys.indexOf(movieModel)
	}
}

/**
 * A simple pager adapter that represents ScreenSlidePageFragment objects, in
 * sequence.
 */
class ScreenSlidePagerAdapter(
	manager: FragmentManager, val fragments: Map<MovieModel,ScreenSlidePageFragment>
): FragmentStatePagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
	override fun getItem(position: Int): Fragment = fragments.values.elementAt(position)
	override fun getCount(): Int = fragments.size
}