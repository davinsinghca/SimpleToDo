package com.example.simpletodo

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Bridge that tells recyclerView how to display data we give it (list of strings)
// renders list of strings item by item

class TaskItemAdapter(val listOfItems: List<String>,
                      val longClickListener: OnLongClickListener) :
    RecyclerView.Adapter<TaskItemAdapter.ViewHolder>(){

    // implemented in MainActivity
    interface OnLongClickListener {
        fun onItemLongClicked(position: Int)
    }

    // ... constructor and member variables
    // Usually involves inflating a layout from XML and returning the holder
    // tell recyclerView how to "inflate" a lay out for each specific item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val contactView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false)
        // Return a new holder instance
        return ViewHolder(contactView)
    }

    // Involves populating data into the item through holder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Get the data model based on position
        val item: String = listOfItems.get(position)

        // Set item views based on your views and data model
        holder.textView.text = item
    }

    override fun getItemCount(): Int {
        return listOfItems.size
    }

    // Provide a direct reference to each of the views within a data item (Ex: all text fields/buttons within an item)
    // Used to cache the views within the item layout for fast access

    // also where we can set a longClick listener
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Store references to elements in our layout view
        // since our layout of each list item only has a single text field, only one text reference needed

        val textView: TextView

        init {
            textView = itemView.findViewById(android.R.id.text1)

            // pass in a defined OnLongClickListener so we can control it from the main activity
            itemView.setOnLongClickListener {
                longClickListener.onItemLongClicked(adapterPosition)
                true
            }
        }
    }
}