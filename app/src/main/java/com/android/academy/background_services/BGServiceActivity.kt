package com.android.academy.background_services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Toast
import com.android.academy.R
import kotlinx.android.synthetic.main.activity_bgservice.*
import java.util.*

class BGServiceActivity : AppCompatActivity(), View.OnClickListener {
	private var backgroundProgressReceiver = BackgroundProgressReceiver()
	internal var isServiceStarted: Boolean = false
	internal var isIntentServiceStarted: Boolean = false
	internal var toast: Toast? = null
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_bgservice)
		start_intentservice_btn.setOnClickListener(this)
		start_service_btn.setOnClickListener(this)
		savedInstanceState?.let {
			isServiceStarted = it.getBoolean("isServiceStarted")
			isIntentServiceStarted = it.getBoolean("isIntentServiceStarted")
		}
	}
	
	override fun onClick(v: View?) {
		when(v?.id){
			R.id.start_service_btn-> {
				if (isIntentServiceStarted) {
					val intent = Intent(this, HardJobIntentService::class.java)
					stopService(intent)
					isIntentServiceStarted = false
				}
				if (!isServiceStarted) {
					isServiceStarted = true
					val intent = Intent(this, HardJobService::class.java)
					startService(intent)
				}
			}
			R.id.start_intentservice_btn->{
				if (isServiceStarted){
					val intent = Intent(this, HardJobService::class.java)
					stopService(intent)
					isServiceStarted = false
				}
				if (!isIntentServiceStarted){
					isIntentServiceStarted = true
					val intent = Intent(this, HardJobIntentService::class.java)
					startService(intent)
				}
			}
		}
	}
	
	override fun onResume() {
		super.onResume()
		subscribeForProgressUpdates()
	}
	
	public override fun onPause() {
		unregisterReceiver(backgroundProgressReceiver)
		super.onPause()
	}
	
	private fun subscribeForProgressUpdates() {
		backgroundProgressReceiver = BackgroundProgressReceiver()
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
			if (progress >= 0) {
				if (progress == 100) {
					isIntentServiceStarted = false
					isServiceStarted = false
					progress_pct_txt.text = getString(R.string.done)
				}
				
				if (progress >= 0)
					progress_pct_txt.text = String.format(Locale.getDefault(), "%d%%", progress)
			}
			
			intent.getStringExtra(SERVICE_STATUS)?.let {
				toast?.cancel()
				toast = Toast.makeText(context, it, Toast.LENGTH_SHORT)
				toast?.show()
			}
		}
	}
	
	override fun onSaveInstanceState(outState: Bundle) {
		outState.putBoolean("isServiceStarted", isServiceStarted)
		outState.putBoolean("isIntentServiceStarted", isIntentServiceStarted)
		super.onSaveInstanceState(outState)
	}
	
	override fun onDestroy() {
		if (isFinishing) {
			if (isServiceStarted) {
				stopService(Intent(this, HardJobService::class.java))
				isServiceStarted = false
			}
			if (isIntentServiceStarted) {
				stopService(Intent(this, HardJobIntentService::class.java))
				isIntentServiceStarted = false
			}
		}
		toast?.cancel()
		super.onDestroy()
	}
}