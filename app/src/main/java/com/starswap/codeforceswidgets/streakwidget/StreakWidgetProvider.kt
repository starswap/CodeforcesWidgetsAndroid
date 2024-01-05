package com.starswap.codeforceswidgets.streakwidget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.RemoteViews
import com.starswap.codeforceswidgets.R
import com.starswap.codeforceswidgets.codeforces.streak
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
        private val number_to_tally_image = mapOf(
            1 to R.drawable.tally_one,
            2 to R.drawable.tally_two,
            3 to R.drawable.tally_three,
            4 to R.drawable.tally_four,
            5 to R.drawable.tally_five
        )

        private fun updateTallyChart(remoteViews: RemoteViews, tallyChart: Int, number: Int) {
            number_to_tally_image[number]?.let {
                remoteViews.setViewVisibility(tallyChart, VISIBLE)
                remoteViews.setImageViewResource(tallyChart, it)
            } ?: run {
                remoteViews.setViewVisibility(tallyChart, GONE)
            }
        }

        fun updateOneWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
            val handle = loadHandle(context, appWidgetId)
            if (handle != null) {
                val remoteViews = RemoteViews(context.packageName, R.layout.streak_widget_start_layout)
                val (streak, doneToday) = streak(handle);

                remoteViews.setTextViewText(R.id.streak, streak.toString())
                remoteViews.setImageViewResource(R.id.cf_logo, if (doneToday > 0) R.drawable.codeforces_logo_colour else R.drawable.codeforces_logo_bw)

                updateTallyChart(remoteViews, R.id.tally_chart_red, doneToday / 6)
                updateTallyChart(remoteViews, R.id.tally_chart, doneToday % 6)
                appWidgetManager.updateAppWidget(appWidgetId, remoteViews)
            }
        }
    }
}


