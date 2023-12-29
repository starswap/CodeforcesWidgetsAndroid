package com.starswap.codeforceswidgets

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.os.SystemClock
import android.util.Log
import android.widget.Chronometer
import android.widget.RemoteViews

class TimerWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        Log.d("Getting called now", "yeeeee");
        // Perform this loop procedure for each widget that belongs to this
        // provider.
        appWidgetIds.forEach { appWidgetId ->

        //            // Create an Intent to launch ExampleActivity.
//            val pendingIntent: PendingIntent = PendingIntent.getActivity(
//                /* context = */ context,
//                /* requestCode = */  0,
//                /* intent = */ Intent(context, ExampleActivity::class.java),
//                /* flags = */ PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
//            )
//
//            // Get the layout for the widget and attach an onClick listener to
//            // the button
            Log.d("Hopp", "hoppp")
            val remoteViews = RemoteViews(context.packageName, R.layout.timer_widget_start_layout)
            remoteViews.setChronometerCountDown(R.id.chronometer, false);
            remoteViews.setChronometer(R.id.chronometer, SystemClock.elapsedRealtime() - 50000, null, true)
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews)
        }
    }
}