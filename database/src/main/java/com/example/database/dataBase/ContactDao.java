package com.example.database.dataBase;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface ContactDao {

    @Query("SELECT * FROM contact")
    List<ContactEntity> getAllContacts();

    @Query("SELECT * FROM contact WHERE id = :id")
    ContactEntity getContact(int id);

   @Update
    void upData(ContactEntity contactEntity);

    @Delete
    void delete(ContactEntity... contactEntity);

    @Insert(onConflict = REPLACE)
    void insert(ContactEntity contactEntity);

}
