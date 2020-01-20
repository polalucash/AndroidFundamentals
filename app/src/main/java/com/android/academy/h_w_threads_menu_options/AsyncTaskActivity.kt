package com.android.academy.h_w_threads_menu_options

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import com.android.academy.R
import kotlinx.android.synthetic.main.fragment_counter.*

class AsyncTaskActivity : AppCompatActivity(), IAsyncTaskEvents  {
	private lateinit var counterFragment: CounterFragment
	private var asyncTask: CounterAsyncTask? = null
	
	companion object{
		const val FRAGMENT_TAG = "fragment_tag"
	}
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_async_task)
		
		savedInstanceState?.let {
			counterFragment = supportFragmentManager.findFragmentByTag(FRAGMENT_TAG) as CounterFragment
		} ?: run {
			counterFragment = CounterFragment()//Get Fragment Instance
			val data = Bundle()//Use bundle to pass data
			data.putString(	CounterFragment.FRAGMENT_TYPE, 	getString(R.string.async_task_activity))//put string, int, etc in bundle with a key value
			counterFragment.arguments = data//Finally set argument bundle to fragment
			supportFragmentManager
				.beginTransaction()
				.replace(R.id.fragment, counterFragment, FRAGMENT_TAG)
				.commit()//now replace the argument fragment
		}
	}
	
	/***
	 * // IAsyncTaskEvent's methods - start:
	 */
	override fun createAsyncTask() {
		Toast.makeText(this, getString(R.string.msg_created_task), Toast.LENGTH_SHORT).show()
		asyncTask = CounterAsyncTask(this)
	}
	
	override fun startAsyncTask() {
		if (asyncTask == null || asyncTask !!.isCancelled) {
			Toast.makeText(this, R.string.msg_should_create_task, Toast.LENGTH_SHORT).show()
		} else {
			Toast.makeText(this, getString(R.string.msg_started_task), Toast.LENGTH_SHORT).show()
			asyncTask!!.execute(10)
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
		counterFragment.updateFragmentText("Done!")
		asyncTask = null
	}
	
	override fun onProgressUpdate(value: String) {
		counterFragment.updateFragmentText(value)
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











