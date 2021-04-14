package com.tholh.mvp.feature.detail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tholh.mvp.AndroidUtilities
import com.tholh.mvp.ImageUtils
import com.tholh.mvp.R
import com.tholh.mvp.component.LoadableImageView
import com.tholh.mvp.feature.adapter.BaseAdapter
import com.tholh.mvp.model.Review

class ReviewAdapter : BaseAdapter<Review>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_review_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null && holder is ViewHolder) {
            holder.imgAvatar.loadPhotoCircle(ImageUtils.getFullUrl(item.author_details.avatar_path))
            holder.tvName.text = item.author
            holder.tvComment.text = item.content
            holder.tvRating.text = item.author_details.rating.toString()
            holder.ratingBar.rating = item.author_details.rating
            holder.tvTime.text = item.created_at
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgAvatar: LoadableImageView = itemView.findViewById(R.id.img_avatar)
        val tvName: TextView = itemView.findViewById(R.id.tv_name)
        val tvComment: TextView = itemView.findViewById(R.id.tv_comment)
        val tvRating: TextView = itemView.findViewById(R.id.tv_rating)
        val ratingBar: RatingBar = itemView.findViewById(R.id.rating_bar)
        val tvTime: TextView = itemView.findViewById(R.id.tv_time)
        init {
            tvName.typeface = AndroidUtilities.getTypeface("fonts/helvetica-bold.ttf")
            tvComment.typeface = AndroidUtilities.getTypeface("fonts/helvetica.ttf")
            tvTime.typeface = AndroidUtilities.getTypeface("fonts/helvetica.ttf")
        }
    }
}