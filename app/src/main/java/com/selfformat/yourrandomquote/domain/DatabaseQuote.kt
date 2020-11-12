package com.selfformat.yourrandomquote.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class DatabaseQuote(
    val quote: String,
    val author: String,
    val category: Quote.Category = Quote.Category.GENERAL,
    val dateAdded: Long = System.currentTimeMillis()
)

@Parcelize
data class Quote(
    val quote: String? = null,
    val author: String? = null,
    val category: Category = Category.GENERAL,
    val dateAdded: Long = System.currentTimeMillis()
) : Parcelable {
    enum class Category {
        GENERAL,
    }
}
