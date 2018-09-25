package com.example.abdelazim.code_05_notifymetodolist.database;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

public class AppTypeConverter {

    @TypeConverter
    public static Long fromDateToLong(Date date) {
        return date == null? null : date.getTime();
    }

    @TypeConverter
    public static Date fromLongToDate(Long time) {
        return time == null? null : new Date(time);
    }
}
