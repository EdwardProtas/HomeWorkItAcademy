package com.example.contact;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;


public class NewAdapter extends RecyclerView.Adapter<NewAdapter.ItemViewHolder> {

    private List<Contact> newContactList;
    private final int TYPE_ITEM1 = 0;
    private final int TYPE_ITEM2 = 1;
    private SelectedContact selectedContact;
    private Contact contact;
    private int getPosition ;

    public NewAdapter(List<Contact> newContactList, SelectedContact selectedContact) {
        this.newContactList = newContactList;
        this.selectedContact = selectedContact;
    }

    void addItems(Contact contact) {
        newContactList.add(contact);
        notifyDataSetChanged();
    }

    void remove() {
        newContactList.remove(contact);
        notifyDataSetChanged();
    }

    void upDate(Contact contactc){
        newContactList.remove(getPosition);
        newContactList.add(getPosition , contactc);
        notifyItemChanged(getPosition);
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_ITEM1) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact2, parent, false);
        }
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, final int position) {
        Contact contact = newContactList.get(position);
        holder.bind(contact);
    }

    @Override
    public int getItemViewType(int position) {
        Contact contact = newContactList.get(position);
        return contact.type ? TYPE_ITEM1 : TYPE_ITEM2;
    }

    @Override
    public int getItemCount() {
        return newContactList.size();
    }

    public interface SelectedContact {
        void selectedContact(Contact contact);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView phoneImage;
        private ImageView phoneImage2;
        private TextView text_name_contact;
        private TextView number;


        ItemViewHolder(View itemView) {
            super(itemView);
            phoneImage = itemView.findViewById(R.id.phoneImage);
            phoneImage2 = itemView.findViewById(R.id.phoneImage2);
            text_name_contact = itemView.findViewById(R.id.text_name_contact);
            number = itemView.findViewById(R.id.number);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedContact.selectedContact(newContactList.get(getAdapterPosition()));
                    contact = newContactList.get(getAdapterPosition());
                    getPosition=  newContactList.indexOf(contact);
                }
            });
        }

        void bind(Contact contact) {
            text_name_contact.setText(contact.nameText);
            number.setText(contact.numberText);
        }
    }

    static class Contact {
        private String nameText;
        private String numberText;
        private ImageView icon;

        public String getNameText() {
            return nameText;
        }

        public void setNameText(String nameText) {
            this.nameText = nameText;
        }

        public String getNumberText() {
            return numberText;
        }

        public void setNumberText(String numberText) {
            this.numberText = numberText;
        }

        private Boolean type;

        public Boolean getType() {
            return type;
        }

        public void setType(Boolean type) {
            this.type = type;
        }

        public Contact(String nameText, String numberText, Boolean type) {
            this.nameText = nameText;
            this.numberText = numberText;
            this.type = type;
        }
    }
}