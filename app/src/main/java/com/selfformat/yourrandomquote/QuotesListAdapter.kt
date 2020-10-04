package com.selfformat.yourrandomquote

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.quote_list_item.view.*

class QuotesListAdapter(private val quote: List<String?>) :
    RecyclerView.Adapter<QuotesListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val view = itemView.findViewById<View>(R.id.quoteItemLayout)

        fun bind(string: String?) {
            view.quoteTextView.text = string
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.quote_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(quote[position])

    override fun getItemCount() = quote.size
}
