package com.example.simpletodo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

private const val REQUEST_EDIT = 20

class MainActivity : AppCompatActivity() {

    var listOfTasks = mutableListOf<String>()

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

                saveItems()
            }
        }

        loadItems()

        // Lookup the recyclerview in activity layout
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        // Initialize contacts
        // Create adapter passing in the sample user data
        adapter = TaskItemAdapter(listOfTasks, onLongClickListener, ::clickListener)

        // Attach the adapter to the recyclerview to populate items
        recyclerView.adapter = adapter

        // Set layout manager to position the items
        recyclerView.layoutManager = LinearLayoutManager(this)

        val inputTextField = findViewById<EditText>(R.id.addTaskField)

        // Set up button/input field so user can input a task and add to list
        // get a reference to the button, then setOnClickListener
        findViewById<Button>(R.id.button).setOnClickListener {
            val userText: String = inputTextField.text.toString()

            if (userText != "") {
                // 1. graph inputted text that is in @+id/addTaskField (just .text is an "Editable" object)
                val userInputtedTask = userText

                // 2. add string to list of tasks
                listOfTasks.add(userInputtedTask)

                // 2.5. notify adapter
                adapter.notifyItemInserted(listOfTasks.size - 1)

                // 3. clear input text field
                inputTextField.setText("")

                saveItems()
            }
        }
    }

    // Edit an item

    // FirstActivity, launching an activity for a result
    fun clickListener(position: Int) {
        val i = Intent(this@MainActivity, EditItemActivity::class.java)
        i.putExtra("currentText", listOfTasks.get(position)) // pass the current text to the new activity window
        i.putExtra("index", position)
        startActivityForResult(i, REQUEST_EDIT)
    }

    // ActivityOne.kt, time to handle the result of the sub-activity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // REQUEST_EDIT is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_EDIT) {
            // Extract name value from result extras
            val newText = data!!.getExtras()!!.getString("newText")
            val position = data.getExtras()!!.getInt("index")

            // Edit the text of the item
            listOfTasks.set(position, newText!!)

            // 2.5. notify adapter
            adapter.notifyItemChanged(position)

            saveItems()
        }
    }

    // Save the data that the user has inputted
    // By writing and reading from a file

    // Create a method to get the file we need
    fun getDataFile() : File {
        // Every line is going to represent a task in our list of tasks
        return File(filesDir, "data.txt")
    }

    // Load the items by reading every line in the data file
    fun loadItems() {
        try {
            listOfTasks = FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }
    }

    // Save items by writing them into the file
    fun saveItems() {
        try {
            FileUtils.writeLines(getDataFile(), listOfTasks)
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }
    }
}