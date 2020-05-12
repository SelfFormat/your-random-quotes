package com.example.yourrandomquote.domain

data class User(
    val firstName: String,
    val surname: String,
    val email: String,
    val password: String,
    val quotes: List<Quote>
)