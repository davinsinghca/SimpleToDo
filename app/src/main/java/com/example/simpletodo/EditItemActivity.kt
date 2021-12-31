package com.example.simpletodo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class EditItemActivity : AppCompatActivity() {
    private lateinit var taskField : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_item)

        taskField = findViewById(R.id.editTaskField)

        taskField.setText(intent.getStringExtra("currentText"))

        findViewById<Button>(R.id.okbutton).setOnClickListener{
            onOKButton()
        }

        findViewById<Button>(R.id.cancelbutton).setOnClickListener{
            setResult(RESULT_CANCELED)
            finish()
        }
    }

    // ActivityNamePrompt.kt -- launched for a result
    fun onOKButton() {
        // Prepare data intent
        val data = Intent()
        // Pass relevant data back as a result
        data.putExtra("newText", taskField.text.toString())
        data.putExtra("index", intent.getIntExtra("index", 0))
        // Activity finished ok, return the data
        setResult(RESULT_OK, data) // set result code and bundle data for response
        finish() // closes the activity, pass data to parent
    }
}