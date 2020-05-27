package com.selfformat.yourrandomquote.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log
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
private const val TAG = "QuoteWidget"
private const val notWorking = "notWorking"

class QuoteWidget : AppWidgetProvider() {

    var quoteList: MutableList<String> = mutableListOf()


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
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            quoteList.clear()
            val auth = FirebaseAuth.getInstance()
            val userId = auth.currentUser!!.uid //TODO: remove !! as it may be null
            val database = FirebaseDatabase.getInstance().reference.child("users").child(userId)
                .child("quotes")
            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (snapshot in dataSnapshot.children) {
                        val post = snapshot.getValue(Quote::class.java)
                        quoteList.add(post!!.quote!!)
                    }
                    val random = Random.nextInt(from = 0, until = quoteList.size)
                    updateAppWidget(context, appWidgetManager, appWidgetId, quoteList[random])
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })
            updateAppWidget(context, appWidgetManager, appWidgetId, notWorking)
        }
    }

    private fun updateAppWidget(
        context: Context, appWidgetManager: AppWidgetManager,
        appWidgetId: Int, text: String
    ) {
        initializeData()
        val views: RemoteViews = RemoteViews(
            context.packageName,
            R.layout.quote_widget_layout
        ).apply {
            setOnClickPendingIntent(
                R.id.refresh,
                getPendingSelfIntent(context, ACTION_UPDATE_CLICK_NEXT)
            )
            //TODO: change to different check to not set unneeded value
            if (text != notWorking) {
                setTextViewText(R.id.quote, text)
            }
        }
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }

    private fun getPendingSelfIntent(context: Context, action: String): PendingIntent? {
        val intent = Intent(context, javaClass)
        intent.action = action
        return PendingIntent.getBroadcast(context, 0, intent, 0)
    }

    @Throws(NullPointerException::class)
    private fun initializeData() {
        try {
            quoteList.clear()
            val auth = FirebaseAuth.getInstance()
            val userId = auth.currentUser!!.uid
            val database = FirebaseDatabase.getInstance().reference.child("users").child(userId)
                .child("quotes")
            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (snapshot in dataSnapshot.children) {
                        val post = snapshot.getValue(Quote::class.java)
                        quoteList.add(post!!.quote!!)
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }
    }
}
