package com.example.contactkotlin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class ItemAdapter(

        private val newContactList: MutableList<Contact>,
        private val selectedContact: SelectedContact

) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    private val TYPE_ITEM1: Int = 0
    private val TYPE_ITEM2: Int = 1
    private lateinit var contact: Contact
    private var getPosition: Int = 0

    fun addItems(contact: Contact) {
        newContactList.add(contact)
        notifyDataSetChanged()
    }

    fun remove() {
        newContactList.remove(contact)
        notifyDataSetChanged()
    }

    fun upDate(contactc: Contact) {
        newContactList.removeAt(getPosition)
        newContactList.add(getPosition, contactc)
        notifyItemChanged(getPosition)
    }

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): ItemViewHolder {
        val view: View
        view = if (viewType == TYPE_ITEM1) {
            LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_contact, parent, false)
        } else {
            LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_contact2, parent, false)
        }
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(
            holder: ItemViewHolder,
            position: Int
    ) {
        val contact = newContactList[position]
        holder.bind(contact)
    }

    override fun getItemViewType(position: Int): Int {
        val contact = newContactList[position]
        return if (contact.type) TYPE_ITEM1 else TYPE_ITEM2
    }

    override fun getItemCount(): Int {
        return newContactList.size
    }

    interface SelectedContact {
        fun selectedContact(contact: Contact)
    }

    inner class ItemViewHolder(itemView: View) :
            ViewHolder(itemView) {

        private val text_name_contact: TextView = itemView.findViewById(R.id.text_name_contact)
        private val number: TextView = itemView.findViewById(R.id.number)

        fun bind(contact: Contact) {
            text_name_contact.text = contact.nameText
            number.text = contact.numberText
        }

        init {
            itemView.setOnClickListener {
                selectedContact.selectedContact(newContactList[adapterPosition])
                contact = newContactList[adapterPosition]
                getPosition = newContactList.indexOf(contact)
            }
        }
    }

    class Contact(
            var nameText: String,
            var numberText: String,
            var type: Boolean
    ) {
        private val icon: ImageView? = null
    }

}