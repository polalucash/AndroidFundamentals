package com.android.academy.my_viewpager

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
import com.android.academy.models.MoviesContent.movies


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
		val fragments = movies.associateWith { movieModel -> ScreenSlidePageFragment.newInstance(movieModel) }
		// The pager adapter, which provides the pages to the view pager widget.
		val pagerAdapter = ScreenSlidePagerAdapter(supportFragmentManager, fragments)
		pager.adapter = pagerAdapter
		val item = intent.getParcelableExtra<MovieModel?>(MovieModelTAG )
		item?.let { setPagerCurrentItem(it) }
	}
	
	
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
		pager.currentItem = (pager.adapter as ScreenSlidePagerAdapter).fragments.keys.indexOf(movieModel)
	}
}

/**
 * A simple pager adapter that represents ScreenSlidePageFragment objects, in
 * sequence.
 */
class ScreenSlidePagerAdapter(	manager: FragmentManager, val fragments: Map<MovieModel,ScreenSlidePageFragment>):
	FragmentStatePagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
	override fun getItem(position: Int): Fragment = fragments.values.elementAt(position)
	override fun getCount(): Int = fragments.size
}