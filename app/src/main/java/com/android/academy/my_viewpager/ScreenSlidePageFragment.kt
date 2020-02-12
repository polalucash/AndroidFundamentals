package com.android.academy.my_viewpager

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.academy.R
import com.android.academy.models.MovieModel
import com.android.academy.networking.MovieVideoResult
import com.android.academy.networking.MovieVideosList
import com.android.academy.networking.NetworkingConstants
import com.android.academy.networking.RestClient
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_movie_details.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class ScreenSlidePageFragment: Fragment(),View.OnClickListener {
	
	private var movieModel: MovieModel? = null
	private val picasso = Picasso.get()
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?
	): View {
		return inflater.inflate(R.layout.fragment_movie_details, container, false)
	}
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		movieModel = arguments?.getParcelable(MovieModelTAG)
		movieModel?.let { loadMovie(it) }
		details_fragment_trailer_btn.setOnClickListener(this)
		super.onViewCreated(view, savedInstanceState)
	}
	
	private fun loadMovie(movieModel: MovieModel) {
		picasso
			.load(movieModel.posterUrl)
			.into(details_fragment_poster)
		picasso
			.load(movieModel.coverUrl)
			.into(details_fragment_cover)
		details_fragment_title.text = movieModel.title
		details_fragment_overview_text.text = movieModel.overview
		details_fragment_release_date.text = movieModel.releaseDate
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
	
	override fun onClick(v: View?) {
		movieModel?.let {
			RestClient.moviesService.getMovieTrailer(movieModel!!.movieId)
				.enqueue(object : Callback<MovieVideosList> {
					override fun onFailure(call: Call<MovieVideosList>, t: Throwable) {
						Toast.makeText(
							context,
							"something went wrong",
							Toast.LENGTH_SHORT
						).show()
					}
					
					override fun onResponse(
						call: Call<MovieVideosList>,
						response: Response<MovieVideosList>
					) {
						val key = response.body()?.results?.firstOrNull { r ->
							r.site.toLowerCase(
								Locale.getDefault()
							) == "youtube"
						}?.key
						if (key != null) {
							val trailerUrl = NetworkingConstants.YOUTUBE_BASE_URL + key
							val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(trailerUrl))
							startActivity(browserIntent)
						} else {
							Toast.makeText(
								context,
								"something went wrong",
								Toast.LENGTH_SHORT
							).show()
						}
					}
				})
		}
		
	}
	
	
}