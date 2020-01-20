package com.android.academy.h_w_threads_menu_options

import android.os.AsyncTask
import android.os.SystemClock
import android.widget.TextView
import java.lang.ref.WeakReference

class CounterAsyncTask(private val iAsyncTaskEvents: IAsyncTaskEvents):
	AsyncTask<Int, String, Unit>() {
	
	override fun doInBackground(vararg params: Int?) {
		val length = when (params.size) {
			1 -> params[0]!!
			else -> 10
		}
		
		for (i in 0..length) {
			SystemClock.sleep(500)
			if (isCancelled){
				return
			}
			publishProgress(i.toString())
		}
	}
	
	override fun onPreExecute() {
		super.onPreExecute()
		iAsyncTaskEvents.onPreExecute()
	}
	override fun onPostExecute(result: Unit?) {
		super.onPostExecute(result)
		iAsyncTaskEvents.onPostExecute()
	}
	
	override fun onProgressUpdate(vararg values: String?) {
		values[0]?.let{iAsyncTaskEvents.onProgressUpdate(it)}
	}
}