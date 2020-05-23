package com.selfformat.yourrandomquote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.selfformat.yourrandomquote.domain.User

class LoginViewModel : ViewModel() {

    private var database: DatabaseReference = FirebaseDatabase.getInstance().reference

    companion object {
        const val TAG = "LoginViewModel"
    }

    enum class AuthenticationState {
        AUTHENTICATED, UNAUTHENTICATED
    }

    val authenticationState = FirebaseUserLiveData().map { user ->
        if (user != null) {
            AuthenticationState.AUTHENTICATED
        } else {
            AuthenticationState.UNAUTHENTICATED
        }
    }

    fun addUserToDatabase(user: User) {
        database.child("users").child(user.uid).setValue(user)
    }

}
