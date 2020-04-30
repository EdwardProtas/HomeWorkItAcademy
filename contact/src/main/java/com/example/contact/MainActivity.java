package com.example.contact;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NewAdapter.SelectedContact {

    private EditText search_editText;
    private RecyclerView recyclerview;
    private ImageButton addButton;
    private NewAdapter adapter;
    private static final int REQUEST_CODE_ADDCONTACT = 1;
    private static final int REQUEST_CODE_REMOVE = 2;
    private static final String KEY_NUMBER = "number";
    private Boolean ff;
    private List<NewAdapter.Contact> newContactList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addButton = findViewById(R.id.addButton);

        recyclerview = findViewById(R.id.recyclerview);
        recyclerview.setAdapter(new NewAdapter(newContactList, this));
        recyclerview.setLayoutManager(new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false));

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADDCONTACT);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_ADDCONTACT:
                    assert data != null;
                    boolean radioButtom_Phone = data.getBooleanExtra("radioButtom_Phone", false);
                    boolean radioButtom_Email = data.getBooleanExtra("radioButtom_Email", false);
                    if (radioButtom_Email) {
                        ff = true;
                    }
                    if (radioButtom_Phone) {
                        ff = false;
                    }
                    String newName = data.getStringExtra("name");
                    String newNumberAndEmail = data.getStringExtra("number_email");
                    if (!newName.trim().isEmpty() && !newNumberAndEmail.trim().isEmpty() && recyclerview.getAdapter() != null) {
                        adapter = (NewAdapter) recyclerview.getAdapter();
                        adapter.addItems(new NewAdapter.Contact(newName, newNumberAndEmail, ff));
                    }
                    break;
                case REQUEST_CODE_REMOVE:
                    String nameRemove = data.getStringExtra("name_remove");
                    String NumberAndEmailREmove = data.getStringExtra("editText_phoneNumber");
                    Boolean flag = data.getBooleanExtra("flag" , false);
                    if (nameRemove.trim().isEmpty() && NumberAndEmailREmove.trim().isEmpty()) {
                        adapter.remove();
                    }
                    if (!nameRemove.trim().isEmpty() && !NumberAndEmailREmove.trim().isEmpty()) {
                        NewAdapter.Contact contact = new NewAdapter.Contact(nameRemove , NumberAndEmailREmove , flag);
                        adapter.upDate(contact);
                    }
                default:
                    break;
            }
        }
    }
    @Override
    public void selectedContact(NewAdapter.Contact contact) {
        Intent intent = new Intent(MainActivity.this, RemoveActivity.class);
        intent.putExtra("mNameList", contact.getNameText());
        intent.putExtra("mNumberList", contact.getNumberText());
        intent.putExtra("flag", contact.getType());
        startActivityForResult(intent, REQUEST_CODE_REMOVE);
    }
}
