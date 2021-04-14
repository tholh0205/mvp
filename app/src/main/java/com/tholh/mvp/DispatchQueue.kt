package com.tholh.mvp

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.os.Message
import java.util.concurrent.CountDownLatch

class DispatchQueue(threadName: String) : Thread(threadName) {

    private var syncLatch = CountDownLatch(1)
    @Volatile
    private var handler: Handler? = null

    init {
        start()
    }

    fun sendMessage(msg: Message, delay: Int) {
        try {
            syncLatch.await()
            if (delay <= 0) {
                handler?.sendMessage(msg)
            } else {
                handler?.sendMessageDelayed(msg, delay.toLong())
            }
        } catch (e: Exception) {
            FileLog.e(e)
        }

    }

    fun cancelRunnable(runnable: Runnable) {
        try {
            syncLatch.await()
            handler?.removeCallbacks(runnable)
        } catch (e: Exception) {
            FileLog.e(e)
        }

    }

    fun postRunnable(runnable: Runnable) {
        postRunnable(runnable, 0)
    }

    fun postRunnable(runnable: Runnable, delay: Long) {
        try {
            syncLatch.await()
        } catch (e: Exception) {
            FileLog.e(e)
        }

        if (delay <= 0) {
            handler?.post(runnable)
        } else {
            handler?.postDelayed(runnable, delay)
        }
    }

    fun cleanupQueue() {
        try {
            syncLatch.await()
            handler?.removeCallbacksAndMessages(null)
        } catch (e: Exception) {
            FileLog.e(e)
        }

    }

    fun handleMessage(inputMessage: Message) {

    }

    fun recycle() {
        handler?.looper?.quit()
    }

    override fun run() {
        Looper.prepare()
        handler = @SuppressLint("HandlerLeak")
        object : Handler() {
            override fun handleMessage(msg: Message) {
                this@DispatchQueue.handleMessage(msg)
            }
        }
        syncLatch.countDown()
        Looper.loop()
    }
}