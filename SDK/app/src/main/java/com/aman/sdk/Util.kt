package com.aman.sdk

import android.app.Application
import android.content.Context
import android.content.Intent

object Util {

    private lateinit var applicationContext: Application
    private lateinit var mOnConnected: () -> Unit
    private lateinit var mOnDisconnected: () -> Unit

    fun init(applicationContext: Context) {
        this.applicationContext = applicationContext as Application
    }

    fun startWifiService() {
        val intent = Intent(this.applicationContext, WifiActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }

        this.applicationContext.startActivity(intent)
    }

    fun setOnConnected(onConnected: () -> Unit): Util {
        mOnConnected = onConnected
        return this
    }

    fun setOnDisconnected(onDisconnected: () -> Unit): Util {
        mOnDisconnected = onDisconnected
        return this
    }

    @JvmStatic
    fun onDisconnected() {
        mOnDisconnected()
    }

    @JvmStatic
    fun onConnected() {
        mOnConnected()
    }
}