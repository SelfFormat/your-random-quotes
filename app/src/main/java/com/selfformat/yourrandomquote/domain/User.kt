package com.selfformat.yourrandomquote.domain

data class User(
    val uid: String,
    val name: String? = null,
    val email: String? = null,
    val quotes: List<Quote>? = emptyList()
)
