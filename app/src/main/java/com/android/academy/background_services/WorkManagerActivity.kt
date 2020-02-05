package com.android.academy.background_services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.android.academy.R
import kotlinx.android.synthetic.main.activity_work_manager.*
import java.util.*

class WorkManagerActivity : AppCompatActivity(),View.OnClickListener {
	private var workId: UUID? = null
	private val backgroundProgressReceiver = WorkerBackgroundProgressReceiver()
	
	companion object {
		
		const val PROGRESS_UPDATE_ACTION: String = "PROGRESS_UPDATE_ACTION"
		const val PROGRESS_VALUE_KEY: String = "PROGRESS_VALUE_KEY"
		const val SERVICE_STATUS: String = "SERVICE_STATUS"
	}
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_work_manager)
		worker_enqueue_btn.setOnClickListener(this)
		worker_cancel_btn.setOnClickListener(this)
	}
	
	override fun onStart() {
		super.onStart()
		registerReceiver(backgroundProgressReceiver, IntentFilter(PROGRESS_UPDATE_ACTION))
	}
	
	override fun onClick(v: View?) {
		when (v?.id) {
			R.id.worker_enqueue_btn -> {
				if (workId == null) queueWork()
			}
			R.id.worker_cancel_btn -> {
				workId?.let {
					cancelWork()
				}
			}
		}
	}
	
	private fun cancelWork() {
		workId?.let {
			WorkManager
				.getInstance(this)
				.cancelWorkById(it)
			workId = null
		}
	}
	
	private fun queueWork() {
		if (workId == null) {
			val constraints = buildConstraints()
			OneTimeWorkRequestBuilder<HardJobWorker>()
				.setConstraints(constraints)
				.build()
				.apply {
					workId = this.id
					WorkManager
						.getInstance(this@WorkManagerActivity)
						.enqueue(this)
				}
		}
	}
	
	private fun buildConstraints(): Constraints {
		return Constraints.Builder()
			.setRequiredNetworkType(
				if (network_switch.isChecked)
					NetworkType.CONNECTED
				else NetworkType.NOT_REQUIRED
			)
			.setRequiresCharging(charge_switch.isChecked)
			.setRequiresBatteryNotLow(battery_not_low_switch.isChecked)
			.build()
	}
	
	
	override fun onStop() {
		unregisterReceiver(backgroundProgressReceiver)
		super.onStop()
	}
	override fun onDestroy() {
		if (workId != null) {
			cancelWork()
		}
		super.onDestroy()
	}
	
	inner class WorkerBackgroundProgressReceiver : BroadcastReceiver() {
		override fun onReceive(context: Context, intent: Intent) {
			val progress = intent.getIntExtra(PROGRESS_VALUE_KEY, -1)
			val text: String
			if (progress >= 0) {
				if (progress > 99) {
					text = context.getString(R.string.worker_work_done)
					workId = null
				}
				else {
					text = "$progress %"
				}
				progress_pct_txt.text = text
			}
			intent.getStringExtra(SERVICE_STATUS)?.let { msg->Toast.makeText(context,msg,Toast.LENGTH_SHORT).show() }
		}
	}
	
}