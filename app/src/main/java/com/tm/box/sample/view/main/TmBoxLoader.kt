package com.tm.box.sample.view.main

import android.app.Application
import android.content.Context
import android.util.Log
import java.io.File
import com.tm.box.TmBox
import com.tm.box.app.BActivityThread
import com.tm.box.app.configuration.AppLifecycleCallback
import com.tm.box.app.configuration.ClientConfiguration
import com.tm.box.sample.app.App
import com.tm.box.sample.biz.cache.AppSharedPreferenceDelegate


class TmBoxLoader {

    private var mShowShortcutPermissionDialog by AppSharedPreferenceDelegate(App.getContext(), true)

    fun showShortcutPermissionDialog(): Boolean {
        return try {
            mShowShortcutPermissionDialog
        } catch (e: Exception) {
            Log.e(TAG, "Error getting showShortcutPermissionDialog: ${e.message}")
            true
        }
    }

    fun invalidShortcutPermissionDialog(show: Boolean) {
        try {
            this.mShowShortcutPermissionDialog = show
        } catch (e: Exception) {
            Log.e(TAG, "Error setting showShortcutPermissionDialog: ${e.message}")
        }
    }

    fun getTmBox(): TmBox {
        return try {
            TmBox.get()
        } catch (e: Exception) {
            Log.e(TAG, "Error getting TmBox: ${e.message}")
            throw e
        }
    }

    fun addLifecycleCallback() {
        try {
            TmBox.get()
                    .addAppLifecycleCallback(
                            object : AppLifecycleCallback() {
                                override fun beforeCreateApplication(
                                        packageName: String?,
                                        processName: String?,
                                        context: Context?,
                                        userId: Int
                                ) {
                                    try {
                                        Log.d(
                                                TAG,
                                                "beforeCreateApplication: pkg $packageName, processName $processName,userID:${BActivityThread.getUserId()}"
                                        )
                                    } catch (e: Exception) {
                                        Log.e(TAG, "Error in beforeCreateApplication: ${e.message}")
                                    }
                                }

                                override fun beforeApplicationOnCreate(
                                        packageName: String?,
                                        processName: String?,
                                        application: Application?,
                                        userId: Int
                                ) {
                                    try {
                                        Log.d(
                                                TAG,
                                                "beforeApplicationOnCreate: pkg $packageName, processName $processName"
                                        )
                                    } catch (e: Exception) {
                                        Log.e(
                                                TAG,
                                                "Error in beforeApplicationOnCreate: ${e.message}"
                                        )
                                    }
                                }

                                override fun afterApplicationOnCreate(
                                        packageName: String?,
                                        processName: String?,
                                        application: Application?,
                                        userId: Int
                                ) {
                                    try {
                                        Log.d(
                                                TAG,
                                                "afterApplicationOnCreate: pkg $packageName, processName $processName"
                                        )
                                    } catch (e: Exception) {
                                        Log.e(
                                                TAG,
                                                "Error in afterApplicationOnCreate: ${e.message}"
                                        )
                                    }
                                }

                                override fun onStoragePermissionNeeded(
                                        packageName: String?,
                                        userId: Int
                                ): Boolean {
                                    try {
                                        Log.w(
                                                TAG,
                                                "Storage permission needed for launching: $packageName"
                                        )
                                        
                                        
                                        
                                        val intent =
                                                android.content.Intent(
                                                        "com.tm.box.sample.REQUEST_STORAGE_PERMISSION"
                                                )
                                        intent.putExtra("package_name", packageName)
                                        intent.putExtra("user_id", userId)
                                        intent.setPackage(App.getContext().packageName)
                                        App.getContext().sendBroadcast(intent)
                                        
                                        
                                        
                                        
                                        
                                        return false
                                    } catch (e: Exception) {
                                        Log.e(
                                                TAG,
                                                "Error in onStoragePermissionNeeded: ${e.message}"
                                        )
                                        return false
                                    }
                                }
                            }
                    )
        } catch (e: Exception) {
            Log.e(TAG, "Error adding lifecycle callback: ${e.message}")
        }
    }

    fun attachBaseContext(context: Context) {
        try {
            TmBox.get()
                    .doAttachBaseContext(
                            context,
                            object : ClientConfiguration() {
                                override fun getHostPackageName(): String {
                                    return try {
                                        context.packageName
                                    } catch (e: Exception) {
                                        Log.e(TAG, "Error getting package name: ${e.message}")
                                        "unknown"
                                    }
                                }

                                override fun isHideRoot(): Boolean = false

                                override fun isEnableDaemonService(): Boolean = false

                                override fun isUseVpnNetwork(): Boolean = false

                                override fun isDisableFlagSecure(): Boolean = false

                                override fun requestInstallPackage(
                                        file: File?,
                                        userId: Int
                                ): Boolean {
                                    if (file == null) {
                                        Log.w(TAG, "requestInstallPackage: file is null")
                                    }
                                    return false
                                }
                            }
                    )
        } catch (e: Exception) {
            Log.e(TAG, "Error in attachBaseContext: ${e.message}")
        }
    }

    fun doOnCreate() {
        try {
            TmBox.get().doCreate()

            
            try {
                TmBox.get().addServiceAvailableCallback {
                    Log.d(TAG, "Services became available, triggering app list refresh")
                    
                    
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error registering service available callback: ${e.message}")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error in doOnCreate: ${e.message}")
        }
    }

    companion object {
        val TAG: String = TmBoxLoader::class.java.simpleName
    }
}
