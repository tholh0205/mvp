package com.tholh.mvp

import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object FileLog {

    private var streamWriter: OutputStreamWriter? = null
    private var dateFormat: DateFormat? = null
    private var logQueue: DispatchQueue? = null
    private var currentFile: File? = null
    private var inited = false

    private val tag = "ThoLH"

    private fun ensureInitied() {
        if (inited) {
            return
        }
        dateFormat = SimpleDateFormat("dd_MM_yyyy_HH_mm_ss", Locale.US)
        try {
            val sdCard = ApplicationLoader.applicationContext?.getExternalFilesDir(null)
            if (sdCard != null) {
                val dir = File(sdCard.absolutePath + "/logs")
                dir.mkdirs()
                currentFile = File(dir, dateFormat?.format(System.currentTimeMillis()) + ".txt")
                logQueue = DispatchQueue("logQueue")
                currentFile?.createNewFile()
                val stream = FileOutputStream(currentFile!!)
                streamWriter = OutputStreamWriter(stream)
                streamWriter?.write("-----start log ${dateFormat?.format(System.currentTimeMillis())} -----\n")
                streamWriter?.flush()
                inited = true
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun e(message: String) {
        if (!BuildVars.LOGS_ENABLED) {
            return
        }
        ensureInitied()
        Log.e(tag, message)
        if (streamWriter != null) {
            logQueue?.postRunnable(Runnable {
                try {
                    streamWriter?.write("${dateFormat?.format(System.currentTimeMillis())} E/$tag: $message \n")
                    streamWriter?.flush()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            })
        }
    }

    fun e(e: Throwable) {
        if (!BuildVars.LOGS_ENABLED) {
            return
        }
        ensureInitied()
        e.printStackTrace()
        if (streamWriter != null) {
            logQueue?.postRunnable(Runnable {
                try {
                    streamWriter?.write("${dateFormat?.format(System.currentTimeMillis())} E/$tag: $e \n")
                    val stack = e.stackTrace
                    for (i in stack.indices) {
                        streamWriter?.write("${dateFormat?.format(System.currentTimeMillis())} E/$tag: ${stack[i]} \n")
                    }
                    streamWriter?.flush()
                } catch (e1: Exception) {
                    e1.printStackTrace()
                }
            })
        }
    }

    fun e(message: String, exception: Throwable) {
        if (!BuildVars.LOGS_ENABLED) {
            return
        }
        ensureInitied()
        Log.e(tag, message, exception)
        if (streamWriter != null) {
            try {
                streamWriter?.write("${dateFormat?.format(System.currentTimeMillis())} E/$tag: $message \n")
                streamWriter?.write(exception.toString())
                streamWriter?.flush()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun d(message: String) {
        if (!BuildVars.LOGS_ENABLED) {
            return
        }
        ensureInitied()
        Log.d(tag, message)
        if (streamWriter != null) {
            logQueue?.postRunnable(Runnable {
                try {
                    streamWriter?.write("${dateFormat?.format(System.currentTimeMillis())} D/$tag: $message \n")
                    streamWriter?.flush()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            })
        }
    }

    fun w(message: String) {
        if (!BuildVars.LOGS_ENABLED) {
            return
        }
        ensureInitied()
        Log.w(tag, message)
        if (streamWriter != null) {
            logQueue?.postRunnable(Runnable {
                try {
                    streamWriter?.write("${dateFormat?.format(System.currentTimeMillis())} W/$tag: $message \n")
                    streamWriter?.flush()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            })
        }
    }

    fun cleanupLogs() {
        ensureInitied()
        val sdCard = ApplicationLoader.applicationContext?.getExternalFilesDir(null)
        if (sdCard != null) {
            val dir = File(sdCard.absolutePath + "/logs")
            val files = dir.listFiles()
            if (files != null && files.isNotEmpty()) {
                for (i in files.indices) {
                    val file = files[i]
                    if (currentFile != null && file.absolutePath == currentFile?.absolutePath) {
                        continue
                    }
                    file.delete()
                }
            }
        }
    }
}