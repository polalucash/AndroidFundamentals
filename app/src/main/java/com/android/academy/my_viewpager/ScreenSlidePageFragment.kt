package com.android.academy.my_viewpager

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.android.academy.R
import com.android.academy.models.MovieModel

class ScreenSlidePageFragment: Fragment() {
	
	private lateinit var posterImage: ImageView
	private lateinit var titleText: TextView
	private lateinit var overviewText: TextView
	private lateinit var releaseDateText: TextView
	private lateinit var trailerUrlBtn: Button
	
	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		val view = inflater.inflate(R.layout.fragment_movie_details, container, false)
		bindViews(view)
		
		val movieModel: MovieModel? = arguments?.getParcelable(MovieModelTAG)
		movieModel?.let { loadMovie( it,container) }
		return view
	}
	
	private fun loadMovie(
		movieModel: MovieModel,
		container: ViewGroup?
	) {
		titleText.text = movieModel.title
		overviewText.text = movieModel.overview
		posterImage.setImageResource(movieModel.imageRes)
		releaseDateText.text = movieModel.releaseDate
		trailerUrlBtn.setOnClickListener{
			movieModel.trailerUrl?.let { it1 ->
				if (container != null) {
					openWebPage(it1, container.context)
				}
			}
		}
	}
	
	private fun bindViews(view: View) {
		titleText = view.findViewById(R.id.details_fragment_title)
		overviewText = view.findViewById(R.id.details_fragment_overview_text)
		posterImage = view.findViewById(R.id.details_fragment_poster)
		releaseDateText = view.findViewById(R.id.details_fragment_release_date)
		trailerUrlBtn = view.findViewById(R.id.details_fragment_trailer_btn)
	}
	
	companion object {
		const val MovieModelTAG: String = "MovieModel"
		fun newInstance(movieModel: MovieModel): ScreenSlidePageFragment {
			val fragment = ScreenSlidePageFragment()
			val args = Bundle()
			args.putParcelable(MovieModelTAG, movieModel)
			fragment.arguments = args
			return fragment
		}
	
	}
	fun openWebPage(url: String, context: Context) {
		context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
		
	}
}