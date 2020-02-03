package com.android.academy.background_services

import android.content.Context
import android.content.Intent
import android.os.SystemClock
import androidx.work.*
import kotlinx.coroutines.delay

class HardJobWorker(appContext: Context, workerParams:WorkerParameters): Worker(appContext, workerParams) {
	
	override fun doWork(): Result {
		var i = 0
		while (i <= 100) {
			SystemClock.sleep(100)
			val intent = Intent(BGServiceActivity.PROGRESS_UPDATE_ACTION)
			intent.putExtra(BGServiceActivity.PROGRESS_VALUE_KEY, i)
			setProgressAsync(workDataOf(Companion.Progress to i))
			i++
		}
		
		return Result.success()
	}
	
	companion object {
		const val Progress = "Progress"
	}
}