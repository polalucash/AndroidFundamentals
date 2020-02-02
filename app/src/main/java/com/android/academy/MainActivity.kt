package com.android.academy

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.android.academy.background_services.BGServiceActivity
import com.android.academy.fragment_viewpager_by_lec.MainFragmentsActivity
import com.android.academy.threads.AsyncTaskActivity
import com.android.academy.threads.ThreadHandlerActivity
import com.android.academy.my_viewpager.MainHWViewPageActivity
import com.android.academy.recyclerview_adapters_by_lec.MovieListActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
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
	
	override fun onCreateOptionsMenu(menu: Menu): Boolean {
		// Inflate the menu to use in the action bar
		val inflater = MenuInflater(this)
		inflater.inflate(R.menu.options_menu, menu)
		return super.onCreateOptionsMenu(menu)
	}
	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		// Handle presses on the action bar menu items
		when (item.itemId) {
			R.id.activity_async_task ->	{
				startActivity(Intent(this@MainActivity, AsyncTaskActivity::class.java))
			}
			R.id.activity_thread_handler ->{
				startActivity(Intent(this@MainActivity, ThreadHandlerActivity::class.java))
			}
			R.id.activity_bgservice ->{
				startActivity(Intent(this@MainActivity, BGServiceActivity::class.java))
			}
		}
		return super.onOptionsItemSelected(item)
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
