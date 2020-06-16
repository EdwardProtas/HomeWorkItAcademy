package com.example.project.database;

import java.util.Date;

import androidx.room.TypeConverter;

public class Converters {

    @TypeConverter
    public static long dateToLong(Date date) {
        long d = 0;
        if(date != null) {
           d = date.getTime();
        }
        return d;
    }

    @TypeConverter
    public static Date longToDate(long millisecond) {
        return new Date(millisecond);
    }
}
