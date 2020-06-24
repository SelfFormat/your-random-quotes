package com.selfformat.yourrandomquote.widget

import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.selfformat.yourrandomquote.R
import com.selfformat.yourrandomquote.data.FirebaseQuotesDataSource
import com.selfformat.yourrandomquote.domain.Quote

class SingleQuoteRemoteViewsFactory(
    private val packageName: String,
    private val quotesDataSource: FirebaseQuotesDataSource,
    private val quoteListState: QuoteListState
) : RemoteViewsService.RemoteViewsFactory {
    private companion object {
        const val TAG = "QuoteAdapter"
    }
    private var quotes: List<Quote> = emptyList()

    override fun onCreate() {
    }

    override fun getLoadingView(): RemoteViews? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0L
    }

    override fun onDataSetChanged() {
        this.quotes = try {
            quotesDataSource.allQuotes()
        } catch (ex: Exception) {
            Log.e(TAG, "Failed to load quotes", ex)
            emptyList()
        }
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun getViewAt(position: Int): RemoteViews {
        val quoteText = getRandomQuoteText()
        val remoteView = RemoteViews(
            packageName,
            R.layout.item_list_text_amatic
        )
        remoteView.setTextViewText(android.R.id.text1, quoteText)
        return remoteView
    }

    private fun getRandomQuoteText(): String {
        return if (quotes.isNotEmpty()) {
            val randomQuote = quoteListState.randomize(quotes)
            randomQuote.quote
        } else {
            null
        } ?: ""
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
