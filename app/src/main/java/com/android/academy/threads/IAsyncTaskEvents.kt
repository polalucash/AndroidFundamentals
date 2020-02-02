package com.android.academy.threads

interface IAsyncTaskEvents {
	fun onPreExecute()
	fun onPostExecute()
	fun onProgressUpdate( value: String)
	
	fun createAsyncTask()
	fun startAsyncTask(counterStart:Int=0)
	fun cancelAsyncTask()
}