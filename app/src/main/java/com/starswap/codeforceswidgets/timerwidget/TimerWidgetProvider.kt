package com.starswap.codeforceswidgets.timerwidget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.os.SystemClock
import android.widget.RemoteViews
import com.starswap.codeforceswidgets.R
import com.starswap.codeforceswidgets.codeforces.get_user
import com.starswap.codeforceswidgets.codeforces.latest_submissions
import com.starswap.codeforceswidgets.codeforces.render_user
import com.starswap.codeforceswidgets.handle.loadHandle

class TimerWidgetProvider : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        appWidgetIds.forEach { updateOneWidget(context, appWidgetManager, it) }
    }

    companion object {
        fun updateOneWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
            val handle = loadHandle(context, appWidgetId)
            if (handle != null) {
                val remoteViews = RemoteViews(context.packageName, R.layout.timer_widget_start_layout)
                val submissions =  latest_submissions(handle);
                val lastAC = submissions?.firstOrNull { it.verdict == "OK" }

                if (lastAC == null) {

                } else {
                    remoteViews.setChronometerCountDown(R.id.chronometer, false);
                    val elapsedRealtimeOffset = System.currentTimeMillis() - SystemClock.elapsedRealtime()
                    val acTime = (lastAC.creationTimeSeconds * 1000) - elapsedRealtimeOffset
                    remoteViews.setChronometer(R.id.chronometer, acTime, null, true)
                    val user = get_user(handle)
                    if (user != null) {
                        remoteViews.setTextViewText(R.id.handleLabel, render_user(user))
                    }
                }
                appWidgetManager.updateAppWidget(appWidgetId, remoteViews)
            }
        }
    }
}


