package com.starswap.codeforceswidgets.handle

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.starswap.codeforceswidgets.R
import com.starswap.codeforceswidgets.timerwidget.updateWidget

class HandleConfigureActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        /* Render the configuration screen */
        super.onCreate(savedInstanceState)
        setContentView(R.layout.configuration_activity)

        /* In case the user backs out of the configuration */
        setResult(RESULT_CANCELED)

        /* Wait until the user has typed in the handle and pressed the button to configure */
        val setupWidget = findViewById<View>(R.id.setupWidget) as Button
        setupWidget.setOnClickListener { handleSetupWidget(this) }
    }

    private fun handleSetupWidget(context: Context) {

        /* Retrieve the App Widget ID from the Intent that launched the Activity */
        val intent = intent
        val extras = intent.extras
        if (extras != null) {
            val appWidgetId = extras.getInt(
                AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID
            )

            /* If the intent doesn’t have a widget ID, then failed */
            if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
                finish()
            }

            /* Get an instance of the AppWidgetManager */
            val appWidgetManager = AppWidgetManager.getInstance(context)

            /* Save the handle that the user asked for into local storage for the widget */
            val handle = (findViewById<View>(R.id.handleTextbox) as EditText).text.toString()
            saveHandle(context, appWidgetId, handle)

            /* Start a thread to render the widget */
            val thread = Thread { updateWidget(context, appWidgetManager, appWidgetId) }
            thread.start()

            /* We are done here so create the return intent */
            val resultValue = Intent()
            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId) // Pass the original appWidgetId
            setResult(RESULT_OK, resultValue) // Configuration succeeded
            finish()
        }
    }
}