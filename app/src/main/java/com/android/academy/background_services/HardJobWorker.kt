package com.android.academy.background_services

import android.content.Context
import android.content.Intent
import android.os.SystemClock
import androidx.work.*

class HardJobWorker(context: Context, workerParams:WorkerParameters): Worker(context.applicationContext, workerParams) {
	private var isWorkerStopped: Boolean = false
	override fun doWork(): Result {
		showToast("Starting Work")
		var i = 0
		while (i <= 100 && !isWorkerStopped) {
			SystemClock.sleep(100)
			val broadcastIntent = Intent(WorkManagerActivity.PROGRESS_UPDATE_ACTION)
			broadcastIntent.putExtra(WorkManagerActivity.PROGRESS_VALUE_KEY, i)
			setProgressAsync(workDataOf(Companion.Progress to i))
			applicationContext.sendBroadcast(broadcastIntent)
			i++
		}
		return if (i < 100) {
			showToast("Work failure")
			Result.failure()
		} else {
			showToast("Finishing Work")
			Result.success()
		}
	}
	
	private fun showToast(msg: String) {
		val intent = Intent(BGServiceActivity.PROGRESS_UPDATE_ACTION)
		intent.putExtra(BGServiceActivity.SERVICE_STATUS, msg)
		applicationContext.sendBroadcast(intent)
	}
	override fun onStopped() {
		isWorkerStopped = true
	}
	companion object {
		const val Progress = "Progress"
	}
}