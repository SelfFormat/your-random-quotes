package com.selfformat.yourrandomquote.widget

import com.selfformat.yourrandomquote.domain.Quote
import kotlin.random.Random

class QuoteListState {
    private var lastGeneratedQuote: Int = 0

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
