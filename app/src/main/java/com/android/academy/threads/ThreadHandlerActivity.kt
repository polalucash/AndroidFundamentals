package com.android.academy.threads

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.academy.R
import kotlinx.android.synthetic.main.fragment_counter.*

class ThreadHandlerActivity : AppCompatActivity(), IAsyncTaskEvents {
	private var myAsyncTask: MySimpleAsyncTask? = null
	companion object {
		const val Status: String = "STATUS"
	}
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.fragment_counter)
		
		cancel_btn.setOnClickListener { cancelAsyncTask() }
		start_btn.setOnClickListener { startAsyncTask() }
		create_btn.setOnClickListener { createAsyncTask() }
		
		counter_status_text.text =
			if (savedInstanceState != null && savedInstanceState.containsKey(Status)) {
				val counter = savedInstanceState.getInt(Status)
				createAsyncTask()
				startAsyncTask(counter)
				counter.toString()
			} else {
				getString(R.string.thread_handler_activity)
			}
	}
	
	override fun onSaveInstanceState(outState: Bundle) {
		counter_status_text.text.toString().toIntOrNull()?.let{	outState.putInt(Status,it)}
		super.onSaveInstanceState(outState)
	}
	/***
	 * // IAsyncTaskEvent's methods - start:
	 */
	override fun createAsyncTask() {
		myAsyncTask = MySimpleAsyncTask(this)
		Toast.makeText(this, getString(R.string.msg_created_task), Toast.LENGTH_SHORT).show()
	}
	
	override fun startAsyncTask(counterStart:Int) {
		if (myAsyncTask == null || myAsyncTask !!.isCancelled) {
			Toast.makeText(this, R.string.msg_should_create_task, Toast.LENGTH_SHORT).show()
		} else {
			myAsyncTask!!.execute(counterStart)
			Toast.makeText(this, getString(R.string.msg_started_task), Toast.LENGTH_SHORT).show()
		}
	}
	
	override fun cancelAsyncTask() {
		myAsyncTask?.cancel(true) ?: run {
			Toast.makeText(this, R.string.msg_should_create_task, Toast.LENGTH_SHORT).show()
		}
	}
	
	override fun onPreExecute() {
		Toast.makeText(this, getString(R.string.msg_preexecute), Toast.LENGTH_SHORT).show()
	}
	
	override fun onPostExecute() {
		Toast.makeText(this, getString(R.string.msg_postexecute), Toast.LENGTH_SHORT).show()
		counter_status_text.text =getString(R.string.done)
		myAsyncTask = null
	}
	
	override fun onProgressUpdate(value: String) {
		counter_status_text.text = value
	}
	
	/***
	 * //  IAsyncTaskEvent's methods - end
	 */
	override fun onDestroy() {
		myAsyncTask?.let {
			it.cancel(true)
			myAsyncTask = null
		}
		super.onDestroy()
	}
}