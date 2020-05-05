package com.example.contactkotlin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity

class AddActivity() : AppCompatActivity(){

    private lateinit var keyboard: ImageView
    private lateinit var add: ImageView
    private lateinit var radioButtom_Phone: RadioButton
    private lateinit var radioButtom_Email: RadioButton
    private lateinit var name: EditText
    private lateinit var editText_phoneNumber_email: EditText
    private lateinit var radioGroup: RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_contact)
        val intent = Intent()
        keyboard = findViewById(R.id.keyboard)
        add = findViewById(R.id.add)
        radioButtom_Phone = findViewById(R.id.radioButtom_Phone)
        radioButtom_Email = findViewById(R.id.radioButtom_Email)
        name = findViewById(R.id.name)
        editText_phoneNumber_email = findViewById(R.id.editText_phoneNumber_email)
        radioGroup = findViewById(R.id.radioGroup)
        radioGroup.clearCheck()
        radioGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.radioButtom_Email -> intent.putExtra(
                        "radioButtom_Email",
                        radioButtom_Email.isChecked()
                )
                R.id.radioButtom_Phone -> intent.putExtra(
                        "radioButtom_Phone",
                        radioButtom_Phone.isChecked()
                )
            }
        })
        add.setOnClickListener(View.OnClickListener {
            intent.putExtra("name", name.getText().toString())
            intent.putExtra("number_email", editText_phoneNumber_email.getText().toString())
            setResult(Activity.RESULT_OK, intent)
            finish()
        })
        keyboard.setOnClickListener(View.OnClickListener { onBackPressed() })
    }
}