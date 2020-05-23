package com.selfformat.yourrandomquote

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
    [ ] Add ability to login with firebase (google account auth)
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
