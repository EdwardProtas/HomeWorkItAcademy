package com.example.database.dataBase;

import android.app.AlertDialog;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "contact" , indices = {@Index(value = {"id"} , unique = true)})
public class ContactEntity {

@PrimaryKey (autoGenerate = true)
    private int id;
    private String name;
    private String number_email;
    private Boolean type;

    public ContactEntity(String name, String number_email , Boolean type) {
        this.name = name;
        this.number_email = number_email;
        this.type = type;
    }

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber_email() {
        return number_email;
    }

    public void setNumber_email(String number_email) {
        this.number_email = number_email;
    }



//    static class Bilder {
//
//        private int id;
//        private String name;
//        private String type;
//
//
//        public Bilder setId(int id) {
//            this.id = id;
//            return this;
//        }
//
//        public Bilder setName(String name) {
//            this.name = name;
//            return this;
//        }
//
//        public Bilder setType(String type) {
//            this.type = type;
//            return this;
//        }
//
//        public ContactEntity build() {
//            return new ContactEntity(
//                    id,
//                    name,
//                    type
//            );
//
//        }
//    }
}
