package com.selfformat.yourrandomquote

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

/*  TODO MVP LIST:
    [ ] Make navigation graph (login -> quote lists -> Adding quotes)
    [ ] Add ability to login with firebase (google/email auth)
    [ ] Fetch user data from firebase
    [ ] Populate recyclerview with quotes
    [ ] Display random quote in widget
    [ ] Add refresh option in widget
    [ ] Refresh widget on every screen unlock (if possible or something with similar refresh rate)
    [ ] Polish widget UI
    [ ] Polish recyclerview UI

    TODO ADDONS:
    [ ] removing quotes
    [ ] searching
*/


}
