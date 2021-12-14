package com.aadarshkt.eloquence.providers

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.util.Log
import android.widget.RemoteViews
import com.aadarshkt.eloquence.R
import com.google.assistant.appactions.widgets.AppActionsWidgetExtension


class WordWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    val widgetText = context.getString(R.string.word)
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.word_widget)
    views.setTextViewText(R.id.appwidget_text, widgetText)

    val optionsBundle = appWidgetManager.getAppWidgetOptions(appWidgetId)
    val bii =
        optionsBundle.getString(AppActionsWidgetExtension.EXTRA_APP_ACTIONS_BII) // "actions.intent.CREATE_TAXI_RESERVATION"

    val params = optionsBundle.getBundle(AppActionsWidgetExtension.EXTRA_APP_ACTIONS_PARAMS)


    Log.d("Widget-intent", bii.toString())
    Log.d("Widget-intent", params.toString())

    if (params != null && params.containsKey("name") && params.containsKey("sentence")) {
        val wordName = params.getString("name")
        val sentence = params.getString("sentence")

        Log.d("Widget-intent", wordName.toString())
        Log.d("Widget-intent", sentence.toString())


        // Build your RemoteViews with the extracted BII parameter
        // ...
    }

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}