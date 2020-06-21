package com.google.firebase.database

import com.selfformat.yourrandomquote.LoginViewModel

fun DatabaseReference.users() : DatabaseReference {
    return child("users")
}