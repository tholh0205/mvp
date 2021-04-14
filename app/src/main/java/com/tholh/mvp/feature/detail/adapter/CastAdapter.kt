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
import com.tholh.mvp.model.Cast

class CastAdapter : EndlessAdapter<Cast>() {

    override fun onCreateItemViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_cast_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null && holder is ViewHolder) {
            holder.imgCast.loadUrl(ImageUtils.getFullUrl(item.profile_path))
            holder.tvName.text = item.name
            holder.tvPosition.text = item.character
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgCast: LoadableImageView = itemView.findViewById(R.id.img_cast)
        val tvName: TextView = itemView.findViewById(R.id.tv_name)
        val tvPosition: TextView = itemView.findViewById(R.id.tv_position)

        init {
            tvName.typeface = AndroidUtilities.getTypeface("fonts/helvetica.ttf")
            tvPosition.typeface = AndroidUtilities.getTypeface("fonts/helvetica-light.ttf")
        }
    }
}