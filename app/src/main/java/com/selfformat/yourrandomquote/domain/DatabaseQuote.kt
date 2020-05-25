package com.selfformat.yourrandomquote.domain

data class DatabaseQuote(
    val quote: String,
    val author: String,
    val category: Quote.Category = Quote.Category.GENERAL,
    val dateAdded: Long = System.currentTimeMillis()
)

data class Quote(
    val quote: String? = null,
    val author: String? = null,
    val category: Category = Category.GENERAL,
    val dateAdded: Long = System.currentTimeMillis()
) {
    enum class Category {
        GENERAL,
    }
}
