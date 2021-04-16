package com.zyh.permission

import android.app.Activity

object FreePermission {
    fun bind(activity: Activity) {
        val activityName: String = activity::class.java.canonicalName
        val delegateName = activityName + "_Permission"
        try {
            val forName = Class.forName(delegateName)
            val inject = forName.getConstructor().newInstance()
            val method = inject.javaClass.getDeclaredMethod("permissionClick", activity.javaClass)
            method.invoke(inject, activity)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}