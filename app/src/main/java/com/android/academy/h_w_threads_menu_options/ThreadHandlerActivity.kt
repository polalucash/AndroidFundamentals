package com.android.academy.h_w_threads_menu_options

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import com.android.academy.R

class ThreadHandlerActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.fragment_counter)
		supportActionBar?.hide()
	}
	
	companion object{
		fun start(context: Context){
			val intent = Intent(context, ThreadHandlerActivity::class.java)
			context.startActivity(intent)
		}
	}
}