package com.selfformat.yourrandomquote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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
                prepareQuoteFromInput()
                LoginViewModel.uid?.let {
                    quoteViewModel.addQuote(it, prepareQuoteFromInput())
                }
            }
        }
    }

    private fun prepareQuoteFromInput() : Quote {
        return Quote.sampleQuote.copy(quote = "overriden successfuly") //TODO: Change from sample to real quote from edittext fields
    }

    private fun validationSucceeded(): Boolean {
        return true
    }

}
