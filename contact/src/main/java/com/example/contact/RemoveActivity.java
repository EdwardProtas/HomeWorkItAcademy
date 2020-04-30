package com.example.contact;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class RemoveActivity extends AppCompatActivity {

    private ImageView keyboard2;
    private EditText name_remove;
    private EditText editText_phoneNumber;
    private Button remove_button;
    private final int REQUEST_CODE_REMOVE = 2;
    private Boolean flag;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove);
        keyboard2 = findViewById(R.id.keyboard2);
        remove_button = findViewById(R.id.remove_button);
        name_remove = findViewById(R.id.name_remove);
        editText_phoneNumber = findViewById(R.id.editText_phoneNumber);

        keyboard2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getIntentIn();

        remove_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("name_remove", name_remove.getText().toString());
                intent.putExtra("editText_phoneNumber", editText_phoneNumber.getText().toString());
                intent.putExtra("flag", flag);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private void getIntentIn() {
        if (getIntent().hasExtra("mNameList") && getIntent().hasExtra("mNumberList")) {
            String nameRemove = getIntent().getStringExtra("mNameList");
            String editTextPhoneNumber = getIntent().getStringExtra("mNumberList");
            flag = getIntent().getBooleanExtra("flag" , false);
            setVal(nameRemove, editTextPhoneNumber);
        }
    }

    private void setVal(String nameRemove, String editTextPhoneNumber) {
        name_remove.setText(nameRemove);
        editText_phoneNumber.setText(editTextPhoneNumber);
    }
}