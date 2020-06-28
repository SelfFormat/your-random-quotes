package com.selfformat.yourrandomquote

import androidx.lifecycle.ViewModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.quotes
import com.google.firebase.database.users
import com.google.firebase.database.withID
import com.selfformat.yourrandomquote.domain.DatabaseQuote

class AddQuoteViewModel : ViewModel() {

    private var database: DatabaseReference = FirebaseDatabase.getInstance().reference

    fun addQuote(uid: String, databaseQuote: DatabaseQuote) {
        database.users().withID(uid).quotes().push().setValue(databaseQuote)
    }
}
