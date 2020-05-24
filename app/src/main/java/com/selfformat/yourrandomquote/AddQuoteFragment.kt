package com.selfformat.yourrandomquote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.selfformat.yourrandomquote.domain.Quote
import kotlinx.android.synthetic.main.add_quote_fragment.*

class AddQuoteFragment : Fragment() {

    private val quoteViewModel: AddQuoteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_quote_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        addQuoteButton.setOnClickListener {
            if (validationSucceeded()) {
                LoginViewModel.uid?.let {
                    quoteViewModel.addQuote(it, prepareQuoteFromInput())
                }
                findNavController().navigateUp()
            }
        }
    }

    private fun prepareQuoteFromInput() : Quote {
        return Quote.sampleQuote.copy(quote = quoteTextField.editText?.text.toString(), author = authorFirstNameTextField.editText?.text.toString()) //TODO: Change from sample to real quote from edittext fields
    }

    private fun validationSucceeded(): Boolean {
        if (quoteTextField.editText?.text.isNullOrBlank() || authorFirstNameTextField.editText?.text.isNullOrBlank()) {
            quoteTextField.error = "You need to fill every field"
            authorFirstNameTextField.error = "You need to fill every field"
            return false
        }

        return true
    }

}
