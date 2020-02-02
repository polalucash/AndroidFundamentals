package com.android.academy.threads

import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.os.SystemClock
import androidx.annotation.MainThread
import androidx.annotation.UiThread
import androidx.annotation.WorkerThread

class MySimpleAsyncTask (private val iAsyncTaskEvents:IAsyncTaskEvents) {
	var isCancelled: Boolean = false
	private val threadTag = "SavtaThread"
	private val uiHandler: Handler = Handler(Looper.getMainLooper())
	private var handlerThread: HandlerThread
	private var myHandler: Handler
	init {
		handlerThread = HandlerThread(threadTag)
		handlerThread.start()
		myHandler = Handler(handlerThread.looper)
	}
	@MainThread
	 fun onPreExecute() {
		iAsyncTaskEvents.onPreExecute()
	}
	
	@WorkerThread
	fun doInBackground(vararg params: Int?){
		val start = when (params.size) {
			1 -> params[0]!!
			else -> 0
		}
		
		for (i in start..10) {
			SystemClock.sleep(500)
			if (isCancelled){
				return
			}
			publishProgress(i.toString())
		}
	}
	@MainThread
	 fun onPostExecute() {
		 iAsyncTaskEvents.onPostExecute()
		 handlerThread.quitSafely()
	}
	
	fun execute(params:Int?) {
		runOnUiThread(Runnable {
			onPreExecute()
			myHandler.post {
				doInBackground(params).run {
					runOnUiThread(Runnable {
						onPostExecute()
					})
				}
			}
		})
	}
	
	fun publishProgress(vararg progress: String) {
		uiHandler.post { onProgressUpdate(*progress) }
	}
	
	@UiThread
	fun onProgressUpdate(vararg values: String) {
		values[0].let{iAsyncTaskEvents.onProgressUpdate(it)}
	}
	
	fun cancel(mayInterruptIfRunning: Boolean) {
		isCancelled = true
		if (mayInterruptIfRunning)
			handlerThread.interrupt()
		handlerThread.quitSafely()
	}
	
	private fun runOnUiThread(runnable: Runnable): Boolean {
		return uiHandler.post(runnable)
	}
}