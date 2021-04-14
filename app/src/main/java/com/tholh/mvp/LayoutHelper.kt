package com.tholh.mvp

import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView

object LayoutHelper {

    const val MATCH_PARENT = -1
    const val WRAP_CONTENT = -2

    private fun getSize(size: Number): Int {
        return if (size.toFloat() < 0) size.toInt() else AndroidUtilities.dp(size)
    }

    fun create(width: Number, height: Number): ViewGroup.LayoutParams {
        return ViewGroup.LayoutParams(getSize(width), getSize(height))
    }

    fun createScroll(width: Number, height: Number, gravity: Number): FrameLayout.LayoutParams {
        return FrameLayout.LayoutParams(getSize(width), getSize(height), gravity.toInt())
    }

    fun createScroll(width: Number, height: Number, gravity: Number, leftMargin: Number, topMargin: Number, rightMargin: Number, bottomMargin: Number): FrameLayout.LayoutParams {
        val lp = FrameLayout.LayoutParams(getSize(width), getSize(height), gravity.toInt())
        lp.setMargins(AndroidUtilities.dp(leftMargin), AndroidUtilities.dp(topMargin), AndroidUtilities.dp(rightMargin), AndroidUtilities.dp(bottomMargin))
        return lp
    }

    fun createFrame(width: Number, height: Number): FrameLayout.LayoutParams {
        return FrameLayout.LayoutParams(getSize(width), getSize(height))
    }

    fun createFrame(width: Number, height: Number, gravity: Number): FrameLayout.LayoutParams {
        return FrameLayout.LayoutParams(getSize(width), getSize(height), gravity.toInt())
    }

    fun createFrame(width: Number, height: Number, gravity: Number, leftMargin: Number, topMargin: Number, rightMargin: Number, bottomMargin: Number): FrameLayout.LayoutParams {
        val lp = FrameLayout.LayoutParams(getSize(width), getSize(height), gravity.toInt())
        lp.setMargins(AndroidUtilities.dp(leftMargin), AndroidUtilities.dp(topMargin), AndroidUtilities.dp(rightMargin), AndroidUtilities.dp(bottomMargin))
        return lp
    }

    fun createFrame(width: Number, height: Number, leftMargin: Number, topMargin: Number, rightMargin: Number, bottomMargin: Number): FrameLayout.LayoutParams {
        val lp = FrameLayout.LayoutParams(getSize(width), getSize(height))
        lp.setMargins(AndroidUtilities.dp(leftMargin), AndroidUtilities.dp(topMargin), AndroidUtilities.dp(rightMargin), AndroidUtilities.dp(bottomMargin))
        return lp
    }

    fun createLinear(width: Number, height: Number, weight: Float, gravity: Number, leftMargin: Number, topMargin: Number, rightMargin: Number, bottomMargin: Number): LinearLayout.LayoutParams {
        val lp = LinearLayout.LayoutParams(getSize(width), getSize(height), weight)
        lp.gravity = gravity.toInt()
        lp.setMargins(AndroidUtilities.dp(leftMargin), AndroidUtilities.dp(topMargin), AndroidUtilities.dp(rightMargin), AndroidUtilities.dp(bottomMargin))
        return lp
    }

    fun createLinear(width: Number, height: Number, weight: Float, leftMargin: Number, topMargin: Number, rightMargin: Number, bottomMargin: Number): LinearLayout.LayoutParams {
        val lp = LinearLayout.LayoutParams(getSize(width), getSize(height), weight)
        lp.setMargins(AndroidUtilities.dp(leftMargin), AndroidUtilities.dp(topMargin), AndroidUtilities.dp(rightMargin), AndroidUtilities.dp(bottomMargin))
        return lp
    }

    fun createLinear(width: Number, height: Number, gravity: Number, leftMargin: Number, topMargin: Number, rightMargin: Number, bottomMargin: Number): LinearLayout.LayoutParams {
        val lp = LinearLayout.LayoutParams(getSize(width), getSize(height))
        lp.gravity = gravity.toInt()
        lp.setMargins(AndroidUtilities.dp(leftMargin), AndroidUtilities.dp(topMargin), AndroidUtilities.dp(rightMargin), AndroidUtilities.dp(bottomMargin))
        return lp
    }

