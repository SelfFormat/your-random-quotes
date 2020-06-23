package com.selfformat.yourrandomquote.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.selfformat.yourrandomquote.domain.Quote

class FirebaseQuotesDataSource {
    private val firebaseAuth get() = FirebaseAuth.getInstance()
    private val firebaseDatabase get() = FirebaseDatabase.getInstance()

    fun allQuotes() : List<Quote> {
        val user = firebaseAuth.currentUser
        return if (user == null) {
            emptyList()
        } else {
            loadAllQuotes(user.uid)
        }
    }

    private fun loadAllQuotes(userId: String): List<Quote> {
        val databaseRef = firebaseDatabase.reference.apply {
            users()
            withID(userId)
            quotes()
        }
        val dataSnapshot = databaseRef.getBlocking()
        return dataSnapshot.children.mapNotNull { snapshot ->
            snapshot.getValue(Quote::class.java)
        }
    }
}
