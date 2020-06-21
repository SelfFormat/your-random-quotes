package com.google.firebase.database

fun DatabaseReference.users() : DatabaseReference {
    return child("users")
}

fun DatabaseReference.quotes() : DatabaseReference {
    return child("quotes")
}

fun DatabaseReference.name() : DatabaseReference {
    return child("name")
}

fun DatabaseReference.email() : DatabaseReference {
    return child("email")
}

fun DatabaseReference.withID(id: String) : DatabaseReference {
    return child(id)
}