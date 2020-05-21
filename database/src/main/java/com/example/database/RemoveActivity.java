package com.example.database;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class RemoveActivity extends AppCompatActivity {

    private ImageView keyboard2;
    private EditText name_remove;
    private EditText editText_phoneNumber;
    private Button remove_button;
    private final int REQUEST_CODE_REMOVE = 2;
    private Button edit_button;
    private Boolean flag;
    private Dialog mDialog;
    private int id;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove);
        keyboard2 = findViewById(R.id.keyboard2);
        remove_button = findViewById(R.id.remove_button);
        name_remove = findViewById(R.id.name_remove);
        editText_phoneNumber = findViewById(R.id.editText_phoneNumber);
        edit_button = findViewById(R.id.edit_button);

        keyboard2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getIntentIn();
        dialog();

        edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("name_remove", name_remove.getText().toString());
                intent.putExtra("editText_phoneNumber", editText_phoneNumber.getText().toString());
                intent.putExtra("flag", flag);
                intent.putExtra("id" , id);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        remove_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDialog != null) {
                    mDialog.show();
                }
            }
        });
    }

    private void getIntentIn() {
        if (getIntent().hasExtra("mNameList") && getIntent().hasExtra("mNumberList")) {
            id = getIntent().getIntExtra("id", 0);
            String nameRemove = getIntent().getStringExtra("mNameList");
            String editTextPhoneNumber = getIntent().getStringExtra("mNumberList");
            flag = getIntent().getBooleanExtra("flag", false);
            setVal(nameRemove, editTextPhoneNumber);
        }
    }

    private void setVal(String nameRemove, String editTextPhoneNumber) {
        name_remove.setText(nameRemove);
        editText_phoneNumber.setText(editTextPhoneNumber);
    }

    private void dialog() {
        mDialog = new AlertDialog.Builder(this)
                .setMessage("Действительно удалить контакт?")
                .setPositiveButton("Удалить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String name_user = name_remove.getText().toString();
                        String email_number_user = editText_phoneNumber.getText().toString();
                        if (name_user.length() == 0 && email_number_user.length() == 0) {
                            Intent intent = new Intent();
                            intent.putExtra("name_remove", name_remove.getText().toString());
                            intent.putExtra("editText_phoneNumber", editText_phoneNumber.getText().toString());
                            intent.putExtra("flag", flag);
                            intent.putExtra("id", id);
                            setResult(RESULT_OK, intent);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Обнулите значения", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mDialog.cancel();
                    }
                })
                .create();
    }
}