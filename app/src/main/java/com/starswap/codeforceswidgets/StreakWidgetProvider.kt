package com.starswap.codeforceswidgets

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews

class StreakWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // Perform this loop procedure for each widget that belongs to this
        // provider.
        appWidgetIds.forEach { appWidgetId ->
//            if (appWidgetManager.)
        //            // Create an Intent to launch ExampleActivity.
//            val pendingIntent: PendingIntent = PendingIntent.getActivity(
//                /* context = */ context,
//                /* requestCode = */  0,
//                /* intent = */ Intent(context, ExampleActivity::class.java),
//                /* flags = */ PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
//            )
//
//            // Get the layout for the widget and attach an onClick listener to
//            // the button.
//            val views: RemoteViews = RemoteViews(
//                context.packageName,
//                R.layout.appwidget_provider_layout
//            ).apply {
//                setOnClickPendingIntent(R.id.button, pendingIntent)
//            }
//
//            // Tell the AppWidgetManager to perform an update on the current
//            // widget.
//            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}