package com.android.academy.background_services

import android.app.IntentService
import android.content.Intent
import android.os.SystemClock
import com.android.academy.R

class HardJobIntentService: IntentService(TAG) {
	private var isDestroyed: Boolean = false
	
	companion object {
		private const val TAG = "HardJobIntentService"
	}
	
	override fun onHandleIntent(intent: Intent?) {
		showToast(getString(R.string.starting_intent_service_msg))
		var i = 0
		while (i <= 100 && !isDestroyed) {
			SystemClock.sleep(100)
			val broadcastIntent = Intent(BGServiceActivity.PROGRESS_UPDATE_ACTION)
			broadcastIntent.putExtra(BGServiceActivity.PROGRESS_VALUE_KEY, i)
			sendBroadcast(broadcastIntent)
			i++
		}
		showToast(getString(R.string.finishing_intent_service_msg))
	}
	
	override fun onDestroy() {
		isDestroyed = true
		super.onDestroy()
	}
	
	private fun showToast(msg: String) {
		val intent = Intent(BGServiceActivity.PROGRESS_UPDATE_ACTION)
		intent.putExtra(BGServiceActivity.SERVICE_STATUS, msg)
		sendBroadcast(intent)
	}
}