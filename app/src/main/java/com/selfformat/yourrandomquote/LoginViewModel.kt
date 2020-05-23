package com.selfformat.yourrandomquote

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.google.firebase.database.*
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

    fun getUsersRandomQuote(uid: String) {
        val quoteReference: DatabaseReference =
            FirebaseDatabase.getInstance().reference.child("users").child(uid).child("quotes")

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val quote = dataSnapshot.value
                //TODO: update UI with data
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        quoteReference.addValueEventListener(postListener)
    }
}
