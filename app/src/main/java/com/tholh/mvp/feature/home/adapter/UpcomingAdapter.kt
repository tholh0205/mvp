package com.tholh.mvp.feature.home.adapter

import android.text.TextUtils
import android.util.TypedValue
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.tholh.mvp.AndroidUtilities
import com.tholh.mvp.ImageUtils
import com.tholh.mvp.LayoutHelper
import com.tholh.mvp.R
import com.tholh.mvp.component.LoadableImageView
import com.tholh.mvp.feature.adapter.EndlessAdapter
import com.tholh.mvp.model.Movie

class UpcomingAdapter: EndlessAdapter<Movie>() {
    override fun onCreateItemViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val linearLayout = LinearLayout(parent.context)
        linearLayout.orientation = LinearLayout.VERTICAL
        linearLayout.layoutParams =
            LayoutHelper.create(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT)
        return UpcomingViewHolder(linearLayout)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is UpcomingViewHolder) {
            holder.bind(getItem(position))
        }
    }

    class UpcomingViewHolder(itemView: ViewGroup) : RecyclerView.ViewHolder(itemView) {
        private var imageView: LoadableImageView = LoadableImageView(itemView.context)
        private var tvName: TextView = TextView(itemView.context)

        init {
            val cardView = CardView(itemView.context)
            cardView.cardElevation = AndroidUtilities.dp(16).toFloat()
            cardView.radius = AndroidUtilities.dp(16).toFloat()
            itemView.addView(
                cardView,
                LayoutHelper.create(140, 210)
            )
            cardView.addView(
                imageView,
                LayoutHelper.create(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT)
            )
            val linearLayout = LinearLayout(itemView.context)
            linearLayout.orientation = LinearLayout.HORIZONTAL
            itemView.addView(
                linearLayout,
                LayoutHelper.createLinear(140, LayoutHelper.WRAP_CONTENT, 0, 4, 0, 0)
            )
            tvName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
            tvName.setTextColor(
                ContextCompat.getColor(
                    itemView.context,
                    R.color.text_color_primary
                )
            )
            tvName.setLines(2)
            tvName.maxLines = 2
            tvName.ellipsize = TextUtils.TruncateAt.END
            tvName.typeface = AndroidUtilities.getTypeface("fonts/helvetica-bold.ttf")
            tvName.gravity = Gravity.CENTER_HORIZONTAL
            linearLayout.addView(
                tvName,
                LayoutHelper.createLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 1f)
            )
            val imageView = ImageView(itemView.context)
            imageView.setImageResource(R.drawable.ic_more_vertical)
            linearLayout.addView(
                imageView,
                LayoutHelper.createLinear(
                    LayoutHelper.WRAP_CONTENT,
                    LayoutHelper.WRAP_CONTENT,
                    0,
                    4,
                    0,
                    0
                )
            )
        }

        fun bind(movie: Movie?) {
            if (movie == null) {
                return
            }
            imageView.loadUrl(ImageUtils.getFullUrl(movie.poster_path))
            tvName.text = movie.title
        }
    }
}