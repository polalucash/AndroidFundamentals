package com.android.academy.fragment_viewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.android.academy.R
import com.android.academy.models.MovieModel

class MovieDetailsFragment : Fragment() {
	
	private lateinit var posterImage: ImageView
	private lateinit var titleText: TextView
	private lateinit var releaseDateText: TextView
	private lateinit var overviewText: TextView
	
	companion object {
		val MOVIE_BUNDLE_KEY = MovieModel::class.simpleName
		fun newInstance(movie: MovieModel): MovieDetailsFragment {
			val fragment =
				MovieDetailsFragment()
			val args = Bundle()
			args.putParcelable(MOVIE_BUNDLE_KEY, movie)
			fragment.arguments = args
			return fragment
		}
		val Tag = MovieDetailsFragment::class.simpleName
	}
	
	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		val view = inflater.inflate(R.layout.fragment_movie_details, container, false)
		val movie:MovieModel? = arguments?.getParcelable(MOVIE_BUNDLE_KEY)
		initViews(view)
		movie?.let(::loadMovie)
		return view
	}
	
	private fun loadMovie(movie: MovieModel) {
		titleText.text = movie.title
		overviewText.text = movie.overview
		posterImage.setImageResource(movie.imageRes)
	
	}
	
	private fun initViews(view: View) {
		titleText = view.findViewById(R.id.details_fragment_title)
		posterImage = view.findViewById(R.id.details_fragment_poster)
		releaseDateText = view.findViewById(R.id.details_fragment_release_date)
		overviewText = view.findViewById(R.id.details_fragment_overview_text)
	}
	
}