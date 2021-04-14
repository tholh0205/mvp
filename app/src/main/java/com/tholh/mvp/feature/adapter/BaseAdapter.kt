package com.tholh.mvp.feature.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by tholh on 20/12/2019.
 */
abstract class BaseAdapter<Model> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var onItemClickListener: OnItemClickListener? = null

    var items: List<Model>? = null
        set(value) {
            if (!value.isNullOrEmpty()) {
                field = ArrayList(value)
            }
        }

    override fun getItemCount(): Int {
        return if (items.isNullOrEmpty()) {
            0
        } else {
            items!!.size
        }
    }

    open fun getItem(position: Int): Model? {
        if (position in 0 until itemCount) {
            return items!![position]
        }
        return null
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        super.onViewAttachedToWindow(holder)
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener {
                onItemClickListener?.onItemClick(it, holder.adapterPosition)
            }
        }
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(null)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }
}