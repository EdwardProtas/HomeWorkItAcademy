package com.example.contactkotlin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contactkotlin.ItemAdapter.Contact
import java.util.*


class MainActivity() : AppCompatActivity(),  ItemAdapter.SelectedContact {

    private lateinit var search_editText: EditText
    private lateinit var recyclerview: RecyclerView
    private lateinit var addButton: ImageButton
    private lateinit var adapter: ItemAdapter
    private var ff: Boolean = false
    private val newContactList: MutableList<ItemAdapter.Contact> = ArrayList<ItemAdapter.Contact>()

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addButton = findViewById(R.id.addButton)
        recyclerview = findViewById(R.id.recyclerview)
        recyclerview.setAdapter(ItemAdapter(newContactList,this))
        recyclerview.setLayoutManager(
                LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        )
        addButton.setOnClickListener(View.OnClickListener { val intent = Intent(this, AddActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_ADDCONTACT)
        })
    }

    public override fun onActivityResult(
            requestCode: Int,
            resultCode: Int,
            data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_ADDCONTACT -> {
                    val radioButtom_Phone = data!!.getBooleanExtra("radioButtom_Phone", false)
                    val radioButtom_Email = data!!.getBooleanExtra("radioButtom_Email", false)
                    if (radioButtom_Email) {
                        ff = true
                    }
                    if (radioButtom_Phone) {
                        ff = false
                    }
                    val newName = data!!.getStringExtra("name")
                    val newNumberAndEmail = data!!.getStringExtra("number_email")
                    if (!newName.trim { it <= ' ' }.isEmpty() && !newNumberAndEmail.trim { it <= ' ' }.isEmpty() && recyclerview.adapter != null) {
                        adapter = recyclerview.adapter as ItemAdapter
                        adapter.addItems(Contact(newName, newNumberAndEmail, ff))
                    }
                }
                REQUEST_CODE_REMOVE -> {
                    val nameRemove = data!!.getStringExtra("name_remove")
                    val NumberAndEmailREmove = data!!.getStringExtra("editText_phoneNumber")
                    val flag = data!!.getBooleanExtra("flag", false)
                    if (nameRemove.trim { it <= ' ' }.isEmpty() && NumberAndEmailREmove.trim { it <= ' ' }.isEmpty()) {
                        adapter.remove()
                    }
                    if (!nameRemove.trim { it <= ' ' }.isEmpty() && !NumberAndEmailREmove.trim { it <= ' ' }.isEmpty()) {
                        val contact = Contact(nameRemove, NumberAndEmailREmove, flag)
                        adapter.upDate(contact)
                    }
                }
                else -> {
                }
            }
        }
    }

    override fun selectedContact(contact: Contact) {
        val intent = Intent(this, RemoveActivity::class.java)
        intent.putExtra("mNameList", contact.nameText)
        intent.putExtra("mNumberList", contact.numberText)
        intent.putExtra("flag", contact.type)
        startActivityForResult(intent, REQUEST_CODE_REMOVE)
    }

    companion object {
        private const val REQUEST_CODE_ADDCONTACT = 1
        private const val REQUEST_CODE_REMOVE = 2
        private const val KEY_NUMBER = "savedInstanceState"
    }
}
