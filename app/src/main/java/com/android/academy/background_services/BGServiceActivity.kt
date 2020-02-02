package com.android.academy.background_services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.android.academy.R

class BGServiceActivity : AppCompatActivity(), View.OnClickListener {
	private var backgroundProgressReceiver: BackgroundProgressReceiver? = null
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_bgservice)
		//TODO find reference to progress TextView
		//TODO Add listeners for two buttons
	}
	
	override fun onClick(v: View?) {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}
	public override fun onPause() {
		backgroundProgressReceiver?.let {
			unregisterReceiver(backgroundProgressReceiver)
		}
		super.onPause()
	}
	
	private fun subscribeForProgressUpdates() {
		if (backgroundProgressReceiver == null) {
			backgroundProgressReceiver = BackgroundProgressReceiver()
		}
		val progressUpdateActionFilter = IntentFilter(PROGRESS_UPDATE_ACTION)
		registerReceiver(backgroundProgressReceiver, progressUpdateActionFilter)
	}
	
	companion object {
		const val PROGRESS_UPDATE_ACTION: String = "PROGRESS_UPDATE_ACTION"
		const val PROGRESS_VALUE_KEY: String = "PROGRESS_VALUE_KEY"
		const val SERVICE_STATUS: String = "SERVICE_STATUS"
	}
	
	inner class BackgroundProgressReceiver : BroadcastReceiver() {
		override fun onReceive(context: Context, intent: Intent) {
			val progress = intent.getIntExtra(PROGRESS_VALUE_KEY, -1)
			//TODO: parse progress value and update the mProgressValue TextView with relevant value.
			
		}
	}
	
}