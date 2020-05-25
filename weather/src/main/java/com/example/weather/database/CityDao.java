package com.example.weather.database;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface CityDao {

    @Query("SELECT * FROM city")
    List<CityEntity> getAllCity();

    @Query("SELECT * FROM city WHERE id = :id")
    CityEntity getCity(int id);

    @Delete
    void delete(CityEntity... cityEntity);

    @Insert(onConflict = REPLACE)
    void insert(CityEntity cityEntity);

}
