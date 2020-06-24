package com.selfformat.yourrandomquote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.selfformat.yourrandomquote.data.FirebaseQuotesDataSource
import com.selfformat.yourrandomquote.domain.Quote

class LoginViewModel : ViewModel() {

    private var database: DatabaseReference = FirebaseDatabase.getInstance().reference
    private val quotesDataSource = FirebaseQuotesDataSource()

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

            quotesDataSource.allQuotes(onSuccess = { listOfQuotes ->
                _quotes.value = listOfQuotes
            })
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
}

sealed class AuthenticationState
data class AUTHENTICATED(val uid: String) : AuthenticationState()
object UNAUTHENTICATED : AuthenticationState()
