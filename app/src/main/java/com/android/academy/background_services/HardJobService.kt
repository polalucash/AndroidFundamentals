package com.android.academy.background_services


import android.app.Service
import android.content.Intent
import android.os.*
import com.android.academy.R

class HardJobService: Service() {
	private lateinit var handlerThread: HandlerThread
	private lateinit var serviceLooper: Looper
	private lateinit var serviceHandler: ServiceHandler
	private var isDestroyed = false
	
	companion object {
		private const val TAG = "HardJobService"
	}
	
	override fun onBind(intent: Intent): IBinder? {
		return null
	}
	
	override fun onCreate() {
		// To avoid cpu-blocking, we create a background handler to run our service
		handlerThread = HandlerThread(TAG, Process.THREAD_PRIORITY_BACKGROUND)
		// start the new handler thread
		handlerThread.start()
		serviceLooper = handlerThread.looper
		// start the service using the background handler
		serviceHandler = ServiceHandler(serviceLooper)
	}
	
	override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
		isDestroyed = false
		showToast(getString(R.string.starting_hardjob_service_msg))
		// call a new service handler. The service ID can be used to identify the service
		val message = serviceHandler.obtainMessage()
		message.arg1 = startId
		serviceHandler.sendMessage(message)
		return START_STICKY
	}
	
	private fun showToast(msg: String) {
		val intent = Intent(BGServiceActivity.PROGRESS_UPDATE_ACTION)
		intent.putExtra(BGServiceActivity.SERVICE_STATUS, msg)
		sendBroadcast(intent)
	}
	
	override fun onDestroy() {
		isDestroyed = true
		handlerThread.quit()
		super.onDestroy()
	}
	
	private inner class ServiceHandler(looper: Looper) : Handler(looper) {
		override fun handleMessage(msg: Message) {
			// Well calling serviceHandler.sendMessage(message); from onStartCommand this method will be called. Add your cpu-blocking activity here
			var i = 0
			while (i <= 100 && !isDestroyed) {
				SystemClock.sleep(100)
				val intent = Intent(BGServiceActivity.PROGRESS_UPDATE_ACTION)
				intent.putExtra(BGServiceActivity.PROGRESS_VALUE_KEY, i)
				sendBroadcast(intent)
				i++
			}
			showToast(getString(R.string.finishing_hardjob_service_msg, msg.arg1))
			// the msg.arg1 is the startId used in the onStartCommand,
			// so we can track the running service here.
			stopSelf(msg.arg1)
		}
	}
}