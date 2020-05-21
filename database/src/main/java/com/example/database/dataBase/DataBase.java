package com.example.database.dataBase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {ContactEntity.class}, version = 1)
public abstract class DataBase extends RoomDatabase {

     public abstract ContactDao getContactDao();
}
