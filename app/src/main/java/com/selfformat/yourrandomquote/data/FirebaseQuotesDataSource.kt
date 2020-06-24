package com.selfformat.yourrandomquote.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.selfformat.yourrandomquote.domain.Quote

class FirebaseQuotesDataSource {
    private val firebaseAuth get() = FirebaseAuth.getInstance()
    private val firebaseDatabase get() = FirebaseDatabase.getInstance()

    fun allQuotes(): List<Quote> {
        val user = firebaseAuth.currentUser
        return if (user == null) {
            emptyList()
        } else {
            val databaseRef = allQuotesQuery(user)
            val dataSnapshot = databaseRef.getBlocking()
            return mapToQuotes(dataSnapshot)
        }
    }

    fun allQuotes(
        onSuccess: (List<Quote>) -> Unit = {},
        onError: (Throwable) -> Unit = {}
    ) {
        val user = firebaseAuth.currentUser
        if (user == null) {
            onSuccess(emptyList())
        } else {
            val postListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    onSuccess(mapToQuotes(dataSnapshot))
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    onError(databaseError.toException())
                }
            }
            val databaseRef = allQuotesQuery(user)
            databaseRef.addValueEventListener(postListener)
        }
    }

    private fun allQuotesQuery(user: FirebaseUser): DatabaseReference {
        return firebaseDatabase.reference
            .users()
            .withID(user.uid)
            .quotes()
    }

    private fun mapToQuotes(dataSnapshot: DataSnapshot): List<Quote> {
        return dataSnapshot.children.mapNotNull { snapshot ->
            snapshot.getValue(Quote::class.java)
        }
    }
}
