package com.selfformat.yourrandomquote.domain

data class Quote(
    val quote: String,
    val author: String,
    val category: Category
) {
    companion object {
        val sampleQuote = Quote(
            quote = "Niektórym ludziom nie da się nic powiedzieć, jeśli już tego nie wiedzą",
            author = "Yogi Berra",
            category = Category.GENERAL
        )
    }

    enum class Category {
        GENERAL,
    }
}

