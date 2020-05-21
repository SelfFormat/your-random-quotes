package com.selfformat.yourrandomquote.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.selfformat.yourrandomquote.MainActivity
import com.selfformat.yourrandomquote.R

class QuoteWidget : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(
                context,
                appWidgetManager,
                appWidgetId
            )
        }
    }

    companion object {
        //TODO: Change pending intent
        private fun constructPendingIntent(context: Context): PendingIntent {
            return Intent(context, MainActivity::class.java)
                .let { intent ->
                    PendingIntent.getActivity(context, 0, intent, 0)
                }
        }

        internal fun updateAppWidget(
            context: Context, appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {
            val views: RemoteViews = RemoteViews(
                context.packageName,
                R.layout.quote_widget_layout
            ).apply {
                setOnClickPendingIntent(
                    R.id.refresh,
                    constructPendingIntent(
                        context
                    )
                )
                //TODO: set random quote from user database
                setTextViewText(R.id.quote, "test overriden")
            }
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}
