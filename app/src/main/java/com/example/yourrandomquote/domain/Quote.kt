package com.example.yourrandomquote.domain

data class Quote(
    val quote: String,
    val author: Author,
    val category: QuoteCategory
)
