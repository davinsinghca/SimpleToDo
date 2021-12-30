package com.example.simpletodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    val listOfTasks = mutableListOf<String>()

    lateinit var adapter : TaskItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListener = object : TaskItemAdapter.OnLongClickListener {
            override fun onItemLongClicked(position: Int) {
                // 1. remove item from list
                listOfTasks.removeAt(position)

                // 2. notify adapter that our data set has changed
                adapter.notifyDataSetChanged()

            }
        }

        // Detect when user clicks the add button
//        findViewById<Button>(R.id.button).setOnClickListener {
//            // executed when user clicks button
//            Log.i("Caren", "User clicked on button")
//        }

        listOfTasks.add("Do laundry")
        listOfTasks.add("Go for a walk")


        // Lookup the recyclerview in activity layout
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        // Initialize contacts
        // Create adapter passing in the sample user data
        adapter = TaskItemAdapter(listOfTasks, onLongClickListener)

        // Attach the adapter to the recyclerview to populate items
        recyclerView.adapter = adapter

        // Set layout manager to position the items
        recyclerView.layoutManager = LinearLayoutManager(this)

        val inputTextField = findViewById<EditText>(R.id.addTaskField)

        // Set up button/input field so user can input a task and add to list
        // get a reference to the button, then setOnClickListener
        findViewById<Button>(R.id.button).setOnClickListener {
            // 1. graph inputted text that is in @+id/addTaskField (just .text is an "Editable" object)
            val userInputtedTask = inputTextField.text.toString()

            // 2. add string to list of tasks
            listOfTasks.add(userInputtedTask)

            // 2.5. notify adapter
            adapter.notifyItemInserted(listOfTasks.size - 1)

            // 3. clear input text field
            inputTextField.setText("")
        }

    }
}