    fun createLinear(width: Number, height: Number, leftMargin: Number, topMargin: Number, rightMargin: Number, bottomMargin: Number): LinearLayout.LayoutParams {
        val lp = LinearLayout.LayoutParams(getSize(width), getSize(height))
        lp.setMargins(AndroidUtilities.dp(leftMargin), AndroidUtilities.dp(topMargin), AndroidUtilities.dp(rightMargin), AndroidUtilities.dp(bottomMargin))
        return lp
    }

    fun createLinear(width: Number, height: Number, weight: Float, gravity: Number): LinearLayout.LayoutParams {
        val lp = LinearLayout.LayoutParams(getSize(width), getSize(height), weight)
        lp.gravity = gravity.toInt()
        return lp
    }

    fun createLinear(width: Number, height: Number, gravity: Number): LinearLayout.LayoutParams {
        val lp = LinearLayout.LayoutParams(getSize(width), getSize(height))
        lp.gravity = gravity.toInt()
        return lp
    }

    fun createLinear(width: Number, height: Number, weight: Float): LinearLayout.LayoutParams {
        return LinearLayout.LayoutParams(getSize(width), getSize(height), weight)
    }

    fun createLinear(width: Number, height: Number): LinearLayout.LayoutParams {
        return LinearLayout.LayoutParams(getSize(width), getSize(height))
    }

    fun createRelative(
        width: Int,
        height: Int,
        leftMargin: Int,
        topMargin: Int,
        rightMargin: Int,
        bottomMargin: Int,
        alignParent: Int,
        alignRelative: Int,
        anchorRelative: Int
    ): RelativeLayout.LayoutParams? {
        val layoutParams = RelativeLayout.LayoutParams(getSize(width), getSize(height))
        if (alignParent >= 0) {
            layoutParams.addRule(alignParent)
        }
        if (alignRelative >= 0 && anchorRelative >= 0) {
            layoutParams.addRule(alignRelative, anchorRelative)
        }
        layoutParams.leftMargin = AndroidUtilities.dp(leftMargin)
        layoutParams.topMargin = AndroidUtilities.dp(topMargin)
        layoutParams.rightMargin = AndroidUtilities.dp(rightMargin)
        layoutParams.bottomMargin = AndroidUtilities.dp(bottomMargin)
        return layoutParams
    }

    fun createRelative(width: Int, height: Int, leftMargin: Int, topMargin: Int, rightMargin: Int, bottomMargin: Int): RelativeLayout.LayoutParams? {
        return createRelative(width, height, leftMargin, topMargin, rightMargin, bottomMargin, -1, -1, -1)
    }

    fun createRelative(width: Int, height: Int, leftMargin: Int, topMargin: Int, rightMargin: Int, bottomMargin: Int, alignParent: Int): RelativeLayout.LayoutParams? {
        return createRelative(width, height, leftMargin, topMargin, rightMargin, bottomMargin, alignParent, -1, -1)
    }

    fun createRelative(
        width: Int,
        height: Int,
        leftMargin: Int,
        topMargin: Int,
        rightMargin: Int,
        bottomMargin: Int,
        alignRelative: Int,
        anchorRelative: Int
    ): RelativeLayout.LayoutParams? {
        return createRelative(width, height, leftMargin, topMargin, rightMargin, bottomMargin, -1, alignRelative, anchorRelative)
    }

    fun createRelative(width: Int, height: Int, alignParent: Int, alignRelative: Int, anchorRelative: Int): RelativeLayout.LayoutParams? {
        return createRelative(width, height, 0, 0, 0, 0, alignParent, alignRelative, anchorRelative)
    }

    fun createRelative(width: Int, height: Int): RelativeLayout.LayoutParams? {
        return createRelative(width, height, 0, 0, 0, 0, -1, -1, -1)
    }

    fun createRelative(width: Int, height: Int, alignParent: Int): RelativeLayout.LayoutParams? {
        return createRelative(width, height, 0, 0, 0, 0, alignParent, -1, -1)
    }

    fun createRelative(width: Int, height: Int, alignRelative: Int, anchorRelative: Int): RelativeLayout.LayoutParams? {
        return createRelative(width, height, 0, 0, 0, 0, -1, alignRelative, anchorRelative)
    }

    fun createRecycle(width: Number, height: Number) : RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(getSize(width), getSize(height))
    }
}