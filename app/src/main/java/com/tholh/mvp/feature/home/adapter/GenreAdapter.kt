package com.tholh.mvp.feature.home.adapter

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tholh.mvp.AndroidUtilities
import com.tholh.mvp.R
import com.tholh.mvp.feature.adapter.BaseAdapter
import com.tholh.mvp.model.Genre


class GenreAdapter: BaseAdapter<Genre>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_genre_item, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null && holder is ViewHolder) {
            holder.tvGenre.text = item.name
            val gd = GradientDrawable(
                GradientDrawable.Orientation.TL_BR, intArrayOf(0xff00CBCF.toInt(), 0xff007AD9.toInt())
            )
            gd.cornerRadius = AndroidUtilities.dp(8).toFloat()
            holder.itemView.background = gd
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvGenre: TextView = itemView.findViewById(R.id.tv_genre)
        init {
            tvGenre.typeface = AndroidUtilities.getTypeface("fonts/helvetica.ttf")
        }
    }
}