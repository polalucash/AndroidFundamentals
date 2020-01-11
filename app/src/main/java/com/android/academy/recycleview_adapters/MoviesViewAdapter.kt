package com.android.academy.recycleview_adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.android.academy.OnMovieClickListener
import com.android.academy.R
import com.android.academy.models.MovieModel
import kotlinx.android.synthetic.main.item_movie.view.*

class MoviesViewAdapter(
	context: Context,
	private val movieClickListener: OnMovieClickListener
) 	: RecyclerView.Adapter<MoviesViewAdapter.MovieViewHolder>() {
	
	class MovieViewHolder(view: View, movieClickListener: OnMovieClickListener) :
		RecyclerView.ViewHolder(view) {
		private lateinit var movieModel: MovieModel
		private val ivImage: ImageView = view.item_movie_iv
		private val tvTitle: TextView = view.item_movie_tv_title
		private val tvOverview: TextView = view.item_movie_tv_overview
		
		init {
			view.setOnClickListener {
				movieClickListener.onMovieClicked(movieModel)
			}
		}
		fun bind(movieModel: MovieModel) {
			ivImage.setImageResource(movieModel.imageRes)
			tvTitle.text = movieModel.title
			tvOverview.text = movieModel.overview
			this.movieModel = movieModel
		}
	}
    private val layoutInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private val moviesAsyncListDiffer = AsyncListDiffer<MovieModel>(this, MoviesDiffUtilCallBack())

    override fun getItemCount() = moviesAsyncListDiffer.currentList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = layoutInflater.inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(
	        view,
	        movieClickListener
        )
    }

    fun setData(newItems: List<MovieModel>) {
        moviesAsyncListDiffer.submitList(newItems)
    }

    override fun onBindViewHolder(movieHolder: MovieViewHolder, position: Int) {
        val viewModel = moviesAsyncListDiffer.currentList[position]
        movieHolder.bind(viewModel)
    }
}