package com.selfformat.yourrandomquote.domain

data class Quote(
    val quote: String,
    val author: Author,
    val category: Category
) {
    companion object {
        val sampleQuote = Quote(
            quote = "Niektórym ludziom nie da się nic powiedzieć, jeśli już tego nie wiedzą",
            author = Author(firstName = "Yogi", surname = "Berra"),
            category = Category.GENERAL
        )
    }

    enum class Category {
        GENERAL,
    }
}

