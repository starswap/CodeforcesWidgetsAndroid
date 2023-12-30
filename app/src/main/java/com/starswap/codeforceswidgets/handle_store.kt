package com.starswap.codeforceswidgets

import android.content.Context

const val PREFS_NAME = "com.starswap.codeforceswidgets.configuration"
const val HANDLE_KEY = "handle_to_track"

fun saveHandle(context: Context, appWidgetId: Int, userName: String?) {
    val prefs = context.getSharedPreferences(
        PREFS_NAME, 0
    ).edit()
    prefs.putString(HANDLE_KEY + appWidgetId, userName)
    prefs.commit()
}

fun loadHandle(context: Context, appWidgetId: Int): String? {
    val prefs = context.getSharedPreferences(PREFS_NAME, 0)
    return prefs.getString(HANDLE_KEY + appWidgetId, null)
}