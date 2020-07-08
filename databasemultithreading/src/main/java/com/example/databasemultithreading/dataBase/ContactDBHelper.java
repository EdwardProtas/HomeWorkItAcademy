package com.example.databasemultithreading.dataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import androidx.annotation.Nullable;

public class ContactDBHelper extends SQLiteOpenHelper {

    private static final String SQL_CREATE_TABLE_CONTACT =
            "CREATE TABLE " + ContactEntry.TABLE_NAME + " (" +
                    ContactEntry.COLUMN_ID + " INTEGER PRIMARY KEY," +
                    ContactEntry.COLUMN_NAME_CONTACT + " TEXT," +
                    ContactEntry.COLUMN_NAME_PHONE + " TEXT," +
                    ContactEntry.COLUMN_NAME_TYPE + " TEXT)";

    private static final String SQL_DELETE_TABLE_CONTACT =
            "DROP TABLE IF EXISTS" + ContactEntry.TABLE_NAME;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "contact_db";


    public ContactDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_CONTACT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public static class ContactEntry implements BaseColumns {
        public static final String TABLE_NAME = "contactDataBase";
        public static final String COLUMN_NAME_CONTACT = "name";
        public static final String COLUMN_NAME_PHONE = "numberEmail";
        public static final String COLUMN_NAME_TYPE = "type";
        public static final String COLUMN_ID = "id";
    }
}
