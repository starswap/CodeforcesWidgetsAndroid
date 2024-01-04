package com.starswap.codeforceswidgets.streakwidget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.net.Uri
import android.os.SystemClock
import android.widget.RemoteViews
import com.starswap.codeforceswidgets.R
import com.starswap.codeforceswidgets.codeforces.get_user
import com.starswap.codeforceswidgets.codeforces.latest_submissions
import com.starswap.codeforceswidgets.codeforces.render_user
import com.starswap.codeforceswidgets.handle.loadHandle

class StreakWidgetProvider : AppWidgetProvider() {
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
                val remoteViews = RemoteViews(context.packageName, R.layout.streak_widget_start_layout)
                val (streak, doneToday) = Pair(10, false)// streak(handle);

                remoteViews.setTextViewText(R.id.streak, streak.toString())
                remoteViews.setImageViewResource(R.id.cf_logo, if (doneToday) R.drawable.codeforces_logo_colour else R.drawable.codeforces_logo_bw)
                appWidgetManager.updateAppWidget(appWidgetId, remoteViews)
            }
        }
    }
}


