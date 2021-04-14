package com.tholh.mvp.feature.detail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tholh.mvp.ImageUtils
import com.tholh.mvp.R
import com.tholh.mvp.component.LoadableImageView
import com.tholh.mvp.feature.adapter.EndlessAdapter
import com.tholh.mvp.model.Video

class VideoAdapter : EndlessAdapter<Video>() {
    override fun onCreateItemViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_video_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            val item = getItem(position)
            if (item != null) {
                holder.imgThumb.loadUrl(ImageUtils.getYoutubeThumb(item.key))
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgThumb : LoadableImageView = itemView.findViewById(R.id.img_thumb)
    }
}