package com.google.firebase.database

fun DatabaseReference.users() : DatabaseReference {
    return child("users")
}