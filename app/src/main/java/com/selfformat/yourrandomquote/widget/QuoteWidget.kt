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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.selfformat.yourrandomquote.R
import com.selfformat.yourrandomquote.domain.Quote
import kotlin.random.Random

private const val ACTION_UPDATE_CLICK_NEXT = "action.UPDATE_CLICK_NEXT"
private const val notWorking = "notWorking"
private val textViewsWithDifferentFonts = listOf(R.id.quoteAmaticFont, R.id.quoteCormorantFont)             //As remote view doesn't have setTextAppearance method, we will use set of pre-styled textViews and pick one random

class QuoteWidget : AppWidgetProvider() {

    companion object {
        var lastGeneratedQuote: Int = 0
        private const val TAG = "QuoteWidget"
        private const val PATH_USERS = "users"
        private const val PATH_QUOTES = "quotes"
    }

    var quoteList: MutableList<Quote> = mutableListOf()

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
        for (appWidgetId in appWidgetIds) {
            updateLoading(context, appWidgetManager, appWidgetId)
            quoteList.clear()
            val auth = FirebaseAuth.getInstance()
            val user = auth.currentUser
            if (user != null) {
                val userId = user.uid

                val database = FirebaseDatabase.getInstance().reference.child(PATH_USERS).child(userId)
                    .child(PATH_QUOTES)
                database.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        quoteList.clear()
                        for (snapshot in dataSnapshot.children) {
                            val post = snapshot.getValue(Quote::class.java)
                            quoteList.add(post!!)
                        }
                        val randomQuote = quoteList[getRandomQuoteIndex(quoteList.size)]
                        updateAppWidget(
                            context = context,
                            appWidgetManager = appWidgetManager,
                            appWidgetId = appWidgetId,
                            author = randomQuote.author!!,
                            quote = randomQuote.quote!!
                        )
                    }

                    override fun onCancelled(databaseError: DatabaseError) {}
                })
            }
            updateAppWidget(context, appWidgetManager, appWidgetId, notWorking, notWorking)
        }
    }

    private fun getRandomQuoteIndex(size: Int): Int {
        var random = Random.nextInt(
            from = 0,
            until = size
        )
        while (random == lastGeneratedQuote) {
            random = Random.nextInt(from = 0, until = size)
        }
        lastGeneratedQuote = random
        return lastGeneratedQuote
    }

    private fun updateLoading(
        context: Context, appWidgetManager: AppWidgetManager,
        appWidgetId: Int
    ) {
        val views: RemoteViews = RemoteViews(
            context.packageName,
            R.layout.quote_widget_layout
        ).apply {
            setViewVisibility(R.id.refresh, View.GONE)
            setViewVisibility(R.id.progressRefresh, View.VISIBLE)
        }
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }

    private fun updateAppWidget(
        context: Context, appWidgetManager: AppWidgetManager,
        appWidgetId: Int, author: String, quote: String
    ) {
        val views: RemoteViews = RemoteViews(
            context.packageName,
            R.layout.quote_widget_layout
        ).apply {
            setOnClickPendingIntent(
                R.id.refresh,
                getPendingSelfIntent(context, ACTION_UPDATE_CLICK_NEXT)
            )

            for (text in textViewsWithDifferentFonts) {
                setViewVisibility(text, View.GONE)
                //TODO: instead of going through all of id's remember last rendered id  and set it to gone
            }

            val random = Random.nextInt(from = 0, until = textViewsWithDifferentFonts.size)
            if (quote != notWorking) {
                setViewVisibility(textViewsWithDifferentFonts[random], View.VISIBLE)
                setTextViewText(textViewsWithDifferentFonts[random], quote)
            }
            if (author != notWorking) {
                setTextViewText(R.id.author, context.getString(R.string.author_in_widget, author))
            }
            setViewVisibility(R.id.refresh, View.VISIBLE)
            setViewVisibility(R.id.progressRefresh, View.GONE)
        }
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }

    private fun getPendingSelfIntent(context: Context, action: String): PendingIntent? {
        val intent = Intent(context, javaClass)
        intent.action = action
        return PendingIntent.getBroadcast(context, 0, intent, 0)
    }
}
