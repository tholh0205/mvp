package com.tholh.mvp

import android.content.Context
import android.content.res.Configuration
import android.graphics.Point
import android.graphics.Rect
import android.graphics.Typeface
import android.os.Build
import android.util.DisplayMetrics
import android.view.Display
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import com.tholh.mvp.FileLog.e
import java.lang.reflect.Field
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs
import kotlin.math.ceil

object AndroidUtilities {
    private val typefaceCache = Hashtable<String, Typeface>()
    var density = 1f
    var statusBarHeight = 0
    var displaySize = Point()
    var displayMetrics = DisplayMetrics()
    var usingHardwareInput = false
    var isInMultiWindow = false

    fun checkDisplaySize(context: Context, newConfiguration: Configuration?) {
        try {
            density = context.resources.displayMetrics.density
            val configuration = newConfiguration ?: context.resources.configuration
            usingHardwareInput =
                configuration.keyboard != Configuration.KEYBOARD_NOKEYS && configuration.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_NO
            val resourceId: Int =
                context.resources.getIdentifier("status_bar_height", "dimen", "android")
            if (resourceId > 0) {
                statusBarHeight = context.resources.getDimensionPixelSize(resourceId)
            }
            val manager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager?
            if (manager != null) {
                val display = manager.defaultDisplay
                if (display != null) {
                    display.getMetrics(displayMetrics)
                    display.getSize(displaySize)
                }
            }
            if (configuration.screenWidthDp != Configuration.SCREEN_WIDTH_DP_UNDEFINED) {
                val newSize = ceil(configuration.screenWidthDp * density.toDouble()).toInt()
                if (abs(displaySize.x - newSize) > 3) {
                    displaySize.x = newSize
                }
            }
            if (configuration.screenHeightDp != Configuration.SCREEN_HEIGHT_DP_UNDEFINED) {
                val newSize = ceil(configuration.screenHeightDp * density.toDouble()).toInt()
                if (abs(displaySize.y - newSize) > 3) {
                    displaySize.y = newSize
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getRealScreenSize(): Point {
        val size = Point()
        try {
            val windowManager =
                ApplicationLoader.applicationContext!!.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                windowManager.defaultDisplay.getRealSize(size)
            } else {
                try {
                    val mGetRawW = Display::class.java.getMethod("getRawWidth")
                    val mGetRawH = Display::class.java.getMethod("getRawHeight")
                    size.set(
                        mGetRawW.invoke(windowManager.defaultDisplay) as Int,
                        mGetRawH.invoke(windowManager.defaultDisplay) as Int
                    )
                } catch (e: Exception) {
                    windowManager.defaultDisplay.getSize(size)
                    FileLog.e(e)
                }

            }
        } catch (e: Exception) {
            FileLog.e(e)
        }

        return size
    }

    private var mAttachInfoField: Field? = null
    private var mStableInsetsField: Field? = null

    fun getViewInset(view: View?): Int {
        if (view == null || Build.VERSION.SDK_INT < 21 || view.height == displaySize.y || view.height == displaySize.y - statusBarHeight) {
            return 0
        }
        try {
            if (mAttachInfoField == null) {
                mAttachInfoField = View::class.java.getDeclaredField("mAttachInfo")
                mAttachInfoField!!.isAccessible = true
            }
            val mAttachInfo = mAttachInfoField?.get(view)
            if (mAttachInfo != null) {
                if (mStableInsetsField == null) {
                    mStableInsetsField = mAttachInfo.javaClass.getDeclaredField("mStableInsets")
                    mStableInsetsField?.isAccessible = true
                }
                val insets = mStableInsetsField?.get(mAttachInfo) as Rect
                return insets.bottom
            }
        } catch (e: Exception) {
            FileLog.e(e)
        }

        return 0
    }

    fun dp(dp: Number): Int {
        if (dp == 0) {
            return 0
        }
        return ceil(density * dp.toFloat()).toInt()
    }

    fun getTypeface(assetPath: String): Typeface? {
        synchronized(typefaceCache) {
            if (!typefaceCache.containsKey(assetPath)) {
                try {
                    val t: Typeface = if (Build.VERSION.SDK_INT >= 26) {
                        val builder = Typeface.Builder(
                            ApplicationLoader.applicationContext!!.assets,
                            assetPath
                        )
                        if (assetPath.contains("medium")) {
                            builder.setWeight(700)
                        }
                        if (assetPath.contains("italic")) {
                            builder.setItalic(true)
                        }
                        builder.build()
                    } else {
                        Typeface.createFromAsset(
                            ApplicationLoader.applicationContext!!.assets,
                            assetPath
                        )
                    }
                    typefaceCache[assetPath] = t
                } catch (e: java.lang.Exception) {
                    if (BuildVars.LOGS_ENABLED) {
                        e("Could not get typeface '" + assetPath + "' because " + e.message)
                    }
                    return null
                }
            }
            return typefaceCache[assetPath]
        }
    }

    fun getPixelsInCM(cm: Float, isX: Boolean): Float {
        return cm / 2.54f * if (isX) displayMetrics.xdpi else displayMetrics.ydpi
    }

    fun showKeyboard(view: View?) {
        if (view == null) {
            return
        }
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }

    fun hideKeyboard(view: View?) {
        if (view == null) {
            return
        }
        try {
            val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            if (!imm.isActive) {
                return
            }
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun formatDate(pattern: String, time: Long): String {
        val simpleDateFormat = SimpleDateFormat(pattern, Locale.US)
        val date = Date(time)
        return simpleDateFormat.format(date)
    }

    fun isNotEmpty(text: CharSequence?): Boolean {
        return text != null && text.isNotEmpty()
    }

    fun isEmpty(text: CharSequence?): Boolean {
        return text == null || text.isEmpty()
    }

    fun isTablet(): Boolean {
        return false
    }
}