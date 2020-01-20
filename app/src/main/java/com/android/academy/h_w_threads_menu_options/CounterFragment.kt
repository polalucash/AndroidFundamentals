package com.android.academy.h_w_threads_menu_options

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.academy.R
import kotlinx.android.synthetic.main.fragment_counter.*

/**
 * A simple [Fragment] subclass.
 */
class CounterFragment : Fragment(), View.OnClickListener {
	
	companion object {
		const val FRAGMENT_TYPE: String = "FRAGMENT_TYPE"
	}
	private var callbackListener: IAsyncTaskEvents? = null
	
	override fun onCreateView(inflater: LayoutInflater,
	                          container: ViewGroup?,
	                          savedInstanceState: Bundle?
	): View? {
		return inflater.inflate(R.layout.fragment_counter, container, false)
	}
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        When you access btnAsyncCreate, it calls for getView().findViewById(R.id.btn_K).
//        The problem is that you are accessing it too soon. getView() returns null
//        getView() returns null in onCreateView, So we doing it in the onViewCreated method
		super.onViewCreated(view, savedInstanceState)
		
		cancel_btn.setOnClickListener(this)
		start_btn.setOnClickListener(this)
		create_btn.setOnClickListener(this)
		
		//UNPACK OUR DATA FROM OUR BUNDLE
		this.arguments?.getString(FRAGMENT_TYPE)?.let {
			counter_status_text?.text = it
		}
	}
	
	override fun onSaveInstanceState(outState: Bundle) {
		outState.putString(FRAGMENT_TYPE, counter_status_text.text.toString())
		super.onSaveInstanceState(outState)
	}
	override fun onAttach(context: Context) {
		super.onAttach(context)
		if (context is IAsyncTaskEvents) {
			callbackListener = context
		}
		
	}
	
	override fun onDetach() {
		super.onDetach()
		callbackListener = null
	}
	
	
	override fun onClick(v: View) {
		callbackListener?.let {
			if (isAdded)
				when (v.id) {
				R.id.cancel_btn -> it.cancelAsyncTask()
				R.id.create_btn -> it.createAsyncTask()
				R.id.start_btn -> it.startAsyncTask()
			}
		}
	}
	
	fun updateFragmentText(text: String) {
		counter_status_text.text = text
	}
}