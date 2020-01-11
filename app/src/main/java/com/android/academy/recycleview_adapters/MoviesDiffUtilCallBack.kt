package com.android.academy.recycleview_adapters

import androidx.recyclerview.widget.DiffUtil.ItemCallback
import com.android.academy.models.MovieModel

class MoviesDiffUtilCallBack : ItemCallback<MovieModel>() {
    override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
        return oldItem.imageRes == newItem.imageRes
                && oldItem.title == newItem.title
                && oldItem.overview == newItem.overview
    }

    override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }
}
