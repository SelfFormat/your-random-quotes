package com.selfformat.yourrandomquote.widget

import com.selfformat.yourrandomquote.R
import com.selfformat.yourrandomquote.domain.Quote
import kotlin.random.Random

object QuoteListState {
    private var lastGeneratedQuote: Int = 0

    // As remote view doesn't have setTextAppearance method, we will use set of pre-styled textViews and pick one random
    val textViewsWithDifferentFonts = listOf(
        R.id.quoteAmaticFont,
        R.id.quoteCormorantFont
    )

    fun randomize(quotes: List<Quote>): Quote {
        return quotes[getRandomQuoteIndex(quotes.size)]
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
}
