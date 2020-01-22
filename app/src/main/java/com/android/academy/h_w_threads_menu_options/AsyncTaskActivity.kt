package com.android.academy.h_w_threads_menu_options

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.academy.R
import kotlinx.android.synthetic.main.fragment_counter.*

class AsyncTaskActivity : AppCompatActivity(), IAsyncTaskEvents  {
	private var asyncTask: CounterAsyncTask? = null
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
				getString(R.string.async_task_activity)
			}
		
	}
	
	override fun onSaveInstanceState(outState: Bundle) {
		counter_status_text.text.toString().toIntOrNull()?.let{	outState.putInt(Status ,it)}
		super.onSaveInstanceState(outState)
	}
	
	/***
	 * // IAsyncTaskEvent's methods - start:
	 */
	override fun createAsyncTask() {
		Toast.makeText(this, getString(R.string.msg_created_task), Toast.LENGTH_SHORT).show()
		asyncTask = CounterAsyncTask(this)
	}
	
	override fun startAsyncTask(counterStart:Int) {
		if (asyncTask == null || asyncTask !!.isCancelled) {
			Toast.makeText(this, R.string.msg_should_create_task, Toast.LENGTH_SHORT).show()
		} else {
			Toast.makeText(this, getString(R.string.msg_started_task), Toast.LENGTH_SHORT).show()
			asyncTask!!.execute(counterStart)
		}
	}
	
	override fun cancelAsyncTask() {
		asyncTask?.cancel(true) ?: run {
			Toast.makeText(this, R.string.msg_should_create_task, Toast.LENGTH_SHORT).show()
		}
	}
	
	override fun onPreExecute() {
		Toast.makeText(this, getString(R.string.msg_preexecute), Toast.LENGTH_SHORT).show()
	}
	
	override fun onPostExecute() {
		Toast.makeText(this, getString(R.string.msg_postexecute), Toast.LENGTH_SHORT).show()
		counter_status_text.text =getString(R.string.done)
		asyncTask = null
	}
	
	override fun onProgressUpdate(value: String) {
		counter_status_text.text = value
	}
	
	/***
	 * //  IAsyncTaskEvent's methods - end
	 */
	override fun onDestroy() {
		asyncTask?.let {
			it.cancel(false)
			asyncTask = null
		}
		super.onDestroy()
	}
}











