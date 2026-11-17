package com.tm.box.sample.app

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.tm.box.sample.view.main.TmBoxLoader


object AppManager {
    private const val TAG = "AppManager"

    @JvmStatic
    val mTmBoxLoader by lazy {
        try {
            TmBoxLoader()
        } catch (e: Exception) {
            Log.e(TAG, "Error creating TmBoxLoader: ${e.message}")

            TmBoxLoader() 
        }
    }

    @JvmStatic
    val mTmBox by lazy {
        try {
            mTmBoxLoader.getTmBox()
        } catch (e: Exception) {
            Log.e(TAG, "Error getting TmBox: ${e.message}")
            throw e 
        }
    }

    @JvmStatic
    val mRemarkSharedPreferences: SharedPreferences by lazy {
        try {
            App.getContext().getSharedPreferences("UserRemark", Context.MODE_PRIVATE)
        } catch (e: Exception) {
            Log.e(TAG, "Error creating SharedPreferences: ${e.message}")
            throw e 
        }
    }

    fun doAttachBaseContext(context: Context) {
        try {
            mTmBoxLoader.attachBaseContext(context)
            mTmBoxLoader.addLifecycleCallback()
        } catch (e: Exception) {
            Log.e(TAG, "Error in doAttachBaseContext: ${e.message}")
            
        }
    }

    fun doOnCreate() {
        try {
            mTmBoxLoader.doOnCreate()
        } catch (e: Exception) {
            Log.e(TAG, "Error in doOnCreate: ${e.message}")
        }
    }
}
