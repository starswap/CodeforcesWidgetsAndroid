package com.starswap.codeforceswidgets

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.os.SystemClock
import android.util.Log
import android.widget.RemoteViews

class TimerWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        appWidgetIds.forEach { updateWidget(context, appWidgetManager, it) }
    }
}

fun updateWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
    Log.d("TimerWidgetProvider", "Updating Widget $appWidgetId")
    //            // Create an Intent to launch ExampleActivity.
//            val pendingIntent: PendingIntent = PendingIntent.getActivity(
//                /* context = */ context,
//                /* requestCode = */  0,
//                /* intent = */ Intent(context, ExampleActivity::class.java),
//                /* flags = */ PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
//            )
//
    val handle = loadHandle(context, appWidgetId)
    if (handle != null) {
        Log.d("TimerWidgetProvider", "Got handle $handle")
        val remoteViews = RemoteViews(context.packageName, R.layout.timer_widget_start_layout)

        remoteViews.setChronometerCountDown(R.id.chronometer, false);
        remoteViews.setChronometer(R.id.chronometer, SystemClock.elapsedRealtime() - 50000, null, true)
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews)
    }
}