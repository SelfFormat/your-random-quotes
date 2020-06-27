package com.selfformat.yourrandomquote

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeAuthenticationState()
        logInSignUpButton.setOnClickListener {
            launchSignInFlow()
        }

        viewModel.quotes.observe(viewLifecycleOwner, Observer { quotes ->
            val quoteList = quotes.flatMap { arrayListOf(it.quote) }
            val adapter = ArrayAdapter<String>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                quoteList
            )
            listView.adapter = adapter
            adapter.notifyDataSetChanged() // TODO: How to notify after logout to display empty list?
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SIGN_IN_RESULT_CODE) {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) {
                Log.i(TAG, "Successfully signed in user ${FirebaseAuth.getInstance().currentUser?.displayName}!")
            } else {
                Log.i(TAG, "Sign in unsuccessful ${response?.error?.errorCode}")
            }
        }
    }

    private fun observeAuthenticationState() {
        viewModel.authenticationState.observe(viewLifecycleOwner, Observer { authenticationState ->
            when (authenticationState) {
                is AUTHENTICATED -> {
                    welcome.text = showPersonalizedWelcomeMessage(getString(R.string.welcome))
                    logInSignUpButton.text = getString(R.string.sign_out)
                    logInSignUpButton.setOnClickListener {
                        AuthUI.getInstance().signOut(requireContext())
                    }
                    fab.visibility = View.VISIBLE
                    fab.setOnClickListener(navigateToAddQuoteFragment())
                }
                else -> {
                    welcome.text = getString(R.string.please_log_in)
                    logInSignUpButton.text = getString(R.string.log_in_sign_up)
                    logInSignUpButton.setOnClickListener {
                        launchSignInFlow()
                    }
                    fab.visibility = View.GONE
                }
            }
        })
    }

    private fun showPersonalizedWelcomeMessage(message: String): String {
        return String.format(
            resources.getString(
                R.string.welcome_message_authed,
                FirebaseAuth.getInstance().currentUser?.displayName,
                message
            )
        )
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
        const val TAG = "MainFragment"
        const val SIGN_IN_RESULT_CODE = 1001
    }

    private fun navigateToAddQuoteFragment() =
        Navigation.createNavigateOnClickListener(R.id.action_mainFragment_to_addQuoteFragment, null)
}
