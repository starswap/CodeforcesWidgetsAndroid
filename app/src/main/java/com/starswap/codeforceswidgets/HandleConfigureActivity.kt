package com.starswap.codeforceswidgets

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RemoteViews

class HandleConfigureActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.configuration_activity)
        setResult(RESULT_CANCELED)
        val setupWidget = findViewById<View>(R.id.setupWidget) as Button
        setupWidget.setOnClickListener { handleSetupWidget(this) }
    }

    private fun handleSetupWidget(context: Context) {
        showAppWidget(context)
    }

    private var appWidgetId = 0
    private fun showAppWidget(context: Context) {
        appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID
        //Retrieve the App Widget ID from the Intent that launched the Activity//
        val intent = intent
        val extras = intent.extras
        if (extras != null) {
            appWidgetId = extras.getInt(
                AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID
            )
            //If the intent doesn’t have a widget ID, then call finish()//
            if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
                finish()
            }

            Log.d("AppWidgetID", appWidgetId.toString())
            Log.d("InvalidAppWidget", AppWidgetManager.INVALID_APPWIDGET_ID.toString())
            //TO DO, Perform the configuration and get an instance of the AppWidgetManager//
            val appWidgetManager = AppWidgetManager.getInstance(context)
//            val views = RemoteViews(context.packageName, R.layout.timer_widget_start_layout)
//            appWidgetManager.updateAppWidget(appWidgetId, views)
            // we need to call through to the widget provider somehow
            val handle = (findViewById<View>(R.id.handleTextbox) as EditText).text.toString()
            Log.d("CFHANDLE", handle)
            saveHandle(context, appWidgetId, handle)
            updateWidget(context, appWidgetManager, appWidgetId)


            //Create the return intent//
            val resultValue = Intent()
            //Pass the original appWidgetId//
            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            //Set the results from the configuration Activity//
            setResult(RESULT_OK, resultValue)
            //Finish the Activity//
            finish()
        }
    }
}