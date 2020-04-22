package ca.cshawi.myinventory

import android.app.Activity
import android.app.Application
import android.os.Bundle

class ActivityLifeCycleCallback() :
    Application.ActivityLifecycleCallbacks {
    override fun onActivityPaused(activity: Activity?) {}

    override fun onActivityResumed(activity: Activity?) {}

    override fun onActivityStarted(activity: Activity?) {
        MyInventory.CURRENT = activity
    }

    override fun onActivityDestroyed(activity: Activity?) {}

    override fun onActivitySaveInstanceState(activity: Activity?, p1: Bundle?) {}

    override fun onActivityStopped(activity: Activity?) {}

    override fun onActivityCreated(activity: Activity?, p1: Bundle?) {}
}