package com.example.databasemultithreading;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

;


public class NewAdapter extends RecyclerView.Adapter<NewAdapter.ItemViewHolder> implements Parcelable, Serializable {

    private List<Contact> newContactList;
    private final int TYPE_ITEM1 = 0;
    private final int TYPE_ITEM2 = 1;
    private SelectedContact selectedContact;
    private Contact contact;
    private int getPosition ;


    public void setNewContactList(List<Contact> newContactList) {
        this.newContactList = newContactList;
    }

    public NewAdapter(List<Contact> newContactList, SelectedContact selectedContact) {
        this.newContactList = newContactList;
        this.selectedContact = selectedContact;
    }

    private NewAdapter ( Parcel s){
        newContactList = (List<Contact>) s.readSerializable();
    }

    public static final Creator<NewAdapter> CREATOR = new Creator<NewAdapter>() {
        @Override
        public NewAdapter createFromParcel(Parcel in) {
            return new NewAdapter(in);
        }

        @Override
        public NewAdapter[] newArray(int size) {
            return new NewAdapter[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
       dest.writeSerializable((Serializable) newContactList);
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

    static class Contact implements Serializable {
        private String nameText;
        private String numberText;
        private ImageView icon;
        private int id;


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

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