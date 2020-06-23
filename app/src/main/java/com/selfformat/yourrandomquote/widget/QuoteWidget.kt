package com.selfformat.yourrandomquote.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import android.widget.RemoteViewsService.RemoteViewsFactory
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
        new(context, appWidgetManager, appWidgetIds)
    }

    private fun new(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            val intent = Intent(context, QuoteService::class.java)
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            intent.data = Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME))

            val widget = RemoteViews(context.packageName, R.layout.quote_widget_layout)
            widget.setRemoteAdapter(R.id.listView, intent)
            appWidgetManager.updateAppWidget(appWidgetId, widget)
        }
    }

    class QuoteService : RemoteViewsService() {
        override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory {
            return QuoteRemoteViewsFactory(applicationContext.packageName)
        }
    }

    class QuoteRemoteViewsFactory(private val packageName: String) : RemoteViewsFactory {
        override fun onCreate() {
        }

        override fun getLoadingView(): RemoteViews? {
            return null
        }

        override fun getItemId(position: Int): Long {
            return 0L
        }

        override fun onDataSetChanged() {
        }

        override fun hasStableIds(): Boolean {
            return true
        }

        override fun getViewAt(position: Int): RemoteViews {
            val remoteView = RemoteViews(
                packageName,
                android.R.layout.simple_list_item_1
            )
            remoteView.setTextViewText(android.R.id.text1, """
                Contrary to popular belief, Lorem Ipsum is
                not simply random text.
                It has roots in a piece of classical Latin 
                literature from 45 BC, making it over 2000 years old.
                Richard McClintock, a Latin professor at Hampden-Sydney 
                College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of "de Finibus Bonorum et Malorum" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, "Lorem ipsum dolor sit amet..", comes from a line in section 1.10.32.
                The standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested. Sections 1.10.32 and 1.10.33 from "de Finibus Bonorum et Malorum" by Cicero are also reproduced in their exact original form, accompanied by English versions from the 1914 translation by H. Rackham.
                Contrary to popular belief, Lorem Ipsum is
                not simply random text.
                It has roots in a piece of classical Latin 
                literature from 45 BC, making it over 2000 years old.
                Richard McClintock, a Latin professor at Hampden-Sydney 
                College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of "de Finibus Bonorum et Malorum" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, "Lorem ipsum dolor sit amet..", comes from a line in section 1.10.32.
                The standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested. Sections 1.10.32 and 1.10.33 from "de Finibus Bonorum et Malorum" by Cicero are also reproduced in their exact original form, accompanied by English versions from the 1914 translation by H. Rackham.
                Contrary to popular belief, Lorem Ipsum is
                not simply random text.
                It has roots in a piece of classical Latin 
                literature from 45 BC, making it over 2000 years old.
                Richard McClintock, a Latin professor at Hampden-Sydney 
                College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of "de Finibus Bonorum et Malorum" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, "Lorem ipsum dolor sit amet..", comes from a line in section 1.10.32.
                The standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested. Sections 1.10.32 and 1.10.33 from "de Finibus Bonorum et Malorum" by Cicero are also reproduced in their exact original form, accompanied by English versions from the 1914 translation by H. Rackham.
                Contrary to popular belief, Lorem Ipsum is
                not simply random text.
                It has roots in a piece of classical Latin 
                literature from 45 BC, making it over 2000 years old.
                Richard McClintock, a Latin professor at Hampden-Sydney 
                College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of "de Finibus Bonorum et Malorum" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, "Lorem ipsum dolor sit amet..", comes from a line in section 1.10.32.
                The standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested. Sections 1.10.32 and 1.10.33 from "de Finibus Bonorum et Malorum" by Cicero are also reproduced in their exact original form, accompanied by English versions from the 1914 translation by H. Rackham.
            """.trimIndent())
            return remoteView
        }

        override fun getCount(): Int {
            return 1
        }

        override fun getViewTypeCount(): Int {
            return 1
        }

        override fun onDestroy() {
        }
    }

    private fun old(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            val widget = quoteWidget(context).loadingQuoteWidget()
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
                    Log.i(TAG, "onUpdate: quotes=${quotes}")

                    for (appWidgetId in appWidgetIds) {
                        val randomQuote = QuoteListState.randomize(quotes)
                        val widget = quoteWidget(context).loadedQuoteWidget(context, randomQuote)
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
            //TODO: instead of going through all of id's remember last rendered id  and set it to gone
        }

        val random = Random.nextInt(from = 0, until = textViewsWithDifferentFonts.size)
        setViewVisibility(textViewsWithDifferentFonts[random], View.VISIBLE)
        setTextViewText(textViewsWithDifferentFonts[random], quote.quote)

        setTextViewText(R.id.author, context.getString(R.string.author_in_widget, quote.author))

        setViewVisibility(R.id.refresh, View.VISIBLE)
        setViewVisibility(R.id.progressRefresh, View.GONE)

        return this
    }

    private fun quoteWidget(context: Context): RemoteViews {
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
