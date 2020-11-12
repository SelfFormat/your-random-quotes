package com.selfformat.yourrandomquote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.selfformat.yourrandomquote.domain.Quote
import kotlinx.android.synthetic.main.quote_list_item.view.*

class QuotesListAdapter(private val quote: Map<String, Quote>) :
    RecyclerView.Adapter<QuotesListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val view = itemView.findViewById<View>(R.id.quoteItemLayout)

        fun bind(quote: Quote?) {
            view.quoteTextView.text = quote?.quote
            view.authorTextView.text = quote?.author
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(
                R.layout.quote_list_item,
                parent,
                false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val quotes = quote.values
        val ids = quote.keys
        val singleQuote = quotes.toList()[position]
        val id = ids.toList()[position]
        holder.bind(singleQuote)
        holder.itemView.setOnClickListener(navigateToQuoteDetails(singleQuote, id))
    }

    private fun navigateToQuoteDetails(quote: Quote?, id: String): View.OnClickListener {
        return Navigation.createNavigateOnClickListener(
            R.id.action_mainFragment_to_quoteDetailsFragment,
            Bundle().apply {
                putParcelable(QUOTE, quote)
                putString(QUOTE_ID, id)
            }
        )
    }

    override fun getItemCount() = quote.size

    companion object {
        private const val QUOTE = "key_quote"
        private const val QUOTE_ID = "key_quote_id"
    }
}
