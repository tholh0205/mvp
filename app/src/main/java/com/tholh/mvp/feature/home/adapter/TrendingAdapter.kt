package com.tholh.mvp.feature.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tholh.mvp.AndroidUtilities
import com.tholh.mvp.ImageUtils
import com.tholh.mvp.LayoutHelper
import com.tholh.mvp.R
import com.tholh.mvp.component.LoadableImageView
import com.tholh.mvp.feature.adapter.EndlessAdapter
import com.tholh.mvp.model.Movie

class TrendingAdapter : EndlessAdapter<Movie>() {
    override fun onCreateItemViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_movie_item, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            holder.imageView.loadUrl(ImageUtils.getFullUrl(getItem(position)?.backdrop_path))
        }
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView : LoadableImageView = itemView.findViewById(R.id.image)
    }
}