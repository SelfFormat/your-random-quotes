package com.selfformat.yourrandomquote

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.main_fragment.*
import timber.log.Timber

class MainFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeAuthenticationState()

        viewModel.quotes.observe(viewLifecycleOwner, Observer { quotes ->
            val adapter = QuotesListAdapter(quotes)
            quotesRecyclerView.adapter = adapter
            adapter.notifyDataSetChanged()
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SIGN_IN_RESULT_CODE) {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) {
                Timber.i("Successfully signed in user ${FirebaseAuth.getInstance().currentUser?.displayName}!")
            } else {
                Timber.i("Sign in unsuccessful ${response?.error?.errorCode}")
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.homepage_signup_menu, menu)
        val signUp = menu.findItem(R.id.signUpMenuButton)
        val signOut = menu.findItem(R.id.signOutMenuButton)

        if (viewModel.authenticationState.value is AUTHENTICATED) {
            signOut.isVisible = true
            signUp.isVisible = false
        } else {
            signOut.isVisible = false
            signUp.isVisible = true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        observeAuthenticationState()
        return when (item.itemId) {
            R.id.signUpMenuButton -> {
                launchSignInFlow()
                true
            }
            R.id.signOutMenuButton -> {
                AuthUI.getInstance().signOut(requireContext())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun observeAuthenticationState() {
        viewModel.authenticationState.observe(viewLifecycleOwner, Observer { authenticationState ->
            when (authenticationState) {
                is AUTHENTICATED -> {
                    fab.visibility = View.VISIBLE
                    fab.setOnClickListener(navigateToAddQuoteFragment())
                    activity?.invalidateOptionsMenu()
                }
                else -> {
                    fab.visibility = View.GONE
                    activity?.invalidateOptionsMenu()
                }
            }
        })
    }

    private fun launchSignInFlow() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )

        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setTheme(R.style.Theme_MyApp)
                .setAvailableProviders(providers)
                .build(),
            SIGN_IN_RESULT_CODE
        )
    }

    companion object {
        const val SIGN_IN_RESULT_CODE = 1001
    }

    private fun navigateToAddQuoteFragment() =
        Navigation.createNavigateOnClickListener(R.id.action_mainFragment_to_quoteDetailsFragment, null)
}
