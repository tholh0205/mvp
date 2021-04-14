package com.tholh.mvp.feature.detail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tholh.mvp.AndroidUtilities
import com.tholh.mvp.ImageUtils
import com.tholh.mvp.R
import com.tholh.mvp.component.LoadableImageView
import com.tholh.mvp.feature.adapter.EndlessAdapter
import com.tholh.mvp.model.Recommendation

class RecommendationAdapter: EndlessAdapter<Recommendation>() {

    override fun onCreateItemViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_recommendation_item, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null && holder is ViewHolder) {
            holder.tvName.text = item.title
            holder.imgThumb.loadUrl(ImageUtils.getFullUrl(item.poster_path))
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgThumb: LoadableImageView = itemView.findViewById(R.id.img_thumb)
        val tvName : TextView = itemView.findViewById(R.id.tv_name)
        init {
            tvName.typeface = AndroidUtilities.getTypeface("fonts/helvetica-bold.ttf")
        }
    }
}