package com.android.academy.h_w_threads_menu_options

interface IAsyncTaskEvents {
	fun onPreExecute()
	fun onPostExecute()
	fun onProgressUpdate( value: String)
	
	fun createAsyncTask()
	fun startAsyncTask(counterStart:Int=0)
	fun cancelAsyncTask()
}