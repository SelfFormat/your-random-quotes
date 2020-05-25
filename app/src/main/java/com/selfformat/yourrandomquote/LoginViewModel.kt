package com.selfformat.yourrandomquote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.selfformat.yourrandomquote.domain.Quote

class LoginViewModel : ViewModel() {

    private var database: DatabaseReference = FirebaseDatabase.getInstance().reference

    companion object {
        const val TAG = "LoginViewModel"
        var uid: String? = null
    }

    private val _quotes = MutableLiveData<List<Quote>>()
    val quotes: LiveData<List<Quote>>
        get() = _quotes

    val authenticationState = FirebaseUserLiveData().map { user ->
        if (user != null) {
            updateOrCreateUser(user)
            uid = user.uid
            getUsersRandomQuote(user.uid)
            AUTHENTICATED(user.uid)
        } else {
            uid = null
            UNAUTHENTICATED
        }
    }

    private fun updateOrCreateUser(user: FirebaseUser) {
        database.child("users").child(user.uid).child("name").setValue(user.displayName)
        database.child("users").child(user.uid).child("email").setValue(user.email)
    }

    private fun getUsersRandomQuote(uid: String) {
        val quoteReference: DatabaseReference =
            FirebaseDatabase.getInstance().reference.child("users").child(uid)
                .child("quotes")

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val listOfQuotes = mutableListOf<Quote>()
                for (snapshot in dataSnapshot.children) {
                    val post = snapshot.getValue(Quote::class.java)
                    listOfQuotes.add(post!!)
                }
                _quotes.value = listOfQuotes
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        quoteReference.addValueEventListener(postListener)
    }
}

sealed class AuthenticationState
data class AUTHENTICATED(val uid: String) : AuthenticationState()
object UNAUTHENTICATED : AuthenticationState()