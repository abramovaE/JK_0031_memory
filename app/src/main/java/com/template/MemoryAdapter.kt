package com.template

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MemoryAdapter(private val cards: Array<String>,
                    private val onClickPairListener: OnClickPairListener):
    RecyclerView.Adapter<MemoryAdapter.MemoryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoryViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.memory_item, parent, false)
        return MemoryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MemoryViewHolder, position: Int) {
        holder.textView.text = cards[position]

        holder.textView.setOnClickListener {
            onClickPairListener.onClickItem(position)
        }
    }

    override fun getItemCount(): Int {
        return cards.size
    }

    class MemoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val textView = itemView.findViewById<TextView>(R.id.textView)!!
    }
}