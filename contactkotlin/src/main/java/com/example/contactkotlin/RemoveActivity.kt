package com.example.contactkotlin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class RemoveActivity() : AppCompatActivity() {

    private lateinit var keyboard2: ImageView
    private lateinit var name_remove: EditText
    private lateinit var editText_phoneNumber: EditText
    private lateinit var remove_button: Button
    private var flag: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_remove)
        keyboard2 = findViewById(R.id.keyboard2)
        remove_button = findViewById(R.id.remove_button)
        name_remove = findViewById(R.id.name_remove)
        editText_phoneNumber = findViewById(R.id.editText_phoneNumber)
        keyboard2.setOnClickListener(View.OnClickListener { onBackPressed() })

        getIntentIn()

        remove_button.setOnClickListener(View.OnClickListener {
            val intent = Intent()
            intent.putExtra("name_remove", name_remove.getText().toString())
            intent.putExtra("editText_phoneNumber", editText_phoneNumber.getText().toString())
            intent.putExtra("flag", flag)
            setResult(Activity.RESULT_OK, intent)
            finish()
        })
    }

    private fun getIntentIn() {
        if (intent.hasExtra("mNameList") && intent.hasExtra("mNumberList")) {
            val nameRemove = intent.getStringExtra("mNameList")
            val editTextPhoneNumber = intent.getStringExtra("mNumberList")
            flag = intent.getBooleanExtra("flag", false)
            setVal(nameRemove, editTextPhoneNumber)
        }
    }

    private fun setVal(
            nameRemove: String,
            editTextPhoneNumber: String
    ) {
        name_remove.setText(nameRemove)
        editText_phoneNumber.setText(editTextPhoneNumber)
    }
}