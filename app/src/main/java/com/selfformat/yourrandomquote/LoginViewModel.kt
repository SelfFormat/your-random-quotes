package com.selfformat.yourrandomquote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.email
import com.google.firebase.database.name
import com.google.firebase.database.quotes
import com.google.firebase.database.users
import com.google.firebase.database.withID
import com.selfformat.yourrandomquote.domain.Quote
import timber.log.Timber

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
            _quotes.value = emptyList()
            UNAUTHENTICATED
        }
    }

    private fun updateOrCreateUser(user: FirebaseUser) {
        database.users().withID(user.uid).name().setValue(user.displayName)
        database.users().withID(user.uid).email().setValue(user.email)
    }

    private fun getUsersRandomQuote(uid: String) {
        val quoteReference: DatabaseReference =
            FirebaseDatabase.getInstance().reference.users().withID(uid).quotes()

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
                Timber.w(databaseError.toException(), "loadPost: onCancelled ")
            }
        }
        quoteReference.addValueEventListener(postListener)
    }
}

sealed class AuthenticationState
data class AUTHENTICATED(val uid: String) : AuthenticationState()
object UNAUTHENTICATED : AuthenticationState()
