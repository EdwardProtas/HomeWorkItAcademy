package com.example.databasemultithreading;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.databasemultithreading.dataBase.ContactEntity;
import com.example.databasemultithreading.dataBase.DataBase;
import com.facebook.stetho.Stetho;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements NewAdapter.SelectedContact {

    private EditText search_editText;
    private RecyclerView recyclerview;
    private ImageButton addButton;
    private NewAdapter adapter;
    private static final int REQUEST_CODE_ADDCONTACT = 1;
    private static final int REQUEST_CODE_REMOVE = 2;
    private static final String KEY = "savedInstanceState";
    private Boolean ff;
    private List<NewAdapter.Contact> newContactList = new ArrayList<>();
    private DataBase mDataBase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addButton = findViewById(R.id.addButton);
        recyclerview = findViewById(R.id.recyclerview);
        Stetho.initializeWithDefaults(this);
        mDataBase = DataBase.getInstance(MainActivity.this);
        Stetho.initializeWithDefaults(this);
        getContacts();
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADDCONTACT);
            }
        });
        if (savedInstanceState != null) {
            recyclerview.setAdapter((RecyclerView.Adapter) savedInstanceState.getParcelable(KEY));
        } else {
            recyclerview.setAdapter(new NewAdapter(newContactList, this));
        }
        recyclerview.setLayoutManager(new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false));
        adapter = (NewAdapter) recyclerview.getAdapter();
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_ADDCONTACT:
                    if (data != null) {
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
                            NewAdapter.Contact contact = new NewAdapter.Contact(newName, newNumberAndEmail, ff);
                            adapter.addItems(contact);
                            addContact(contact);
                        }
                    }
                    break;
                case REQUEST_CODE_REMOVE:
                    if (data != null) {
                    int id = data.getIntExtra("id", 0 );
                        String nameRemove = data.getStringExtra("name_remove");
                        String NumberAndEmailREmove = data.getStringExtra("editText_phoneNumber");
                        Boolean flag = data.getBooleanExtra("flag", false);
                        if (nameRemove.trim().isEmpty() && NumberAndEmailREmove.trim().isEmpty()) {
                            NewAdapter.Contact contact = new NewAdapter.Contact(nameRemove, NumberAndEmailREmove, flag);
                            contact.setId(id);
                            adapter.remove();
                            deleteContact(contact);
                            Log.d("Tag1" , String.valueOf(contact.getId()));
                        }
                        if (!nameRemove.trim().isEmpty() && !NumberAndEmailREmove.trim().isEmpty()) {
                            NewAdapter.Contact contact = new NewAdapter.Contact(nameRemove, NumberAndEmailREmove, flag);
                            contact.setId(id);
                            adapter.upDate(contact);
                            updateContact(contact);
                            Log.d("Tag" , String.valueOf(contact.getId()));
                        }
                    }
                default:
                    break;
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY, adapter);
    }

    @Override
    public void selectedContact(NewAdapter.Contact contact) {
        Intent intent = new Intent(MainActivity.this, RemoveActivity.class);
        intent.putExtra("mNameList", contact.getNameText());
        intent.putExtra("mNumberList", contact.getNumberText());
        intent.putExtra("flag", contact.getType());
        intent.putExtra("id" , contact.getId());
        startActivityForResult(intent, REQUEST_CODE_REMOVE);
    }

    public void addContact(final NewAdapter.Contact contact) {
        mDataBase.getDataBaseExecutorService().execute(new Runnable() {
            @Override
            public void run() {
                ContactEntity contactEntity = new ContactEntity(contact.getNameText(), contact.getNumberText(), contact.getType());
                mDataBase.getContactDao().insert(contactEntity);
                contact.setId(contactEntity.getId());
            }
        });
    }

    public void getContacts() {
        CompletableFuture<Void> completableFuture = CompletableFuture.supplyAsync(new Supplier<Void>() {
            @Override
            public Void get() {
                List<ContactEntity> contactEntities = mDataBase.getContactDao().getAllContacts();
                NewAdapter.Contact contact;
                for (ContactEntity contactEntity : contactEntities) {
                    if (contactEntity.getType().equals(true)) {
                        contact = new NewAdapter.Contact(contactEntity.getName(), contactEntity.getNumber_email(), contactEntity.getType());
                        contact.setId(contactEntity.getId());
                    } else {
                        contact = new NewAdapter.Contact(contactEntity.getName(), contactEntity.getNumber_email(), contactEntity.getType());
                        contact.setId(contactEntity.getId());
                    }
                    newContactList.add(contact);
                }
                return null;
            }
        }, mDataBase.getDataBaseExecutorService());
    }



    public void deleteContact(final NewAdapter.Contact contact){
        mDataBase.getDataBaseExecutorService().execute(new Runnable() {
            @Override
            public void run() {
                ContactEntity contactEntity = new ContactEntity(contact.getNameText() , contact.getNumberText(),contact.getType());
                contactEntity.setId(contact.getId());
                mDataBase.getContactDao().delete(contactEntity);
            }
        });

    }

    public void updateContact(final NewAdapter.Contact contact){
        mDataBase.getDataBaseExecutorService().execute(new Runnable() {
            @Override
            public void run() {
                ContactEntity contactEntity = new ContactEntity(contact.getNameText() , contact.getNumberText(),contact.getType());
                contactEntity.setId(contact.getId());
                mDataBase.getContactDao().upData(contactEntity);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDataBase.close();
    }

}
