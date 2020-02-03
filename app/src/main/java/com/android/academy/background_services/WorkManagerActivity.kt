package com.android.academy.background_services

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Switch
import androidx.lifecycle.Observer
import androidx.work.*
import com.android.academy.R
import com.android.academy.background_services.HardJobWorker.Companion.Progress
import kotlinx.android.synthetic.main.activity_work_manager.*
import kotlinx.android.synthetic.main.activity_work_manager.progress_pct_txt
import java.util.*

class WorkManagerActivity : AppCompatActivity(),View.OnClickListener {
	private lateinit var setRequiredNetworkType: Switch
	private lateinit var setRequiresCharging: Switch
	private lateinit var setRequiresBatteryNotLow: Switch
	private var workRequestId: UUID? = null
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_work_manager)
		worker_enqueue_btn.setOnClickListener(this)
		worker_cancel_btn.setOnClickListener(this)
		
		workRequestId = savedInstanceState?.getString("workRequestId")?.let {
			UUID.fromString(it)
		}
	}
	
	override fun onClick(v: View?) {
		when (v?.id) {
			R.id.worker_enqueue_btn -> {
				if (workRequestId == null) queueWork()
			}
			R.id.worker_cancel_btn -> {
				workRequestId?.let {
					cancelWork()
				}
			}
		}
	}
	
	private fun cancelWork() {
		WorkManager.getInstance(applicationContext)
			.cancelWorkById(workRequestId!!)
		workRequestId = null
	}
	
	private fun queueWork() {
		val constraints = Constraints.Builder()
			.setRequiredNetworkType(
				if (network_switch.isChecked)
					NetworkType.CONNECTED
				else NetworkType.NOT_REQUIRED
			)
			.setRequiresCharging(charge_switch.isChecked)
			.setRequiresBatteryNotLow(battery_not_low_switch.isChecked)
			.build()
		
		val workRequest = OneTimeWorkRequestBuilder<HardJobWorker>()
			.setConstraints(constraints)
			.build()
			.apply {
				WorkManager
					.getInstance(this@WorkManagerActivity)
					.enqueue(this)
			}
		workRequestId = workRequest.id
		WorkManager.getInstance(applicationContext)
			// requestId is the WorkRequest id
			.getWorkInfoByIdLiveData(workRequestId!!)
			.observe(this, Observer { workInfo: WorkInfo? ->
				if (workInfo != null) {
					val progress = workInfo.progress.getInt(Progress, -1)
					if (progress >= 0) {
						if (progress == 100) {
							progress_pct_txt.text = getString(R.string.done)
						}
						if (progress >= 0)
							progress_pct_txt.text =
								String.format(Locale.getDefault(), "%d%%", progress)
					}
				}
			})
	}
	
	override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
		outState.putBoolean("setRequiredNetworkType", setRequiredNetworkType.isChecked)
		outState.putBoolean("setRequiresCharging", setRequiresCharging.isChecked)
		outState.putBoolean("setRequiresBatteryNotLow", setRequiresBatteryNotLow.isChecked)
		workRequestId?.let { outState.putString("workRequestId", workRequestId.toString())}
		super.onSaveInstanceState(outState, outPersistentState)
	}
	
	override fun onDestroy() {
		if(workRequestId!=null){
			cancelWork()
		}
		super.onDestroy()
	}
}