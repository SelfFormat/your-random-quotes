package com.selfformat.yourrandomquote

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI

class MainActivity : AppCompatActivity() {

    private val navController: NavController
        get() = findNavController(R.id.nav_host_fragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

/*  TODO MVP LIST:
    [x] Make base navigation graph
    [x] Add ability to login with firebase (email auth)
    [x] Extract dependency versions
    [x] Migrate gradle from groovy to kotlin
    [x] Add ability to login with firebase (google account auth)
    [x] Add user to firebase realtime database
    [ ] Add button for adding quotes to main fragment
    [ ] Remove login fragment as AuthUI is sufficient
    [ ] Add fragment for adding quotes to user's database
    [ ] Restrict adding quotes only for logged in user
    [ ] Add recycler view with all user's quotes
    [ ] Populate recyclerview with quotes once user is logged in
    [ ] Display random quote in widget
    [ ] Add refresh option in widget
    [ ] Refresh widget on every screen unlock (if possible or something with similar refresh rate)
    [ ] Polish widget UI
    [ ] Polish recyclerview UI

    TODO ADDONS:
    [ ] add theme and polish AuthUI look
    [ ] removing quotes
    [ ] searching
*/

}
