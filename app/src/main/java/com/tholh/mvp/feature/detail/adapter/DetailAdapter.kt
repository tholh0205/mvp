package com.tholh.mvp.feature.detail.adapter

import android.content.res.ColorStateList
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.android.material.chip.ChipGroup
import com.tholh.mvp.AndroidUtilities
import com.tholh.mvp.ImageUtils
import com.tholh.mvp.R
import com.tholh.mvp.component.CustomDividerItemDecoration
import com.tholh.mvp.component.EqualSpacingItemDecoration
import com.tholh.mvp.component.LoadableImageView
import com.tholh.mvp.feature.adapter.BaseAdapter
import com.tholh.mvp.feature.detail.Type
import com.tholh.mvp.feature.detail.model.DetailItem
import com.tholh.mvp.model.*


class DetailAdapter : BaseAdapter<DetailItem>() {

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        if (item != null) {
            return item.id
        }
        return -1
    }

    override fun getItemId(position: Int): Long {
        val item = getItem(position)
        if (item != null) {
            return item.id.toLong()
        }
        return super.getItemId(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            Type.TYPE_MOVIE_DETAIL -> {
                DetailViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.layout_movie_info, parent, false)
                )
            }
            Type.TYPE_YOUR_RATE -> {
                YourRateViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.layout_your_rate, parent, false)
                )
            }
            Type.TYPE_CAST -> {
                CastViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.layout_home_item, parent, false)
                )
            }
            Type.TYPE_VIDEO -> {
                VideoViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.layout_home_item, parent, false)
                )
            }
            Type.TYPE_REVIEW -> {
                ReviewViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.layout_home_item, parent, false)
                )
            }
            Type.TYPE_RECOMMENDATION -> {
                RecommendationViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.layout_home_item, parent, false)
                )
            }
            else -> ViewHolder(FrameLayout(parent.context))

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            when (holder) {
                is DetailViewHolder -> {
                    if (!item.list.isNullOrEmpty()) {
                        val movieDetail = item.list[0] as MovieDetail?
                        if (movieDetail != null) {
                            holder.imgCover.loadUrl(ImageUtils.getFullUrl(movieDetail.backdrop_path))
                            holder.imgPoster.loadUrl(ImageUtils.getFullUrl(movieDetail.poster_path))
                            holder.tvName.text = movieDetail.title
                            holder.tvDescription.text = movieDetail.overview
                            holder.tvRating.text = movieDetail.vote_average.toString()
                            holder.ratingBar.rating = movieDetail.vote_average.toFloat()
                            holder.tvReleaseDate.text = movieDetail.release_date
                            holder.tvReadMore.setOnClickListener {
                                holder.tvDescription.maxLines = 100
                                notifyItemChanged(holder.adapterPosition)
                            }
                            if (holder.chipGroup.childCount <= 0) {
                                val layoutInflater = LayoutInflater.from(holder.itemView.context)
                                movieDetail.genres.forEach {
                                    val chip = layoutInflater.inflate(
                                        R.layout.layout_chip_item,
                                        null
                                    ) as Chip
                                    chip.text = it.name
                                    chip.isClickable = false
                                    val chipDrawable = chip.chipDrawable as ChipDrawable
                                    chipDrawable.chipBackgroundColor =
                                        ColorStateList.valueOf(0xff007AD9.toInt())
                                    chip.typeface =
                                        AndroidUtilities.getTypeface("fonts/helvetica.ttf")
                                    holder.chipGroup.addView(chip)
                                }
                            }
                        }
                    }
                }
                is CastViewHolder -> {
                    holder.tvName.text = holder.itemView.resources.getString(R.string.series_cast)
                    val casts = item.list as List<Cast>?
                    holder.castAdapter.items = casts
                }
                is VideoViewHolder -> {
                    holder.tvName.text = holder.itemView.resources.getString(R.string.video)
                    val videos = item.list as List<Video>?
                    holder.videoAdapter.items = videos
                }
                is ReviewViewHolder -> {
                    holder.tvName.text = holder.itemView.resources.getString(R.string.comments)
                    val comments = item.list as List<Review>?
                    holder.reviewAdapter.items = comments
                }
                is RecommendationViewHolder -> {
                    holder.tvName.text =
                        holder.itemView.resources.getString(R.string.recommendations)
                    val recommendations = item.list as List<Recommendation>?
                    holder.recommendationAdapter.items = recommendations
                }
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    class DetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgCover: LoadableImageView = itemView.findViewById(R.id.movie_cover)
        val imgPoster: LoadableImageView = itemView.findViewById(R.id.movie_poster)
        val tvName: TextView = itemView.findViewById(R.id.tv_name)
        val tvDescription: TextView = itemView.findViewById(R.id.tv_description)
        val tvRating: TextView = itemView.findViewById(R.id.tv_rating)
        val ratingBar: RatingBar = itemView.findViewById(R.id.rating_bar)
        val tvReleaseDate: TextView = itemView.findViewById(R.id.tv_release_date)
        val chipGroup
                : ChipGroup = itemView.findViewById(R.id.chip_group)
        val tvReadMore: TextView = itemView.findViewById(R.id.tv_read_more)

        init {
            tvName.typeface = AndroidUtilities.getTypeface("fonts/helvetica-bold.ttf")
            tvDescription.typeface = AndroidUtilities.getTypeface("fonts/helvetica.ttf")
            tvRating.typeface = AndroidUtilities.getTypeface("fonts/helvetica-bold.ttf")
            tvReleaseDate.typeface = AndroidUtilities.getTypeface("fonts/helvetica.ttf")
            tvReadMore.typeface = AndroidUtilities.getTypeface("fonts/helvetica.ttf")
            itemView.findViewById<TextView>(R.id.tv_favorite).typeface = AndroidUtilities.getTypeface("fonts/helvetica.ttf")
        }
    }

    class YourRateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvYourRate: TextView = itemView.findViewById(R.id.tv_your_rate)
        val tvRating: TextView = itemView.findViewById(R.id.tv_rating)
        val btnWriteComment: TextView = itemView.findViewById(R.id.btn_write_a_comment)

        init {
            tvYourRate.typeface = AndroidUtilities.getTypeface("fonts/helvetica-bold.ttf")
            tvRating.typeface = AndroidUtilities.getTypeface("fonts/helvetica-bold.ttf")
            btnWriteComment.typeface = AndroidUtilities.getTypeface("fonts/helvetica-bold.ttf")
        }
    }

    class CastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tv_name)
        val recyclerView: RecyclerView = itemView.findViewById(R.id.recyclerView)
        val castAdapter = CastAdapter()

        init {
            tvName.typeface = AndroidUtilities.getTypeface("fonts/helvetica-bold.ttf")
            recyclerView.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            recyclerView.adapter = castAdapter
            recyclerView.itemAnimator = null
            recyclerView.addItemDecoration(
                EqualSpacingItemDecoration(
                    AndroidUtilities.dp(16),
                    EqualSpacingItemDecoration.HORIZONTAL
                )
            )
            itemView.findViewById<View>(R.id.arrow_right).visibility = View.GONE
        }
    }

    class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tv_name)
        val recyclerView: RecyclerView = itemView.findViewById(R.id.recyclerView)
        val videoAdapter = VideoAdapter()

        init {
            tvName.typeface = AndroidUtilities.getTypeface("fonts/helvetica-bold.ttf")
            recyclerView.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            recyclerView.adapter = videoAdapter
            recyclerView.itemAnimator = null
            recyclerView.addItemDecoration(
                EqualSpacingItemDecoration(
                    AndroidUtilities.dp(16),
                    EqualSpacingItemDecoration.HORIZONTAL
                )
            )
            itemView.findViewById<View>(R.id.arrow_right).visibility = View.GONE
        }

    }

    class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tv_name)
        val recyclerView: RecyclerView = itemView.findViewById(R.id.recyclerView)
        val reviewAdapter = ReviewAdapter()

        init {
            tvName.typeface = AndroidUtilities.getTypeface("fonts/helvetica-bold.ttf")
            recyclerView.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL, false)
            recyclerView.adapter = reviewAdapter
            recyclerView.itemAnimator = null
            val itemDecoration = CustomDividerItemDecoration(itemView.context, DividerItemDecoration.VERTICAL)
            itemDecoration.extraLeft = AndroidUtilities.dp(66)
            recyclerView.addItemDecoration(itemDecoration)
            itemView.findViewById<View>(R.id.arrow_right).visibility = View.GONE
        }
    }

    class RecommendationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tv_name)
        val recyclerView: RecyclerView = itemView.findViewById(R.id.recyclerView)
        val recommendationAdapter = RecommendationAdapter()

        init {
            tvName.typeface = AndroidUtilities.getTypeface("fonts/helvetica-bold.ttf")
            recyclerView.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            recyclerView.adapter = recommendationAdapter
            recyclerView.itemAnimator = null
            recyclerView.addItemDecoration(
                EqualSpacingItemDecoration(
                    AndroidUtilities.dp(16),
                    EqualSpacingItemDecoration.HORIZONTAL
                )
            )
        }
    }
}