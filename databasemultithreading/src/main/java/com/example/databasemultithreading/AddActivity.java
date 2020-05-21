package com.example.databasemultithreading;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AddActivity extends AppCompatActivity  {

    private ImageView keyboard;
    private ImageView add;
    private RadioButton radioButtom_Phone;
    private RadioButton radioButtom_Email;
    private EditText name;
    private EditText editText_phoneNumber_email;
    private RadioGroup radioGroup;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        final Intent intent = new Intent();
        keyboard = findViewById(R.id.keyboard);
        add = findViewById(R.id.add);
        radioButtom_Phone = findViewById(R.id.radioButtom_Phone);
        radioButtom_Email = findViewById(R.id.radioButtom_Email);
        name = findViewById(R.id.name);
        editText_phoneNumber_email = findViewById(R.id.editText_phoneNumber_email);
        radioGroup = findViewById(R.id.radioGroup);
        radioGroup.clearCheck();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radioButtom_Email:
                        intent.putExtra("radioButtom_Email", radioButtom_Email.isChecked());
                        break;
                    case  R.id.radioButtom_Phone:
                        intent.putExtra("radioButtom_Phone", radioButtom_Phone.isChecked());
                        break;
                }
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("name", name.getText().toString());
                intent.putExtra("number_email", editText_phoneNumber_email.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        keyboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}