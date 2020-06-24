package com.selfformat.yourrandomquote.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.RemoteViews
import com.selfformat.yourrandomquote.R

private const val ACTION_UPDATE_CLICK_NEXT = "action.UPDATE_CLICK_NEXT"
private const val TAG = "QuoteWidget"

class QuoteWidget : AppWidgetProvider() {

    override fun onReceive(context: Context?, intent: Intent) {
        super.onReceive(context, intent)
        if (ACTION_UPDATE_CLICK_NEXT == intent.action) {
            Log.d(TAG, "onReceive: ")
            onUpdate(context!!)
        }
    }

    private fun onUpdate(context: Context) {
        val appWidgetManager = AppWidgetManager.getInstance(context)
        val thisAppWidgetComponentName = ComponentName(context.packageName, javaClass.name)
        val appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidgetComponentName)
        onUpdate(context, appWidgetManager, appWidgetIds)
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        Log.i(TAG, "onUpdate: appWidgetIds=${appWidgetIds.size}")
        createRemoteViews(context, appWidgetIds).forEach { (appWidgetId, widget) ->
            appWidgetManager.updateAppWidget(appWidgetId, widget)
        }
    }

    private fun createRemoteViews(
        context: Context,
        appWidgetIds: IntArray
    ): Map<Int, RemoteViews> {
        return appWidgetIds.map { appWidgetId ->
            val intent = Intent(context, QuoteService::class.java)
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            intent.data = Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME))

            val widget = RemoteViews(context.packageName, R.layout.quote_widget_layout)
            widget.setRemoteAdapter(R.id.listView, intent)
            widget.setOnClickPendingIntent(
                R.id.refresh,
                getPendingSelfIntent(context)
            )
            appWidgetId to widget
        }.toMap()
    }

    private fun getPendingSelfIntent(context: Context): PendingIntent? {
        val intent = Intent(context, javaClass)
        intent.action = ACTION_UPDATE_CLICK_NEXT
        return PendingIntent.getBroadcast(context, 0, intent, 0)
    }
}
