package ca.cshawi.myinventory

import android.app.Activity
import android.app.Application

class MyInventory : Application() {
    private val activityLifecycleCallback = ActivityLifeCycleCallback()

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(activityLifecycleCallback)
    }

    override fun onTerminate() {
        super.onTerminate()
        unregisterActivityLifecycleCallbacks(activityLifecycleCallback)
    }
  
    companion object {
        var CURRENT: Activity? = null
    }
}