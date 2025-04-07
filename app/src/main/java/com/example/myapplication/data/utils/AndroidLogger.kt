package com.example.myapplication.data.utils

import android.util.Log

interface Logger {
    fun e(tag: String, message: String, throwable: Throwable? = null)
}

class AndroidLogger : Logger {
    override fun e(tag: String, message: String, throwable: Throwable?) {
        Log.e(tag, message, throwable)
    }
}
