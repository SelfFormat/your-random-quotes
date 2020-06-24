package com.selfformat.yourrandomquote.widget

import android.content.Intent
import android.widget.RemoteViewsService
import com.selfformat.yourrandomquote.data.FirebaseQuotesDataSource

class QuoteService : RemoteViewsService() {
    private val firebaseQuotesDataSource = FirebaseQuotesDataSource()
    private val quoteListState = QuoteListState()

    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory {
        return SingleQuoteRemoteViewsFactory(
            packageName = applicationContext.packageName,
            quotesDataSource = firebaseQuotesDataSource,
            quoteListState = quoteListState
        )
    }
}
