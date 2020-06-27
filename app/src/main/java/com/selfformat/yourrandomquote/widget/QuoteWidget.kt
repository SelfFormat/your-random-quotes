package com.selfformat.yourrandomquote.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.RemoteViews
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.selfformat.yourrandomquote.R
import com.selfformat.yourrandomquote.domain.Quote
import com.selfformat.yourrandomquote.widget.QuoteListState.textViewsWithDifferentFonts
import kotlin.random.Random

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
        for (appWidgetId in appWidgetIds) {
            val widget = mainWidget(context).loadingQuoteWidget()
            appWidgetManager.updateAppWidget(appWidgetId, widget)
        }

        val auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        if (user != null) {
            val userId = user.uid
            val database = FirebaseDatabase.getInstance()
                .reference
                .users()
                .withID(userId)
                .quotes()
            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val quotes = dataSnapshot.children.mapNotNull { snapshot ->
                        snapshot.getValue(Quote::class.java)
                    }
                    Log.i(TAG, "onUpdate: quotes=$quotes")

                    for (appWidgetId in appWidgetIds) {
                        val randomQuote = QuoteListState.randomize(quotes)
                        val widget = mainWidget(context).loadedQuoteWidget(context, randomQuote)
                        appWidgetManager.updateAppWidget(appWidgetId, widget)
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })
        }
    }

    private fun RemoteViews.loadingQuoteWidget(): RemoteViews {
        setViewVisibility(R.id.refresh, View.GONE)
        setViewVisibility(R.id.progressRefresh, View.VISIBLE)
        return this
    }

    private fun RemoteViews.loadedQuoteWidget(context: Context, quote: Quote): RemoteViews {
        setOnClickPendingIntent(
            R.id.refresh,
            getPendingSelfIntent(context)
        )

        for (text in textViewsWithDifferentFonts) {
            setViewVisibility(text, View.GONE)
        }

        val random = Random.nextInt(from = 0, until = textViewsWithDifferentFonts.size)
        setViewVisibility(textViewsWithDifferentFonts[random], View.VISIBLE)
        setTextViewText(textViewsWithDifferentFonts[random], quote.quote)

        setTextViewText(R.id.author, context.getString(R.string.author_in_widget, quote.author))

        setViewVisibility(R.id.refresh, View.VISIBLE)
        setViewVisibility(R.id.progressRefresh, View.GONE)

        return this
    }

    private fun mainWidget(context: Context): RemoteViews {
        return RemoteViews(
            context.packageName,
            R.layout.quote_widget_layout
        )
    }

    private fun getPendingSelfIntent(context: Context): PendingIntent? {
        val intent = Intent(context, javaClass)
        intent.action = ACTION_UPDATE_CLICK_NEXT
        return PendingIntent.getBroadcast(context, 0, intent, 0)
    }
}
