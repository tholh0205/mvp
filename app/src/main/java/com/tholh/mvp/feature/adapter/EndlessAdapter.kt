package com.tholh.mvp.feature.adapter

import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.tholh.mvp.LayoutHelper

/**
 * Created by tholh on 20/12/2019.
 */
abstract class EndlessAdapter<Model> : BaseAdapter<Model>() {

    companion object {
        const val TYPE_HEADER = 0
        const val TYPE_ITEM = 1
        const val TYPE_LOADING = 2
    }

    var showLoading = false

    override fun getItemCount(): Int {
        var count = super.getItemCount()
        if (showLoading) {
            count++
        }
        return count
    }

    override fun getItemViewType(position: Int): Int {
        val itemCount = itemCount
        if (showLoading && position == itemCount - 1) {
            return TYPE_LOADING
        }
        return TYPE_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_LOADING -> {
                val frameLayout = object : FrameLayout(parent.context) {
                    override fun hasOverlappingRendering(): Boolean {
                        return false
                    }
                }
                frameLayout.layoutParams = LayoutHelper.createRecycle(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT)
                return LoadingViewHolder(frameLayout)
            }
            else -> onCreateItemViewHolder(parent, viewType)
        }
    }

    abstract fun onCreateItemViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder

    open inner class LoadingViewHolder(itemView: ViewGroup) : RecyclerView.ViewHolder(itemView) {
        init {
            val progressBar = ProgressBar(itemView.context)
            when (itemView) {
                is FrameLayout -> itemView.addView(progressBar, LayoutHelper.createFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER))
                is LinearLayout -> itemView.addView(progressBar, LayoutHelper.createLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER))
                is RelativeLayout -> itemView.addView(progressBar, LayoutHelper.createRelative(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, RelativeLayout.CENTER_IN_PARENT))
            }
        }
    }

}