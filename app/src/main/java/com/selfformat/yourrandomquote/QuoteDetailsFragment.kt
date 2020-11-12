package com.selfformat.yourrandomquote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.selfformat.yourrandomquote.domain.DatabaseQuote
import com.selfformat.yourrandomquote.domain.Quote
import kotlinx.android.synthetic.main.add_quote_fragment.*

class QuoteDetailsFragment : Fragment() {

    private val quoteViewModel: QuoteDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.quote_details_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val quote = arguments?.getParcelable<Quote?>(QUOTE)
        val id = arguments?.getString(QUOTE_ID)
        if (quote == null || id == null) {
            addQuoteLayout()
        } else {
            editQuoteLayout(quote, id)
        }

    }

    private fun editQuoteLayout(quote: Quote, id: String) {
        quoteTextField.editText?.setText(quote.quote)
        authorTextField.editText?.setText(quote.author)

        saveQuoteButton.apply {
            visibility = View.VISIBLE
            setOnClickListener {
                if (validationSucceeded()) {
                    LoginViewModel.uid?.let {
                        quoteViewModel.updateQuote(it, id, prepareQuoteFromInput())
                    }
                    findNavController().navigateUp()
                }
            }
        }
        removeQuoteButton.apply {
            visibility = View.VISIBLE
            setOnClickListener {
                if (validationSucceeded()) {
                    LoginViewModel.uid?.let {
                        quoteViewModel.removeQuote(uid = it, quoteId = id)
                    }
                    findNavController().navigateUp()
                }
            }
        }
    }

    private fun addQuoteLayout() {
        removeQuoteButton.apply {
            visibility = View.GONE
        }

        saveQuoteButton.apply {
            visibility = View.VISIBLE
            setOnClickListener {
                if (validationSucceeded()) {
                    LoginViewModel.uid?.let {
                        quoteViewModel.addQuote(it, prepareQuoteFromInput())
                    }
                    findNavController().navigateUp()
                }
            }
        }
    }

    private fun prepareQuoteFromInput(): DatabaseQuote {
        return DatabaseQuote(
            quote = quoteTextField.editText?.text.toString(),
            author = authorTextField.editText?.text.toString()
        )
    }

    private fun validationSucceeded(): Boolean {
        if (quoteTextField.editText?.text.isNullOrBlank() || authorTextField.editText?.text.isNullOrBlank()) {
            quoteTextField.error = getString(R.string.edittext_error_message)
            authorTextField.error = getString(R.string.edittext_error_message)
            return false
        }
        return true
    }

    companion object {
        private const val QUOTE = "key_quote"
        private const val QUOTE_ID = "key_quote_id"
    }
}
