package com.android.academy.recyclerview_adapters_by_lec

import androidx.recyclerview.widget.DiffUtil.ItemCallback
import com.android.academy.models.MovieModel

class MoviesDiffUtilCallBack : ItemCallback<MovieModel>() {
    override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
        return oldItem.title == newItem.title
		        && oldItem.posterUrl == newItem.posterUrl
                && oldItem.overview == newItem.overview
    }

    override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }
}
