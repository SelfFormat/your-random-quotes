package com.selfformat.yourrandomquote

import androidx.lifecycle.ViewModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.selfformat.yourrandomquote.domain.DatabaseQuote

class AddQuoteViewModel : ViewModel() {

    private var database: DatabaseReference = FirebaseDatabase.getInstance().reference

    fun addQuote(uid: String, databaseQuote: DatabaseQuote) {
        database.child("users").child(uid).child("quotes").push().setValue(databaseQuote)
    }

    companion object {
        const val TAG = "AddQuoteViewModel"
    }

}
