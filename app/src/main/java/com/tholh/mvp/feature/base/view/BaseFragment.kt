package com.tholh.mvp.feature.base.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.tholh.mvp.FileLog
import com.tholh.mvp.LayoutHelper
import com.tholh.mvp.R
import com.tholh.mvp.feature.base.presenter.BasePresenter

abstract class BaseFragment : Fragment, BaseView {

    private var loadingLayout: FrameLayout? = null
    private var loadingAnimation: Animator? = null
    private var animationInProcess = false
    open var toolbar: Toolbar? = null
    protected var visibleDialog: Dialog? = null

    constructor() : super()
    constructor(contentLayoutId: Int) : super(contentLayoutId)

    open fun getPresenter(): BasePresenter<out BaseView>? {
        return null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar = view.findViewById(R.id.toolbar)
        if (toolbar != null) {
            setupActionBar()
            val localActivity = activity
            if (localActivity is AppCompatActivity) {
                localActivity.setSupportActionBar(toolbar)
            }
        }
        getPresenter()?.onViewCreated()
    }

    open fun setupActionBar() {
        setHasOptionsMenu(true)
        toolbar?.title = javaClass.simpleName
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            findNavController().navigateUp()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    open fun createNavOptionsBuilder(): NavOptions.Builder {
        val builder = NavOptions.Builder()
        builder.setEnterAnim(R.animator.enter_from_right)
        builder.setExitAnim(R.animator.exit_to_left)
        builder.setPopEnterAnim(R.animator.enter_from_left)
        builder.setPopExitAnim(R.animator.exit_to_right)
        return builder
    }

    override fun onStart() {
        super.onStart()
        getPresenter()?.onStart()
    }

    override fun onResume() {
        super.onResume()
        getPresenter()?.onResume()
    }

    override fun onPause() {
        super.onPause()
        getPresenter()?.onPause()
    }

    override fun onStop() {
        super.onStop()
        dismissCurrentDialog()
        getPresenter()?.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        getPresenter()?.onDestroy()
    }

    override fun showNetworkError() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.network_not_available))
        builder.setMessage(getString(R.string.network_not_available_message))
        builder.setPositiveButton(getString(R.string.ok)) { dialog, _ ->
            dialog.dismiss()
        }
        showDialog(builder.create())
    }

    override fun showGenericError() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.notice))
        builder.setMessage(getString(R.string.generic_error_message))
        builder.setPositiveButton(getString(R.string.ok)) { dialog, _ ->
            dialog.dismiss()
        }
        showDialog(builder.create())
    }

    protected fun dismissCurrentDialog() {
        if (visibleDialog == null) {
            return
        }
        try {
            visibleDialog?.dismiss()
            visibleDialog = null
        } catch (e: Exception) {
            FileLog.e(e)
        }
    }

    protected fun showDialog(dialog: Dialog?) {
        showDialog(dialog, null)
    }

    protected fun showDialog(dialog: Dialog?, onDismissListener: DialogInterface.OnDismissListener?) {
        if (dialog == null) {
            return
        }
        try {
            visibleDialog?.dismiss()
            visibleDialog = null
        } catch (e: Exception) {
            FileLog.e(e)
        }
        try {
            visibleDialog = dialog
            visibleDialog?.setCanceledOnTouchOutside(true)
            visibleDialog?.setOnDismissListener {
                onDismissListener?.onDismiss(it)
                visibleDialog = null
            }
            visibleDialog?.show()
        } catch (e: Exception) {
            FileLog.e(e)
        }
    }

    override fun showLoading() {
        if (loadingAnimation != null) {
            loadingAnimation?.cancel()
            loadingAnimation = null
        }
        val context = context ?: return
        if (loadingLayout == null) {
            loadingLayout = object : FrameLayout(context) {
                override fun hasOverlappingRendering(): Boolean {
                    return false
                }
            }
            loadingLayout?.isClickable = true
            loadingLayout?.setBackgroundColor(0x88000000.toInt())
            val progressView = ProgressBar(context)
            loadingLayout?.addView(progressView, LayoutHelper.createFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER))
            val fragmentContentView = view?.findViewById<ViewGroup>(R.id.fragment_content_view)
            val parent: FrameLayout? = if (fragmentContentView is FrameLayout) {
                fragmentContentView
            } else {
                activity?.findViewById(android.R.id.content)
            }
            loadingLayout?.background = parent?.background
            loadingLayout?.alpha = 0f
            parent?.addView(loadingLayout, LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT))
        }
        val animator = ValueAnimator.ofFloat(0f, 1f)
        animator.addUpdateListener {
            val alpha = it.animatedValue as Float
            loadingLayout?.alpha = alpha
        }
        animator.duration = 200
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationCancel(animation: Animator?) {
                super.onAnimationCancel(animation)
                if (loadingAnimation != null && loadingAnimation == animation) {
                    loadingAnimation = null
                    animationInProcess = false
                }
            }

            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                if (loadingAnimation != null && loadingAnimation == animation) {
                    loadingAnimation = null
                    animationInProcess = false
                }
            }
        })
        loadingAnimation = animator
        animator.start()
        animationInProcess = true
    }

    override fun dismissLoading() {
        if (loadingAnimation != null) {
            loadingAnimation?.cancel()
            loadingAnimation = null
        }
        if (loadingLayout == null) {
            return
        }
        val animator = ValueAnimator.ofFloat(1f, 0f)
        animator.addUpdateListener {
            val alpha = it.animatedValue as Float
            loadingLayout?.alpha = alpha
        }
        animator.duration = 200
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationCancel(animation: Animator?) {
                super.onAnimationCancel(animation)
                if (loadingAnimation != null && loadingAnimation == animation) {
                    loadingAnimation = null
                    animationInProcess = false
                }
            }

            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                if (loadingAnimation != null && loadingAnimation == animation) {
                    val parent = loadingLayout?.parent as ViewGroup?
                    parent?.removeView(loadingLayout)
                    loadingLayout = null
                    loadingAnimation = null
                    animationInProcess = false
                }
            }
        })
        loadingAnimation = animator
        animator.start()
        animationInProcess = true
    }
}