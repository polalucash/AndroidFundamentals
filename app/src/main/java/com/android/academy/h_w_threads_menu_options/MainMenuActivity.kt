package com.android.academy.h_w_threads_menu_options

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.android.academy.R
import kotlinx.android.synthetic.main.activity_thread_handler.*

class MainMenuActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
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
				startActivity(Intent(this@MainMenuActivity, AsyncTaskActivity::class.java))
				
			}
			R.id.activity_thread_handler ->{
				startActivity(Intent(this@MainMenuActivity, ThreadHandlerActivity::class.java))
				
			}
		}
		return super.onOptionsItemSelected(item)
	}
}