package com.tholh.mvp.feature.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tholh.mvp.AndroidUtilities
import com.tholh.mvp.R
import com.tholh.mvp.component.EqualSpacingItemDecoration
import com.tholh.mvp.feature.adapter.BaseAdapter
import com.tholh.mvp.feature.home.Type
import com.tholh.mvp.feature.home.model.HomeItem
import com.tholh.mvp.model.Genre
import com.tholh.mvp.model.Movie


class HomeAdapter : BaseAdapter<HomeItem>() {

    var loadMoreListener: LoadMoreListener? = null
    var onMovieItemClickListener : OnMovieItemClickListener? = null

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
            Type.TYPE_TRENDING -> TrendingViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_home_item, parent, false)
            )
            Type.TYPE_GENRE -> GenreViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_home_item, parent, false)
            )
            Type.TYPE_POPULAR -> PopularViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_home_item, parent, false)
            )
            Type.TYPE_TOP_RATED -> TopRatedViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_home_item, parent, false)
            )
            Type.TYPE_UPCOMING -> UpcomingViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_home_item, parent, false)
            )
            else -> ViewHolder(FrameLayout(parent.context))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            when (holder) {
                is TrendingViewHolder -> {
                    holder.tvName.text = holder.tvName.resources.getString(R.string.trending)
                    val movies = item.children as List<Movie>
                    holder.trendingAdapter.items = movies
                }
                is PopularViewHolder -> {
                    holder.tvName.text = holder.tvName.resources.getString(R.string.popular)
                    val movies = item.children as List<Movie>
                    holder.popularAdapter.items = movies
                }
                is TopRatedViewHolder -> {
                    holder.tvName.text = holder.tvName.resources.getString(R.string.top_rated)
                    val movies = item.children as List<Movie>
                    holder.topRatedAdapter.items = movies
                }
                is UpcomingViewHolder -> {
                    holder.tvName.text = holder.tvName.resources.getString(R.string.upcoming)
                    val movies = item.children as List<Movie>
                    holder.upcomingAdapter.items = movies
                }
                is GenreViewHolder -> {
                    holder.tvName.text = holder.tvName.resources.getString(R.string.genre)
                    holder.tvName.isAllCaps = true
                    val genres = item.children as List<Genre>
                    holder.genreAdapter.items = genres
                }
            }
        }
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        super.onViewAttachedToWindow(holder)
        when (holder) {
            is TrendingViewHolder -> {
                holder.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        val lastItem = (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                        if (dx > 0 && lastItem == holder.trendingAdapter.itemCount - 1) {
                            loadMoreListener?.loadMore(holder.itemId)
                        }
                    }
                })
                holder.trendingAdapter.onItemClickListener = object : OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        onMovieItemClickListener?.onMovieItemClick(view, holder.trendingAdapter.getItem(position)?.id)
                    }
                }
            }
            is PopularViewHolder -> {
                holder.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        val lastItem = (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                        if (dx > 0 && lastItem == holder.popularAdapter.itemCount - 1) {
                            loadMoreListener?.loadMore(holder.itemId)
                        }
                    }
                })
                holder.popularAdapter.onItemClickListener = object : OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        onMovieItemClickListener?.onMovieItemClick(view, holder.popularAdapter.getItem(position)?.id)
                    }
                }
            }
            is TopRatedViewHolder -> {
                holder.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        val lastItem = (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                        if (dx > 0 && lastItem == holder.topRatedAdapter.itemCount - 1) {
                            loadMoreListener?.loadMore(holder.itemId)
                        }
                    }
                })
                holder.topRatedAdapter.onItemClickListener = object : OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        onMovieItemClickListener?.onMovieItemClick(view, holder.topRatedAdapter.getItem(position)?.id)
                    }
                }
            }
            is UpcomingViewHolder -> {
                holder.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        val lastItem = (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                        if (dx > 0 && lastItem == holder.upcomingAdapter.itemCount - 1) {
                            loadMoreListener?.loadMore(holder.itemId)
                        }
                    }
                })
                holder.upcomingAdapter.onItemClickListener = object : OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        onMovieItemClickListener?.onMovieItemClick(view, holder.upcomingAdapter.getItem(position)?.id)
                    }
                }
            }
        }

    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        when (holder) {
            is TrendingViewHolder -> {
                holder.recyclerView.clearOnScrollListeners()
            }
            is PopularViewHolder -> {
                holder.recyclerView.clearOnScrollListeners()
            }
            is TopRatedViewHolder -> {
                holder.recyclerView.clearOnScrollListeners()
            }
            is UpcomingViewHolder -> {
                holder.recyclerView.clearOnScrollListeners()
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    class GenreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tv_name)
        val recyclerView: RecyclerView = itemView.findViewById(R.id.recyclerView)
        val genreAdapter = GenreAdapter()

        init {
            tvName.typeface = AndroidUtilities.getTypeface("fonts/helvetica-bold.ttf")
            recyclerView.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            recyclerView.adapter = genreAdapter
            recyclerView.itemAnimator = null
            recyclerView.addItemDecoration(
                EqualSpacingItemDecoration(AndroidUtilities.dp(16), EqualSpacingItemDecoration.HORIZONTAL)
            )
        }
    }

    class TrendingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tv_name)
        val recyclerView: RecyclerView = itemView.findViewById(R.id.recyclerView)
        val trendingAdapter = TrendingAdapter()

        init {
            tvName.typeface = AndroidUtilities.getTypeface("fonts/helvetica-bold.ttf")
            recyclerView.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            recyclerView.adapter = trendingAdapter
            recyclerView.itemAnimator = null
            recyclerView.addItemDecoration(
                EqualSpacingItemDecoration(AndroidUtilities.dp(16), EqualSpacingItemDecoration.HORIZONTAL)
            )
        }
    }

    class PopularViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tv_name)
        val recyclerView: RecyclerView = itemView.findViewById(R.id.recyclerView)
        val popularAdapter = PopularAdapter()

        init {
            tvName.typeface = AndroidUtilities.getTypeface("fonts/helvetica-bold.ttf")
            recyclerView.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            recyclerView.adapter = popularAdapter
            recyclerView.itemAnimator = null
            recyclerView.addItemDecoration(
                EqualSpacingItemDecoration(AndroidUtilities.dp(16), EqualSpacingItemDecoration.HORIZONTAL)
            )
        }
    }

    class TopRatedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tv_name)
        val recyclerView: RecyclerView = itemView.findViewById(R.id.recyclerView)
        val topRatedAdapter = TopRatedAdapter()

        init {
            tvName.typeface = AndroidUtilities.getTypeface("fonts/helvetica-bold.ttf")
            recyclerView.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            recyclerView.adapter = topRatedAdapter
            recyclerView.itemAnimator = null
            recyclerView.addItemDecoration(
                EqualSpacingItemDecoration(AndroidUtilities.dp(16), EqualSpacingItemDecoration.HORIZONTAL)
            )
        }
    }

    class UpcomingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tv_name)
        val recyclerView: RecyclerView = itemView.findViewById(R.id.recyclerView)
        val upcomingAdapter = UpcomingAdapter()

        init {
            tvName.typeface = AndroidUtilities.getTypeface("fonts/helvetica-bold.ttf")
            recyclerView.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            recyclerView.adapter = upcomingAdapter
            recyclerView.itemAnimator = null
            recyclerView.addItemDecoration(
                EqualSpacingItemDecoration(AndroidUtilities.dp(16), EqualSpacingItemDecoration.HORIZONTAL)
            )
        }
    }

    interface LoadMoreListener {
        fun loadMore(id: Long)
    }

    interface OnMovieItemClickListener {
        fun onMovieItemClick(view: View, movieId: Int?)
    }
}