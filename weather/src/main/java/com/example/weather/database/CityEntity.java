package com.example.weather.database;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "city", indices = {@Index(value = {"id"}, unique = true)})
public class CityEntity {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String nameCity;

    public CityEntity(String nameCity) {
        this.nameCity = nameCity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNameCity() {
        return nameCity;
    }

    public void setNameCity(String nameCity) {
        this.nameCity = nameCity;
    }
}
